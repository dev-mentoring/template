package org.project.portfolio.repository;

import org.project.portfolio.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
  
  Optional<AccountEntity> findByEmail(String email);
}
