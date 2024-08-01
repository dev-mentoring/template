package com.wanted.springcafe.web.post.dto;

import com.wanted.springcafe.domain.post.PostEntity;
import com.wanted.springcafe.web.comment.dto.CommentMapper;
import com.wanted.springcafe.web.post.dto.response.PostDto;
import com.wanted.springcafe.web.post.dto.response.PostListDto;
import com.wanted.springcafe.web.user.UserMapper;

import java.util.List;

public class PostMapper {

    public static List<PostListDto> toDtoList(List<PostEntity> posts) {
        return posts.stream()
                .map(i->new PostListDto(i.getPostId(), i.getTitle(), i.getPublishDate()))
                .toList();
    }

    public static PostDto toDto(PostEntity post) {
        return new PostDto(post.getPostId(),
                post.getTitle(),
                post.getContents(),
                UserMapper.toDto(post.getUser()),
                post.getLikeCount(),
                post.getViewCount(),
                CommentMapper.toDtoList(post.getComments()));
    }

}
