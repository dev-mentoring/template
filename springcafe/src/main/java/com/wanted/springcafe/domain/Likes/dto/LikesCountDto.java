package com.wanted.springcafe.domain.Likes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikesCountDto {

    private final Long postId;
    private final Long likesCount;
}
