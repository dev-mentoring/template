package com.wanted.springcafe.service.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ViewCountDto {
    private Long postId;
    private Long viewCount;
}
