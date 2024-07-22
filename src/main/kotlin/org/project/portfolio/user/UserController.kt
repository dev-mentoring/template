package org.project.portfolio.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

/** User API 컨트롤러 */
@Tag(name = "2. User", description = "The user API")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService
) {
    /**
     * 내 정보 조회 API
     * @param principal 로그인 정보
     * @return 사용자 정보
     */
    @GetMapping
    @Operation(summary = "내 정보 조회 API")
    fun getUserInfo(
        principal: Principal
    ): User {
        return userService.getUser(principal.name)
    }

}
