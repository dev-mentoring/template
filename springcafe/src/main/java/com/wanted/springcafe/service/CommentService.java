package com.wanted.springcafe.service;

import com.wanted.springcafe.constant.Filter;
import com.wanted.springcafe.domain.comment.CommentEntity;
import com.wanted.springcafe.domain.comment.CommentRepository;
import com.wanted.springcafe.domain.post.PostEntity;
import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.exception.NotCommentAuthorException;
import com.wanted.springcafe.utility.FilterManager;
import com.wanted.springcafe.web.comment.dto.CommentMapper;
import com.wanted.springcafe.web.comment.dto.request.CommentSave;
import com.wanted.springcafe.web.comment.dto.request.CommentUpdate;
import com.wanted.springcafe.web.comment.dto.response.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final FilterManager filterManager;

    public Long addComment(
            CommentSave commentSave,
            UserEntity user,
            PostEntity post) {

        CommentEntity commentEntity = new CommentEntity(commentSave.getContent());
        commentEntity.setUser(user);
        commentEntity.setPost(post);
        post.addComment(commentEntity);

        return commentRepository.save(commentEntity).getCommentId();
    }

    public Long addReplyToComment(
            CommentSave commentSave,
            UserEntity user,
            PostEntity post) {

        CommentEntity commentEntity = new CommentEntity(commentSave.getContent());
        if (commentSave.getParentId() != null) {
            CommentEntity parentComment = commentRepository.findById(commentSave.getParentId())
                    .orElseThrow();
            commentEntity.setParent(parentComment);
            parentComment.getChildren().add(commentEntity);
        }
        commentEntity.setUser(user);
        commentEntity.setPost(post);

        return commentRepository.save(commentEntity).getCommentId();
    }

    public void updateComment(
            CommentUpdate commentUpdate,
            Long commentId,
            UserEntity user) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow();
        if (!commentEntity.isCommentWriter(user.getUserId())) {
            throw new NotCommentAuthorException();
        }
        commentEntity.update(commentUpdate.getContent());
    }

    public List<CommentDto> getPostComments(Long postId) {
        filterManager.enableFilter(
                Filter.COMMENT_FILTER,
                Filter.FILTER_PARAM,
                false);

        List<CommentDto> commentList = commentRepository.findAllByPost_PostId(postId)
                .stream()
                .map(CommentMapper::toDto)
                .toList();
        filterManager.disableFilter(Filter.COMMENT_FILTER);

        return commentList;
    }

    public CommentDto getComment(Long id) {
        filterManager.enableFilter(
                Filter.POST_FILTER,
                Filter.FILTER_PARAM,
                false);

        CommentEntity comment = filterManager
                .getEntityManager()
                .createQuery("SELECT c FROM CommentEntity c WHERE c.commentId = :id", CommentEntity.class)
                .setParameter("id", id)
                .getSingleResult();
        filterManager.disableFilter(Filter.POST_FILTER);

        return CommentMapper.toDto(comment);
    }

    public void softDeleteComment(Long commentId, UserEntity user) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow();
        if (!commentEntity.isCommentWriter(user.getUserId())) {
            throw new NotCommentAuthorException();
        }
        commentEntity.delete();
    }

    public void restoreSoftDeletedComment(Long id) {
        filterManager.enableFilter(
                Filter.POST_FILTER,
                Filter.FILTER_PARAM,
                true);

        CommentEntity comment = filterManager
                .getEntityManager()
                .createQuery("SELECT c FROM CommentEntity c WHERE c.commentId = :id", CommentEntity.class)
                .setParameter("id", id)
                .getSingleResult();
        filterManager.disableFilter(Filter.POST_FILTER);
        comment.restore();
    }
}
