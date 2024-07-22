package org.project.portfolio.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/** 토큰 응답 DTO */
@Schema(name = "토큰 응답")
data class TokenResponse(
    /** JWT Access Token */
    @field:Schema(description = "JWT Access Token")
    @JsonProperty("token")
    val token: String
) {
    override fun toString(): String {
        return "TokenResponse(token='$token')"
    }
}
