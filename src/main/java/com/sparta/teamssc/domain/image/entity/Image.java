package com.sparta.teamssc.domain.image.entity;

import com.sparta.teamssc.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(nullable = false, unique = true)
    private String fileLink;

    @OneToMany(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardImage> boardImages = new ArrayList<>();

    @Builder
    public Image(String name, String fileLink) {
        this.name = name;
        this.fileLink = fileLink;
    }

}
