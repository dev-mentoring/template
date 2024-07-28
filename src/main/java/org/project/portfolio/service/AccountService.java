package org.project.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.controller.request.AccountJoinRequest;
import org.project.portfolio.entity.AccountEntity;
import org.project.portfolio.exception.ErrorCode;
import org.project.portfolio.exception.PortfolioApplicationException;
import org.project.portfolio.repository.AccountRepository;
import org.project.portfolio.utils.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
  
  private final AccountRepository accountRepository;
  
  @Transactional
  public void join(AccountJoinRequest request) {
    if(!Validator.isValidEmail(request.getEmail())) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_EMAIL);
    }
    if(!Validator.isValidMobile(request.getMobileNumber())) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_MOBILE);
    }
    if(!Validator.isValidName(request.getName())) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_NAME);
    }
    if(!Validator.isValidPassword(request.getPassword())) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_PASSWORD);
    }
    
    // 이메일 중복 검사
    accountRepository.findByEmail(request.getEmail())
            .ifPresent((it) -> { throw new PortfolioApplicationException(ErrorCode.DUPLICATE_EMAIL); });
    
    AccountEntity accountEntity = AccountEntity.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .mobileNumber(request.getMobileNumber())
            .name(request.getName())
            .build();
    accountRepository.save(accountEntity);
  }
}
