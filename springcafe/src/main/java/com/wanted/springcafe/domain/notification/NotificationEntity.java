package com.wanted.springcafe.domain.notification;

import com.wanted.springcafe.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "notification",indexes ={
        @Index(
                name = "idx_user_id", columnList = "user_id")
})
@Entity
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    private String message;
    private boolean isRead;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime publishDate;

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public NotificationEntity(UserEntity user, String message) {
        this.user = user;
        this.message = message;
    }

    public Long getUserId(){
        return user.getUserId();
    }

    public void setUser(UserEntity user){
        this.user = user;
    }

}
