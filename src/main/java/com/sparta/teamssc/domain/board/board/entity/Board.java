package com.sparta.teamssc.domain.board.board.entity;

import com.sparta.teamssc.common.entity.BaseEntity;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.track.entity.Track;
import com.sparta.teamssc.domain.user.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardType boardType;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String imageUrl;

//    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
//    private List<BoardImage> boardImages = new ArrayList<>();

//    @JoinColumn(name = "period_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Period period;

    @Builder
    public Board(String title, String content, BoardType boardType, User user/*, Period period*/,String imageUrl) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.user = user;
//        this.period = period;
        this.imageUrl = imageUrl;
    }

}
