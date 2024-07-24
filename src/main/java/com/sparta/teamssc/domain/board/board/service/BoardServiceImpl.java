package com.sparta.teamssc.domain.board.board.service;

import com.sparta.teamssc.domain.board.board.dto.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.board.boardImage.entity.BoardImage;
import com.sparta.teamssc.domain.board.board.entity.BoardType;
import com.sparta.teamssc.domain.board.board.repository.BoardRepository;
import com.sparta.teamssc.domain.board.boardImage.service.BoardImageService;
import com.sparta.teamssc.domain.image.entity.Image;
import com.sparta.teamssc.domain.image.service.ImageService;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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


}
