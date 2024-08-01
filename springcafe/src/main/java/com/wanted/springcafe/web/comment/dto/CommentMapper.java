package com.wanted.springcafe.web.comment.dto;

import com.wanted.springcafe.domain.comment.CommentEntity;
import com.wanted.springcafe.web.comment.dto.response.CommentDto;

import java.util.List;

public class CommentMapper {

    public static CommentDto toDto(CommentEntity comment){
        return new CommentDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getUser(),
                comment.isDeleted(),
                comment.getPost().getPostId(),
                comment.getPublishDate(),
                comment.getLastModifiedDate(),
                toDtoList(comment.getChildren()));
    }

    public static List<CommentDto> toDtoList(List<CommentEntity> comments) {
        return comments.stream()
                .map(i->toDto(i))
                .toList();
    }
}
