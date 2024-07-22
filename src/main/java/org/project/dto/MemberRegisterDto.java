package org.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.validation.constraints.Pattern;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterDto {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "휴대폰은 필수 입력 항목입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
    private String phone;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]{3,20}$", message = "이름은 대소문자 및 한글만 포함이 가능합니다.")
    private String username;
    
    @NotBlank(message = "비밀번호은 필수 입력 항목입니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d.*\\d.*\\d.*\\d)(?=.*[!@#$%^&*()_+].*[!@#$%^&*()_+]).{8,}$", message = "대소문자, 숫자 5개 이상, 특수문자 포함 2개 이상 검즘")
    private String password;

}
