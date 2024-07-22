package org.project.portfolio.user

import jakarta.persistence.*
import org.project.portfolio.common.BaseEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/** 유저 엔티티 */
@Entity
@Table(name = "users")
class User(
    /** 유저 이메일 */
    @Id
    var email: String,
    /** 유저 이름 */
    var name: String,
    /** 유저 핸드폰 번호 */
    var phone: String,
    /** 유저 비밀번호 */
    private var password: String,
) : UserDetails, BaseEntity() {
    /** 유저 역할(권한) */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private var roles: MutableSet<Role> = mutableSetOf(Role.USER)

    /** To String */
    override fun toString(): String {
        return "UserEntity(email='$email', name='$name', phone='$phone', password='$password')"
    }

    /** 유저 역할(권한) */
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.toMutableSet()
    }

    /** 패드워드 Getter */
    override fun getPassword(): String {
        return password
    }

    /** 유저 이름 Getter */
    override fun getUsername(): String {
        return email
    }
}
