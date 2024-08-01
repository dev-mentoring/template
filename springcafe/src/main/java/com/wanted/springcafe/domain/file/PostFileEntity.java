package com.wanted.springcafe.domain.file;

import com.wanted.springcafe.domain.post.PostEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name = "postfile")
@Entity
public class PostFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postFileId;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    public PostFileEntity(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }
}
