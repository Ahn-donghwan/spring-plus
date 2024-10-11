package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // 2. 캐싱 사용
    @Cacheable(value = "users", key = "#nickname")
    Optional<User> findByNickname(String nickname);
}
