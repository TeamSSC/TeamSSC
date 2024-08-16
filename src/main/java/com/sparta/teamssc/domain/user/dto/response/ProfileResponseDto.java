package com.sparta.teamssc.domain.user.dto.response;

import com.sparta.teamssc.domain.track.entity.Period;
import com.sparta.teamssc.domain.team.entity.Team;
import com.sparta.teamssc.domain.user.entity.UserMbti;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private final String email;
    private final String username;
    private final String gitLink;
    private final String vlogLink;
    private final String intro;
    private final UserMbti mbti;
    private final String profileImage;
    private final String section;
    private final Team team;
    private final Period period;

    @Builder
    public ProfileResponseDto(String email, String username, String gitLink, String vlogLink, String intro,
                              UserMbti mbti, String profileImage, String section, Team team, Period period){
        this.email = email;
        this.username = username;
        this.gitLink = gitLink;
        this.vlogLink = vlogLink;
        this.intro = intro;
        this.mbti = mbti;
        this.profileImage = profileImage;
        this.section = section;
        this.team = team;
        this.period = period;
    }
}
