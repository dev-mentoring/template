package org.project.portfolio.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountJoinRequest {
  private String email;
  private String password;
  private String mobileNumber;
  private String name;
}
