package com.sparta.teamssc.domain.chat.entity;

import com.sparta.teamssc.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private Long roomId;

    @Builder
    public Message(String content, String sender, Long roomId) {
        this.content = content;
        this.sender = sender;
        this.roomId = roomId;
    }
}
