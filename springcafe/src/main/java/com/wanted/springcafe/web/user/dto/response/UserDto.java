package com.wanted.springcafe.web.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {

    private Long userId;
    private String username;
    private String email;
    private String loginId;
    private String phoneNumber;
}
