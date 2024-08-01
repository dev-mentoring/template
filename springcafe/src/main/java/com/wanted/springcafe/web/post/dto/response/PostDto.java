package com.wanted.springcafe.web.post.dto.response;

import com.wanted.springcafe.web.comment.dto.response.CommentDto;
import com.wanted.springcafe.web.user.dto.response.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostDto {

    private Long postId;
    private String title;
    private String contents;
    private UserDto user;
    private Long likeCount;
    private Long viewCount;
    private List<CommentDto> comments;
}
