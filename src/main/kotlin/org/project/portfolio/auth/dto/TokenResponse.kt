package org.project.portfolio.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "토큰 응답")
data class TokenResponse(
    @field:Schema(description = "JWT Access Token")
    @JsonProperty("token")
    val token: String
) {
    override fun toString(): String {
        return "TokenResponse(token='$token')"
    }
}
