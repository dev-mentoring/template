package com.wanted.springcafe.web.user;

import com.wanted.springcafe.service.UserService;
import com.wanted.springcafe.web.user.dto.request.UserSave;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public void join(@Valid @RequestBody  UserSave userSave, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("유효하지 않은 회원 형식입니다");
            return;
        }
        userService.save(userSave);

    }
}
