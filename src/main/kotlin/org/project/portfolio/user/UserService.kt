package org.project.portfolio.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    /**
     * Get user information
     * @param email 유저 이메일
     */
    fun getUser(email: String): User {
        return userRepository.findById(email).orElseThrow {
            throw IllegalArgumentException("User not found")
        }
    }
}
