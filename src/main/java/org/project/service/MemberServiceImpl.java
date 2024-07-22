package org.project.service;

import lombok.RequiredArgsConstructor;
import org.project.dto.MemberLoginDto;
import org.project.dto.MemberRegisterDto;
import org.project.entity.Member;
import org.project.exception.CustomException;
import org.project.exception.ErrorCode;
import org.project.mapper.MemberMapper;
import org.project.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    @Override
    @Transactional
    public void register(MemberRegisterDto registerDto) {
        memberRepository.findById(registerDto.getEmail())
                .ifPresent(member -> {
                    throw new CustomException(ErrorCode.MEMBER_EMAIL_EXCEPTION);
                });

        Member member = memberMapper.toEntity(registerDto);
        memberRepository.save(member);
    }

    @Override
    public void login(MemberLoginDto loginDto) {

    }
}
