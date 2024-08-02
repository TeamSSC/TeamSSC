package com.sparta.teamssc.domain.user.user.entity;

import com.sparta.teamssc.common.entity.BaseEntity;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.user.refreshToken.entity.RefreshToken;
import com.sparta.teamssc.domain.user.role.userRole.entity.UserRole;
import com.sparta.teamssc.domain.userTeamMatches.entity.UserTeamMatch;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(name = "git_link", nullable = true)
    private String gitLink;

    @Column(name = "vlog_link", nullable = true)
    private String vlogLink;

    @Column(nullable = true)
    private String intro;

    @Column(nullable = true)
    private UserMbti mbti;

    @Column(nullable = true)
    private String hobby;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private UserStatus status;

    @Column(name = "profile_image", nullable = true)
    private String profileImage;

    @Column(nullable = true)
    private String section;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTeamMatch> userTeamMatches = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "period_id")
    private Period period;

    @Builder
    public User(String email, String password, String username, UserStatus status, Period period) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.status = status;
        this.period = period;
    }

    public void login() {
        this.status = UserStatus.ACTIVE;
    }

    public void signupApproval() {
        this.status = UserStatus.ACTIVE;
    }

    public void signupRefusal() {
        this.status = UserStatus.REFUSAL;
    }

    public void logout() {
        this.status = UserStatus.LOGOUT;
    }

    public void updateProfile(String gitLink, String vlogLink,String intro,UserMbti mbti) {
        this.gitLink = gitLink;
        this.vlogLink = vlogLink;
        this.intro = intro;
        this.mbti = mbti;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void withdrawn() {
        this.status = UserStatus.WITHDRAWN;
    }

//    public void updateStatus() { this.status = UserStatus.PENDING; }
}
