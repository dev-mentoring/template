package com.wanted.springcafe.web.post.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostSave {

    private String title;
    private String content;
}
