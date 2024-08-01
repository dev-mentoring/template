package com.wanted.springcafe.web.user;

import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.web.user.dto.response.UserDto;

public class UserMapper {

    public static UserDto toDto(UserEntity user){
        return new UserDto(user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getLoginId(),
                user.getPhoneNumber());
    }
}
