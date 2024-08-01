package com.wanted.springcafe.service;

import com.wanted.springcafe.domain.comment.CommentEntity;
import com.wanted.springcafe.domain.post.PostEntity;
import com.wanted.springcafe.domain.post.PostRepository;
import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.exception.NotCommentAuthorException;
import com.wanted.springcafe.service.facade.PostFacade;
import com.wanted.springcafe.web.comment.dto.request.CommentSave;
import com.wanted.springcafe.web.comment.dto.request.CommentUpdate;
import com.wanted.springcafe.web.comment.dto.response.CommentDto;
import com.wanted.springcafe.web.post.dto.request.PostSave;
import com.wanted.springcafe.web.post.dto.response.PostDto;
import com.wanted.springcafe.web.user.dto.request.UserSave;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostFacade postFacade;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    void test1() {
        UserSave userSave = new UserSave("정연호", "fnelclsrn@gmail.com", "fnelclsrn", "123", "010-000-000");
        Long userId = userService.save(userSave);
        UserEntity user = userService.getUser(userId);

        UserSave user2Save = new UserSave("김연호", "fnelclsrn@gmail.com", "fnelclsrn1", "123", "010-000-000");
        Long user2Id = userService.save(user2Save);
        UserEntity user2 = userService.getUser(user2Id);
        PostSave postSave = new PostSave("안녕하세요", "반가워요");
        Long postId = postService.save(postSave, user).getPostId();

        CommentSave comment1 = new CommentSave("저도 반가워요");
        Long commentId = postFacade.writeComment(comment1, user2, postId);

        entityManager.flush();
        CommentDto comment = commentService.getComment(commentId);
        assertThat(comment.getContent().equals("저도 반가워요")).isTrue();
        assertThat(comment.getUser().getUserId().equals(user2Id)).isTrue();
        assertThat(comment.getPostId().equals(postId)).isTrue();
    }

    @Test
    void test2() {
        UserSave userSave = new UserSave("정연호", "fnelclsrn@gmail.com", "fnelclsrn", "123", "010-000-000");
        Long userId = userService.save(userSave);
        UserEntity user = userService.getUser(userId);

        UserSave user2Save = new UserSave("김연호", "fnelclsrn@gmail.com", "fnelclsrn1", "123", "010-000-000");
        Long user2Id = userService.save(user2Save);
        UserEntity user2 = userService.getUser(user2Id);
        PostSave postSave = new PostSave("안녕하세요", "반가워요");
        Long postId = postService.save(postSave, user).getPostId();

        CommentSave comment1 = new CommentSave("저도 반가워요");
        CommentSave comment2 = new CommentSave("혹시 이름이 어떻게 되세요?");
        Long comment1Id = postFacade.writeComment(comment1, user2, postId);
        Long comment2Id = postFacade.writeComment(comment2, user2, postId);

        entityManager.flush();
        List<CommentDto> allComments = postFacade.getAllComments(postId);
        assertThat(allComments.size() == 2).isTrue();
        assertThat(allComments.get(0).getCommentId().equals(comment1Id)).isTrue();
        assertThat(allComments.get(0).getContent().equals("저도 반가워요")).isTrue();
        assertThat(allComments.get(0).getUser().getUserId().equals(user2Id)).isTrue();
        assertThat(allComments.get(0).getPostId().equals(postId)).isTrue();
        assertThat(allComments.get(1).getCommentId().equals(comment2Id)).isTrue();
        assertThat(allComments.get(1).getContent().equals("혹시 이름이 어떻게 되세요?")).isTrue();
        assertThat(allComments.get(1).getUser().getUserId().equals(user2Id)).isTrue();
        assertThat(allComments.get(1).getPostId().equals(postId)).isTrue();
    }

    @Test
    void test3() {
        UserSave userSave = new UserSave("정연호", "fnelclsrn@gmail.com", "fnelclsrn", "123", "010-000-000");
        Long userId = userService.save(userSave);
        UserEntity user = userService.getUser(userId);

        UserSave user2Save = new UserSave("김연호", "fnelclsrn@gmail.com", "fnelclsrn1", "123", "010-000-000");
        Long user2Id = userService.save(user2Save);
        UserEntity user2 = userService.getUser(user2Id);
        PostSave postSave = new PostSave("안녕하세요", "반가워요");
        Long postId = postService.save(postSave, user).getPostId();

        CommentSave comment1 = new CommentSave("저도 반가워요");
        Long comment1Id = postFacade.writeComment(comment1, user2, postId);
        CommentUpdate commentUpdate = new CommentUpdate("저도 반가워요. 성함이 어떻게 되세요?");
        postFacade.updateComment(commentUpdate, comment1Id, user2);
        entityManager.flush();

        CommentDto comment = commentService.getComment(comment1Id);
        assertThat(comment.getContent().equals("저도 반가워요. 성함이 어떻게 되세요?")).isTrue();
    }

    @Test
    void test4() {
        UserSave userSave = new UserSave("정연호", "fnelclsrn@gmail.com", "fnelclsrn", "123", "010-000-000");
        Long userId = userService.save(userSave);
        UserEntity user = userService.getUser(userId);

        UserSave user2Save = new UserSave("김연호", "fnelclsrn@gmail.com", "fnelclsrn1", "123", "010-000-000");
        Long user2Id = userService.save(user2Save);
        UserEntity user2 = userService.getUser(user2Id);
        PostSave postSave = new PostSave("안녕하세요", "반가워요");
        Long postId = postService.save(postSave, user).getPostId();

        CommentSave comment1 = new CommentSave("저도 반가워요");
        Long comment1Id = postFacade.writeComment(comment1, user2, postId);
        CommentUpdate commentUpdate = new CommentUpdate("저도 반가워요. 성함이 어떻게 되세요?");

        assertThatThrownBy(() -> postFacade.updateComment(commentUpdate, comment1Id, user))
                .isInstanceOf(NotCommentAuthorException.class);
    }

    @Test
    void test5() {
        UserSave userSave = new UserSave("정연호", "fnelclsrn@gmail.com", "fnelclsrn", "123", "010-000-000");
        Long userId = userService.save(userSave);
        UserEntity user = userService.getUser(userId);

        UserSave user2Save = new UserSave("김연호", "fnelclsrn@gmail.com", "fnelclsrn1", "123", "010-000-000");
        Long user2Id = userService.save(user2Save);
        UserEntity user2 = userService.getUser(user2Id);
        PostSave postSave = new PostSave("안녕하세요", "반가워요");
        Long postId = postService.save(postSave, user).getPostId();

        CommentSave comment1 = new CommentSave("저도 반가워요");
        Long comment1Id = postFacade.writeComment(comment1, user2, postId);

        postFacade.deleteComment(comment1Id, user2);
        entityManager.flush();
        List<CommentDto> allComments = postFacade.getAllComments(postId);

        assertThat(allComments.size() == 0).isTrue();
    }

    @Test
    void test6() {
        UserSave userSave = new UserSave("정연호", "fnelclsrn@gmail.com", "fnelclsrn", "123", "010-000-000");
        Long userId = userService.save(userSave);
        UserEntity user = userService.getUser(userId);

        UserSave user2Save = new UserSave("김연호", "fnelclsrn@gmail.com", "fnelclsrn1", "123", "010-000-000");
        Long user2Id = userService.save(user2Save);
        UserEntity user2 = userService.getUser(user2Id);
        PostSave postSave = new PostSave("안녕하세요", "반가워요");
        Long postId = postService.save(postSave, user).getPostId();

        CommentSave comment1 = new CommentSave("저도 반가워요");
        Long comment1Id = postFacade.writeComment(comment1, user2, postId);

        assertThatThrownBy(() -> postFacade.deleteComment(comment1Id, user))
                .isInstanceOf(NotCommentAuthorException.class);

    }

    @Test
    void test7() {
        UserSave userSave = new UserSave("정연호", "fnelclsrn@gmail.com", "fnelclsrn", "123", "010-000-000");
        Long userId = userService.save(userSave);
        UserEntity user = userService.getUser(userId);

        UserSave user2Save = new UserSave("김연호", "fnelclsrn@gmail.com", "fnelclsrn1", "123", "010-000-000");
        Long user2Id = userService.save(user2Save);
        UserEntity user2 = userService.getUser(user2Id);
        PostSave postSave = new PostSave("안녕하세요", "반가워요");
        Long postId = postService.save(postSave, user).getPostId();

        CommentSave comment1 = new CommentSave("저도 반가워요");
        CommentSave comment2 = new CommentSave("몇살이세요?");
        Long comment1Id = postFacade.writeComment(comment1, user2, postId);
        Long comment2Id = postFacade.writeComment(comment2, user2, postId);

        postFacade.deleteComment(comment1Id, user2);
        List<CommentDto> allComments = postFacade.getAllComments(postId);

        assertThat(allComments.size() == 1);
        assertThat(allComments.get(0).getCommentId().equals(comment2Id)).isTrue();
        assertThat(allComments.get(0).getContent().equals("몇살이세요?")).isTrue();
        assertThat(allComments.get(0).getUser().getUserId().equals(user2Id)).isTrue();
        assertThat(allComments.get(0).getPostId().equals(postId)).isTrue();
    }

    @Test
    void test8() {
        UserSave userSave = new UserSave("정연호", "fnelclsrn@gmail.com", "fnelclsrn", "123", "010-000-000");
        Long userId = userService.save(userSave);
        UserEntity user = userService.getUser(userId);

        UserSave user2Save = new UserSave("김연호", "fnelclsrn@gmail.com", "fnelclsrn1", "123", "010-000-000");
        Long user2Id = userService.save(user2Save);
        UserEntity user2 = userService.getUser(user2Id);
        PostSave postSave = new PostSave("안녕하세요", "반가워요");
        Long postId = postService.save(postSave, user).getPostId();

        CommentSave comment1 = new CommentSave("저도 반가워요");
        CommentSave comment2 = new CommentSave("몇살이세요?");
        Long comment1Id = postFacade.writeComment(comment1, user2, postId);
        Long comment2Id = postFacade.writeComment(comment2, user2, postId);

        postFacade.deleteComment(comment1Id, user2);
        postFacade.restoreCommnet(comment1Id);

        entityManager.flush();

        List<CommentDto> allComments = postFacade.getAllComments(postId);
        assertThat(allComments.size() == 2);
        assertThat(allComments.get(0).getCommentId().equals(comment1Id)).isTrue();
        assertThat(allComments.get(0).getContent().equals("저도 반가워요")).isTrue();
        assertThat(allComments.get(0).getUser().getUserId().equals(user2Id)).isTrue();
        assertThat(allComments.get(0).getPostId().equals(postId)).isTrue();
        assertThat(allComments.get(1).getCommentId().equals(comment2Id)).isTrue();
        assertThat(allComments.get(1).getContent().equals("몇살이세요?")).isTrue();
        assertThat(allComments.get(1).getUser().getUserId().equals(user2Id)).isTrue();
        assertThat(allComments.get(1).getPostId().equals(postId)).isTrue();
    }

    @Test
    void test9() {
        UserSave userSave = new UserSave("정연호", "fnelclsrn@gmail.com", "fnelclsrn", "123", "010-000-000");
        Long userId = userService.save(userSave);
        UserEntity user = userService.getUser(userId);

        UserSave user2Save = new UserSave("김연호", "fnelclsrn@gmail.com", "fnelclsrn1", "123", "010-000-000");
        Long user2Id = userService.save(user2Save);
        UserEntity user2 = userService.getUser(user2Id);
        PostSave postSave = new PostSave("안녕하세요", "반가워요");
        Long postId = postService.save(postSave, user).getPostId();

        CommentSave comment1 = new CommentSave("저도 반가워요");
        Long comment1Id = postFacade.writeComment(comment1, user2, postId);

        CommentSave comment2 = new CommentSave("몇살이세요?", comment1Id);
        CommentSave comment3 = new CommentSave("저랑 친해져요!", comment1Id);
        Long comment2Id = postFacade.addReply(comment2, user2, postId);
        Long comment3Id = postFacade.addReply(comment3, user2, postId);

        entityManager.flush();
        CommentDto comment = commentService.getComment(comment1Id);
        assertThat(comment.getContent().equals("저도 반가워요")).isTrue();
        assertThat(comment.getChildren().size() == 2).isTrue();
        assertThat(comment.getChildren().get(0).getContent().equals("몇살이세요?")).isTrue();
        assertThat(comment.getChildren().get(0).getCommentId().equals(comment2Id)).isTrue();
        assertThat(comment.getChildren().get(0).getPostId().equals(postId)).isTrue();
        assertThat(comment.getChildren().get(0).getUser().getUserId().equals(user2Id)).isTrue();
        assertThat(comment.getChildren().get(1).getContent().equals("저랑 친해져요!")).isTrue();
        assertThat(comment.getChildren().get(1).getCommentId().equals(comment3Id)).isTrue();
        assertThat(comment.getChildren().get(1).getPostId().equals(postId)).isTrue();
        assertThat(comment.getChildren().get(1).getUser().getUserId().equals(user2Id)).isTrue();
    }

    @Test
    void test10() {
        UserSave userSave = new UserSave("정연호", "fnelclsrn@gmail.com", "fnelclsrn", "123", "010-000-000");
        Long userId = userService.save(userSave);
        UserEntity user = userService.getUser(userId);

        UserSave user2Save = new UserSave("김연호", "fnelclsrn@gmail.com", "fnelclsrn1", "123", "010-000-000");
        Long user2Id = userService.save(user2Save);
        UserEntity user2 = userService.getUser(user2Id);
        PostSave postSave = new PostSave("안녕하세요", "반가워요");
        Long postId = postService.save(postSave, user).getPostId();

        CommentSave comment1 = new CommentSave("저도 반가워요");
        Long comment1Id = postFacade.writeComment(comment1, user2, postId);

        CommentSave comment2 = new CommentSave("몇살이세요?", comment1Id);
        CommentSave comment3 = new CommentSave("저랑 친해져요!", comment1Id);
        CommentSave comment4 = new CommentSave("저는 부천에 살아요!", comment1Id);
        Long comment2Id = postFacade.addReply(comment2, user2, postId);
        Long comment3Id = postFacade.addReply(comment3, user2, postId);
        Long comment4Id = postFacade.addReply(comment4, user2, postId);

        postFacade.deleteComment(comment1Id, user2);

        entityManager.flush();

        PostDto post = postService.getPost(postId);
        List<CommentDto> comment = post.getComments();

        //소프트딜리트 된 것도 post의 comment로 따라온다.
        assertThat(comment.size() == 1).isTrue();
        assertThat(comment.get(0).isDeleted()).isTrue();

        assertThat(comment.get(0).getContent().equals("저도 반가워요")).isTrue();
        assertThat(comment.get(0).getCommentId().equals(comment1Id)).isTrue();
        assertThat(comment.get(0).getUser().getUserId().equals(user2Id)).isTrue();

        List<CommentDto> children = comment.get(0).getChildren();
        assertThat(children.size() == 3).isTrue();

        assertThat(children.get(0).getContent().equals("몇살이세요?")).isTrue();
        assertThat(children.get(0).getCommentId().equals(comment2Id)).isTrue();
        assertThat(children.get(0).getUser().getUserId().equals(user2Id)).isTrue();
        assertThat(children.get(0).isDeleted()).isFalse();

        assertThat(children.get(1).getContent().equals("저랑 친해져요!")).isTrue();
        assertThat(children.get(1).getCommentId().equals(comment3Id)).isTrue();
        assertThat(children.get(1).getUser().getUserId().equals(user2Id)).isTrue();
        assertThat(children.get(1).isDeleted()).isFalse();

        assertThat(children.get(2).getContent().equals("저는 부천에 살아요!")).isTrue();
        assertThat(children.get(2).getCommentId().equals(comment4Id)).isTrue();
        assertThat(children.get(2).getUser().getUserId().equals(user2Id)).isTrue();
        assertThat(children.get(2).isDeleted()).isFalse();
    }


}