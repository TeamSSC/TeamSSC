package com.sparta.teamssc.domain.board.board.service;

import com.sparta.teamssc.domain.board.board.dto.request.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.dto.request.BoardUpdateRequestDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardResponseDto;
import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.board.board.entity.BoardType;
import com.sparta.teamssc.domain.board.board.exception.BoardCreationFailedException;
import com.sparta.teamssc.domain.board.board.repository.BoardRepository;
import com.sparta.teamssc.domain.board.boardImage.entity.BoardImage;
import com.sparta.teamssc.domain.board.boardImage.service.BoardImageService;
import com.sparta.teamssc.domain.image.entity.Image;
import com.sparta.teamssc.domain.image.service.ImageService;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final BoardImageService boardImageService;

    // 게시글 생성
    @Override
    public void createBoard(BoardRequestDto requestDto, String email) {
        try {
            User user = userService.getUserByEmail(email);

            Board board = Board.builder()
                    .title(requestDto.getTitle())
                    .content(requestDto.getContent())
                    .boardType(BoardType.BOARD)
                    .user(user)
                    .build();

            // 보드 저장
            boardRepository.save(board);

            // 이미지 저장 및 보드와의 연관 관계 설정
            if (requestDto.getImages() != null) {
                for (MultipartFile imageFile : requestDto.getImages()) {
                    Image image = uploadImage(imageFile);
                    boardImageService.boardImageSave(board, image);
                }
            }
        } catch (RuntimeException e) {
            throw new BoardCreationFailedException("보드 생성에 실패했습니다.");
        }
    }

    // 특정 게시글 조회
    @Override
    public BoardResponseDto getBoard(Long boardId) {
        Board board = findBoardByBoardId(boardId);
        List<String> imageUrls = board.getBoardImages().stream()
                .map(boardImage -> boardImage.getImage().getFileLink())  // 이미지 URL을 추출
                .collect(Collectors.toList());

        return BoardResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .fileLinks(imageUrls)
                .build();
    }

    // 게시글 전체 조회
    @Override
    public Page<BoardListResponseDto> getBoards(int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createAt"));

        Page<BoardListResponseDto> boardPage = boardRepository.findPagedBoardList(pageable);

        if (boardPage.isEmpty()) {
            throw new IllegalArgumentException("작성된 게시글이 없거니, " + (page + 1) + " 페이지에 글이 없습니다.");
        }

        return boardPage;
    }

    // 게시글 수정
    @Override
    @Transactional
    public void updateBoard(Long boardId, BoardUpdateRequestDto requestDto, String email) {
        try {
            Board board = findBoardByBoardId(boardId);
            if (board.getUser().getEmail().equals(email)) {

                // 삭제할 이미지가 있으면 삭제
                if (requestDto.getDeleteImagesLink() != null) {
                    for (String imageLink : requestDto.getDeleteImagesLink()) {
                        deleteImage(imageLink);
                    }
                }

                if (requestDto.getUploadImages() != null) {
                    for (MultipartFile imageFile : requestDto.getUploadImages()) {
                        Image image = uploadImage(imageFile);
                        boardImageService.boardImageSave(board, image);
                        board.update(requestDto.getTitle(), requestDto.getContent());
                    }
                } else {
                    board.update(requestDto.getTitle(), requestDto.getContent());
                }

            } else {
                throw new IllegalArgumentException("본인 게시글만 수정할 수 있습니다.");
            }
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("게시글 수정에 실패하였습니다.");
        }
    }

    // 게시글 삭제
    @Override
    public void deleteBoard(Long boardId, String email) {
        try {
            Board board = findBoardByBoardId(boardId);
            if (board.getUser().getEmail().equals(email)) {
                for (String fileLink : boardImageService.findFileUrlByBoardId(boardId)) {
                    deleteImage(fileLink);
                }
                boardRepository.delete(board);
            }
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("게시글 삭제에 실패했습니다.");
        }
    }

    // 이미지 저장하기
    private Image uploadImage(MultipartFile image) {

        if (image != null && !image.isEmpty()) {
            return imageService.uploadFile(image);
        }
        return null;
    }

    // 이미지 삭제하기
    private void deleteImage(String imageUrl) {

        if (imageUrl != null) {
            imageService.deleteFile(imageUrl);
        }
    }

    // 보드 아이디로 게시글 찾기
    @Override
    public Board findBoardByBoardId(Long boardId) {

        return boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
    }

    // 공지사항 작성
    @Override
    public void createNotice(BoardRequestDto requestDto, String email) {
        try {
            User user = userService.getUserByEmail(email);

            Board board = Board.builder()
                    .title(requestDto.getTitle())
                    .content(requestDto.getContent())
                    .boardType(BoardType.NOTICE)
                    .user(user)
                    .build();

            // 보드 저장
            boardRepository.save(board);

            // 이미지 저장 및 보드와의 연관 관계 설정
            if (requestDto.getImages() != null) {
                for (MultipartFile imageFile : requestDto.getImages()) {
                    Image image = uploadImage(imageFile);
                    boardImageService.boardImageSave(board, image);
                }
            }
        } catch (RuntimeException e) {
            throw new BoardCreationFailedException("보드 생성에 실패했습니다.");
        }
    }

    // 공지사항 전체 조회
    @Override
    public Page<BoardListResponseDto> getNotices(int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createAt"));

        Page<BoardListResponseDto> noticePage = boardRepository.findPagedNoticeList(pageable);

        if (noticePage.isEmpty()) {
            throw new IllegalArgumentException("작성된 공지사항이 없거니, " + (page + 1) + " 페이지에 공지사항이 없습니다.");
        }

        return noticePage;
    }
}
