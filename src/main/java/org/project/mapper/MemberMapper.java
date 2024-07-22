package org.project.mapper;

import org.project.dto.MemberRegisterDto;
import org.project.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toEntity(MemberRegisterDto registerDto) {
        return Member.builder()
                .email(registerDto.getEmail())
                .phone(registerDto.getPhone())
                .username(registerDto.getUsername())
                .password(registerDto.getPassword())
                .build();
    }

}
