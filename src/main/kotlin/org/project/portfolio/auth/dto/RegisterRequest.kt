package org.project.portfolio.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "회원가입 폼")
data class RegisterRequest(
    @field:Schema(description = "이메일", example = "test@test.com")
    val email: String,
    @field:Schema(description = "전화번호", example = "010-1234-5678")
    val phone: String,
    @field:Schema(description = "이름", example = "홍길동")
    val name: String,
    @field:Schema(description = "비밀번호", example = "password")
    val password: String
) {
}
