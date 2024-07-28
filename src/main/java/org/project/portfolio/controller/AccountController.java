package org.project.portfolio.controller;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.controller.request.AccountJoinRequest;
import org.project.portfolio.controller.response.Result;
import org.project.portfolio.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
  
  private final AccountService accountService;
  
  @PostMapping("/join")
  public Result<Void> join(@RequestBody AccountJoinRequest request) {
    accountService.join(request);
    return Result.OK();
  }
}
