package com.sparta.teamssc.domain.chat.repository;

import com.sparta.teamssc.domain.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
