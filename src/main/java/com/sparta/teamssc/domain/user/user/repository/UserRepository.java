package com.sparta.teamssc.domain.user.user.repository;

import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import com.sparta.teamssc.domain.user.user.repository.userMapping.ProfileCardMapper;
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

    @Query("SELECT u.id AS id, u.username AS username " +
            "FROM User u " +
            "WHERE (u.period = :periodId) " +
            "ORDER BY u.createAt")
    Page<ProfileCardMapper> findMemberCards(@Param("periodId") Period periodId, Pageable pageable);

    //기수로 사용자 찾기
    @Query("SELECT u FROM User u WHERE u.period.id = :periodId")
    List<User> findAllByPeriodId(@Param("periodId") Long periodId);
}
