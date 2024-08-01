package com.wanted.springcafe.web.post.dto.response;

import com.wanted.springcafe.domain.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostListDto {

    private Long postId;
    private String title;
    private LocalDate publishDate;
}
