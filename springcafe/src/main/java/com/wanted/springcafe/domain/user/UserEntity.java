package com.wanted.springcafe.domain.user;

import com.wanted.springcafe.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "user",uniqueConstraints = {
        @UniqueConstraint(
                name="LOGINID_UNIQUE",
                columnNames={"loginId"}
        )})
@Entity
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    private String loginId;
    private String password;
    private String phoneNumber;
    private boolean isDeleted;
    private String role;

    public UserEntity(
            String username,
            String email,
            String loginId,
            String password,
            String phoneNumber,
            String role
            ) {
        this.username = username;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public UserEntity(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public void setUserId(Long userId){
        if(userId == null) {
            this.userId = userId;
        }
    }
}
