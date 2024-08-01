package com.wanted.springcafe.web.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class PostUpdate {

    private String title;
    private String content;
}
