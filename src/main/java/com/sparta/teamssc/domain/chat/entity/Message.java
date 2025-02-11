package com.sparta.teamssc.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparta.teamssc.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private RoomType roomType;
    @Builder
    public Message(String content, String sender, Long roomId, RoomType roomType) {
        this.content = content;
        this.sender = sender;
        this.roomId = roomId;
        this.roomType = roomType;
    }
}