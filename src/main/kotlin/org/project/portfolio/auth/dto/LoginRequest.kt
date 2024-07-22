package org.project.portfolio.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

/** 로그인 폼 */
@Schema(name = "로그인 폼")
data class LoginRequest(
    /** 이메일 */
    @field:Schema(description = "이메일", example = "test@test.com")
    val email: String,
    /** 비밀번호 */
    @field:Schema(description = "비밀번호", example = "password")
    val password: String
) {
}
