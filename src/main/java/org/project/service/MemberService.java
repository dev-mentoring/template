package org.project.service;

import org.project.dto.MemberLoginDto;
import org.project.dto.MemberRegisterDto;

public interface MemberService {

    void register(MemberRegisterDto registerDto);

    void login(MemberLoginDto loginDto);

}
