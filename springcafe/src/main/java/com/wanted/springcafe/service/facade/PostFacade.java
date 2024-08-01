package com.wanted.springcafe.service.facade;

import com.wanted.springcafe.domain.file.PostFileEntity;
import com.wanted.springcafe.domain.post.PostEntity;
import com.wanted.springcafe.domain.post.PostRepository;
import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.service.*;
import com.wanted.springcafe.service.view.ViewSerivce;
import com.wanted.springcafe.web.comment.dto.request.CommentSave;
import com.wanted.springcafe.web.comment.dto.request.CommentUpdate;
import com.wanted.springcafe.web.comment.dto.response.CommentDto;
import com.wanted.springcafe.web.post.dto.request.PostSave;
import com.wanted.springcafe.web.post.dto.response.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Component
public class PostFacade {

    private final PostRepository postRepository;
    private final PostService postService;
    private final ViewSerivce viewSerivce;
    private final LikesService likesService;
    private final CommentService commentService;
    private final PostFileService postFileService;
    private final UserService userService;
    private final S3Service s3Service;

    public void writePost(PostSave postSave, MultipartFile file) throws IOException {
        UserEntity user = userService.getUser(1L);
        PostEntity post = postService.save(postSave, user);
        if(!file.getResource().exists()) {
            String url = s3Service.saveFile(file);
            PostFileEntity postFile = new PostFileEntity(url);
            postFile.setPost(post);

            postFileService.saveFile(postFile);
        }
    }

    public PostDto viewPost(Long postId, UserEntity user) {
        PostDto post = postService.getPost(postId);
        viewSerivce.addViewCountToRedisWithMidnightExpiration(post, user);
        return post;
    }

    public void likesPost(Long postId, UserEntity user) {
        PostEntity post = postRepository.findById(postId).orElseThrow();
        likesService.addLikesCountToRedis(post, user);
    }

    public void cancelLikes(Long postId, UserEntity user){
        PostEntity post = postRepository.findById(postId).orElseThrow();
        likesService.cancelLikes(post, user);
    }

    //댓글 달기

    public Long writeComment(
            CommentSave commentSave,
            UserEntity user,
            Long postId){
        PostEntity post = postRepository.findById(postId).orElseThrow();
        return commentService.addComment(commentSave, user, post);
    }

    //대댓글 달기

    public Long addReply(
            CommentSave commentSave,
            UserEntity user,
            Long postId){
        PostEntity post = postRepository.findById(postId).orElseThrow();
        return commentService.addReplyToComment(commentSave, user, post);
    }


    public void updateComment(
            CommentUpdate commentUpdate,
            Long commentId,
            UserEntity user ){
        commentService.updateComment(commentUpdate, commentId, user);
    }

    public List<CommentDto> getAllComments(Long postId){
        return commentService.getPostComments(postId);
    }

    public void deleteComment(Long commentId, UserEntity user){
        commentService.softDeleteComment(commentId,user);
    }

    public void restoreCommnet(Long commentId){
        commentService.restoreSoftDeletedComment(commentId);
    }
}
