package org.project.portfolio.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/** 사용자 레포지토리 */
@Repository
interface UserRepository : JpaRepository<User, String> {

}
