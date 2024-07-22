package org.project.portfolio.user

import org.springframework.security.core.GrantedAuthority

/** The roles that a user can have. */
enum class Role : GrantedAuthority {
    /** A regular user. */
    USER,

    /** An administrator. */
    ADMIN,
    ;

    /** Returns the authority of this role. */
    override fun getAuthority(): String {
        return "ROLE_$name"
    }
}
