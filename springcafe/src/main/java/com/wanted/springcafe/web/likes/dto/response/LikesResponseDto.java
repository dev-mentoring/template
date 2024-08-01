package com.wanted.springcafe.web.likes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikesResponseDto {
    private Long likesId;
    private Long postId;
    private Long userId;
}
