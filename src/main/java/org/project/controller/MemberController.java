package org.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.dto.MemberRegisterDto;
import org.project.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@Valid @RequestBody MemberRegisterDto memberRegisterDto, BindingResult bindingResult) {
        log.info("검증 시도: {} - BindingResult hasErrors: {}", memberRegisterDto, bindingResult.hasErrors());

        if (bindingResult.hasErrors()) {
            log.error("검증 오류 발생:");
            bindingResult.getAllErrors().forEach(error -> {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    log.error("Field error - Field: {}, Message: {}", fieldError.getField(), fieldError.getDefaultMessage());
                } else {
                    log.error("Object error - Message: {}", error.getDefaultMessage());
                }
            });

            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> {
                        if (error instanceof FieldError) {
                            FieldError fieldError = (FieldError) error;
                            return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                        } else {
                            return error.getDefaultMessage();
                        }
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(String.join(", ", errors));
        }

        log.info("검증 성공");
        return ResponseEntity.ok("회원가입 성공");
    }

}
