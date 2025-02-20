package com.sparta.teamssc.domain.board.board.repository;

import com.sparta.teamssc.domain.board.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository{
}
