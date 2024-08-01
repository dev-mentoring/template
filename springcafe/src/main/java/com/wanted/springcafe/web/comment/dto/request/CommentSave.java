package com.wanted.springcafe.web.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentSave {

    private String content;
    private Long parentId;

    public CommentSave(String content) {
        this.content = content;
    }
}
