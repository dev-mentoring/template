package com.wanted.springcafe.service;

import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.domain.user.UserRepository;
import com.wanted.springcafe.exception.UserAlreadyExistException;
import com.wanted.springcafe.web.user.dto.request.UserSave;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final String USER_ROLE= "ROLE_USER";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public Long save(UserSave userSave){

        boolean result = userRepository.existsByLoginId(userSave.getLoginId());

        if(result){
            throw new UserAlreadyExistException();
        }

        UserEntity userEntity = new UserEntity(
                userSave.getUsername(),
                userSave.getEmail(),
                userSave.getLoginId(),
                encoder.encode(userSave.getPassword()),
                userSave.getPhoneNumber(),
                USER_ROLE
        );
        return userRepository.save(userEntity).getUserId();
    }

    public UserEntity getUser(Long userId){
        return userRepository.findById(userId).orElseThrow();
    }
}
