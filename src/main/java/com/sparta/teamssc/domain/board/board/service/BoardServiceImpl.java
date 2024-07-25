package com.sparta.teamssc.domain.board.board.service;

import com.sparta.teamssc.domain.board.board.dto.request.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardResponseDto;
import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.board.board.entity.BoardType;
import com.sparta.teamssc.domain.board.board.exception.BoardCreationFailedException;
import com.sparta.teamssc.domain.board.board.repository.BoardRepository;
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
    public void createBoard(BoardRequestDto requestDto, String username) {
        try {
            User user = userService.findByUsername(username);

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
    public Page<BoardListResponseDto> getBoards(int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createAt"));

        Page<BoardListResponseDto> boardPage = boardRepository.findPagedBoardList(pageable);

        if (boardPage.isEmpty()) {
            throw new IllegalArgumentException("작성된 게시글이 없거니, " + (page + 1) + " 페이지에 글이 없습니다.");
        }

        return boardPage;
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
    private Board findBoardByBoardId(Long boardId) {

        return boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
    }
}
