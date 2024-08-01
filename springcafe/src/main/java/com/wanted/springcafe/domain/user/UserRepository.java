package com.wanted.springcafe.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

   boolean existsByLoginId(String loginId);
   Optional<UserEntity> findByLoginId(String loginId);
}
