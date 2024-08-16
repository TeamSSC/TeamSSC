package com.sparta.teamssc.domain.user.repository;

import com.sparta.teamssc.domain.track.entity.Period;
import com.sparta.teamssc.domain.user.entity.User;
import com.sparta.teamssc.domain.user.repository.userMapping.ProfileCardMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    @EntityGraph(attributePaths = {"roles", "roles.role"})
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Optional<User> findByKakaoId(Long kakaoId);

    @Query("SELECT u.id AS id, u.username AS username " +
            "FROM User u JOIN u.roles r "+
            "WHERE (u.period = :periodId AND r.role.name = :role) " +
            "ORDER BY u.createAt")
    Page<ProfileCardMapper> findMemberCards(@Param("periodId") Period periodId, @Param("role") String role, Pageable pageable);
    //기수로 사용자 찾기
    @Query("SELECT u FROM User u WHERE u.period.id = :periodId")
    List<User> findAllByPeriodId(@Param("periodId") Long periodId);
}
