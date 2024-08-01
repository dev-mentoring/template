package com.wanted.springcafe.web.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserSave {

    @NotBlank
    @Size(min = 1, max =8)
    @Pattern(regexp = "^[가-힣]*$")
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 1, max =10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "(?=(?:[^0-9]*[0-9]){5,})(?=.*[a-zA-Z])(?=(?:.*\\W){2,})(?=\\S+$).{7,12}")
    private String password;

    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
    private String phoneNumber;

    public UserSave(String username, String email, String loginId, String password, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
