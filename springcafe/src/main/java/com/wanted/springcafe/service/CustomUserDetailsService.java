package com.wanted.springcafe.service;

import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.domain.user.UserRepository;
import com.wanted.springcafe.web.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByLoginId(username).orElseThrow();
        return new CustomUserDetails(userEntity);
    }
}
