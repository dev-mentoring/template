package com.wanted.springcafe.web.comment.dto.response;

import com.wanted.springcafe.domain.post.PostEntity;
import com.wanted.springcafe.domain.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class CommentDto {

    private Long commentId;
    private String content;
    private UserEntity user;
    private boolean isDeleted;
    private Long postId;
    private LocalDate publishDate;
    private LocalDate lastModifiedDate;
    private List<CommentDto> children;
}
