package com.wanted.springcafe.domain.Likes;

import com.wanted.springcafe.domain.post.PostEntity;
import com.wanted.springcafe.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "likes")
@Entity
public class LikesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }
}
