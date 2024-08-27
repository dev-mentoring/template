package org.project.portfolio.domain.users.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.portfolio.domain.users.dto.request.RegisterRequestDto;
import org.project.portfolio.domain.users.dto.response.MessageResponseDto;
import org.project.portfolio.domain.users.service.UsersService;
import org.project.portfolio.global.exception.dto.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse<MessageResponseDto>> register(
            @Valid @RequestBody RegisterRequestDto requestDto,
            HttpServletRequest servRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        usersService.register(requestDto)
                ));
    }

//    @PostMapping("/login")
//    public ResponseEntity<MessageResponseDto> login(@RequestBody LoginRequestDto requestDto) {
//        usersService.login(requestDto);
//    }
}