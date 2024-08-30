package org.project.portfolio.domain.users.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.portfolio.domain.users.domain.Users;
import org.project.portfolio.domain.users.domain.UsersRole;
import org.project.portfolio.domain.users.dto.request.RegisterRequestDto;
import org.project.portfolio.domain.users.dto.response.MessageResponseDto;
import org.project.portfolio.domain.users.exception.UserDuplicateEmailException;
import org.project.portfolio.domain.users.exception.UserDuplicateUsernameException;
import org.project.portfolio.domain.users.repository.UsersRepository;
import org.project.portfolio.global.redis.RedisDao;
import org.project.portfolio.global.security.jwt.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisDao redisDao;
    private final JwtProvider jwtProvider;

    public MessageResponseDto register(RegisterRequestDto requestDto) {
        // 회원 중복 확인
        Optional<Users> checkUsername = usersRepository.findByUsername(requestDto.getUsername());
        if (checkUsername.isPresent()) {
            throw new UserDuplicateUsernameException();
        }

        // email 중복확인
        Optional<Users> checkEmail = usersRepository.findByEmail(requestDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new UserDuplicateEmailException();
        }

        // 비밀번호 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 등록
        Users users = Users.builder()
                .username(requestDto.getUsername())
                .password(password)
                .email(requestDto.getEmail())
                .role(UsersRole.USER)
                .createdAt(LocalDateTime.now())
                .build();

        usersRepository.save(users);
        return new MessageResponseDto("회원가입이 완료되었습니다.");
    }

    public MessageResponseDto logout(HttpServletRequest servletRequest) {
        String accessTokenFromHeader = jwtProvider.extractAccessToken(servletRequest).orElse(null);
        String username = jwtProvider.extractUsername(accessTokenFromHeader);
        redisDao.deleteValues(username);
        return new MessageResponseDto("로그아웃 되었습니다.");
    }
}