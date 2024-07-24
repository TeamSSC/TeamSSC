package com.sparta.teamssc.domain.board.board.service;

import com.sparta.teamssc.domain.board.board.dto.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.board.board.entity.BoardType;
import com.sparta.teamssc.domain.board.board.repository.BoardRepository;
import com.sparta.teamssc.domain.image.service.ImageService;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;
    private final ImageService imageService;

    // 게시글 생성
    @Override
    public void createBoard(BoardRequestDto requestDto, String username) {

        User user = userService.findByUsername(username);

        String imageUrl = uploadImage(requestDto.getImage());

        Board board = Board.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .imageUrl(imageUrl)
//                .period(user.getTeam().getPeriod())
                .boardType(BoardType.BOARD)
                .user(user)
                .build();

        boardRepository.save(board);


    }

    // 이미지 저장하기
    private String uploadImage(MultipartFile image) {
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
