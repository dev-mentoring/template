package com.wanted.springcafe.domain.Likes.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikesDto {

    private final Long postId;
    private final Long userId;
}
