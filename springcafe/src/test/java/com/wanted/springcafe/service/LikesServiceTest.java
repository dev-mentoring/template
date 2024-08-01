package com.wanted.springcafe.service;

import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.exception.SelfLikeNotAllowedException;
import com.wanted.springcafe.service.facade.BatchFacade;
import com.wanted.springcafe.service.facade.PostFacade;
import com.wanted.springcafe.web.likes.dto.response.LikesResponseDto;
import com.wanted.springcafe.web.notification.dto.NotificationDto;
import com.wanted.springcafe.web.post.dto.request.PostSave;
import com.wanted.springcafe.web.post.dto.response.PostDto;
import com.wanted.springcafe.web.user.dto.request.UserSave;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class LikesServiceTest {

    private static final Logger log = LoggerFactory.getLogger(LikesServiceTest.class);
    @Autowired
    private LikesService likesService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PostFacade postFacade;

    @Autowired
    private BatchFacade batchFacade;

    @Test
    void test1() {
        UserEntity user = userService.getUser(100L);
        PostDto post = postService.getPost(201L);
        postFacade.likesPost(post.getPostId(), user);
        batchFacade.addLikesCountToDb();

        entityManager.flush();
        LikesResponseDto likes = likesService.getLikes(201L, user.getUserId());
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(likes).isNotNull();
        softAssertions.assertThat(likes.getPostId() == 201L).isTrue();
        softAssertions.assertThat(likes.getUserId() == 100L).isTrue();
    }

    @Test
    void test2() {

        UserSave userSave1 = new UserSave("정연호", "fnelclsrn@naver.com", "fnelclsrn", "1234", "010-000-0000");
        UserSave userSave2 = new UserSave("정철호", "fnelclsrn123@naver.com", "fnelclsrn123", "1234", "010-000-0000");
        UserSave userSave3 = new UserSave("정윤화", "fnelclsrn1234@naver.com", "fnelclsrn1234", "1234", "010-000-0000");
        UserSave userSave4 = new UserSave("정정화", "fnelclsrn1234@naver.com", "fnelclsrn1234", "1234", "010-000-0000");

        Long user1Id = userService.save(userSave1);
        Long user2Id = userService.save(userSave2);
        Long user3id = userService.save(userSave3);
        Long user4id = userService.save(userSave4);

        UserEntity user = userService.getUser(user1Id);
        UserEntity user2 = userService.getUser(user2Id);
        UserEntity user3 = userService.getUser(user3id);
        UserEntity user4 = userService.getUser(user4id);

        PostSave postSave = new PostSave("안녕하세요", "반갑습니다.");
        PostSave postSave2 = new PostSave("안녕하세요", "반갑습니다.");
        PostSave postSave3 = new PostSave("안녕하세요", "반갑습니다.");
        Long post1Id = postService.save(postSave, user).getPostId();
        Long post2Id = postService.save(postSave2, user).getPostId();
        Long post3Id = postService.save(postSave3, user).getPostId();
        entityManager.flush();

        postFacade.likesPost(post1Id, user2);
        postFacade.likesPost(post1Id, user3);
        postFacade.likesPost(post1Id, user4);

        postFacade.likesPost(post2Id, user2);
        postFacade.likesPost(post2Id, user3);
        postFacade.likesPost(post2Id, user4);

        postFacade.likesPost(post3Id, user2);
        postFacade.likesPost(post3Id, user3);
        postFacade.likesPost(post3Id, user4);

        batchFacade.addLikesCountToDb();
        entityManager.flush();

        PostDto post = postService.getPost(post1Id);
        PostDto post1 = postService.getPost(post2Id);
        PostDto post2 = postService.getPost(post3Id);

        assertThat(post.getLikeCount() == 3).isTrue();
        assertThat(post1.getLikeCount() == 3).isTrue();
        log.info("테스트 진행중");
        assertThat(post2.getLikeCount() == 3).isTrue();
    }

    @Test
    void test3() {

        UserSave userSave1 = new UserSave("김철수", "fnelclsrn@naver.com", "fnelclsrn", "1234", "010-000-0000");
        UserSave userSave2 = new UserSave("정철수", "fnelclsrn@naver.com", "fnelclsrn", "1234", "010-000-0000");

        Long user1Id = userService.save(userSave1);
        Long user2Id = userService.save(userSave2);

        UserEntity user = userService.getUser(user1Id);
        PostSave postSave = new PostSave("안녕하세요", "반갑습니다.");
        Long post1Id = postService.save(postSave, user).getPostId();

        UserEntity user2 = userService.getUser(user2Id);
        postFacade.likesPost(post1Id, user2);

        List<NotificationDto> notifiactions = notificationService.getAllNotifiactions(user1Id);
        NotificationDto notification = notifiactions.get(notifiactions.size() - 1);
        entityManager.flush();

        assertThat(notification).isNotNull();
        assertThat(notification.getUserName().equals("김철수")).isTrue();
        assertThat(notification.getUserId().equals(user1Id)).isTrue();
        assertThat(notification.getMessage().equals("김철수" + "님 좋아요가 1올랐습니다.")).isTrue();
    }

    @Test
    void test4() {

        UserSave userSave1 = new UserSave(
                "김철수",
                "fnelclsrn@naver.com",
                "fnelclsrn", "1234",
                "010-000-0000"
        );
        UserSave userSave2 = new UserSave(
                "정철수",
                "fnelclsrn@naver.com",
                "fnelclsrn",
                "1234",
                "010-000-0000"
        );

        Long user1Id = userService.save(userSave1);
        Long user2Id = userService.save(userSave2);

        UserEntity user = userService.getUser(user1Id);
        PostSave postSave = new PostSave("안녕하세요", "반갑습니다.");
        Long post1Id = postService.save(postSave, user).getPostId();

        UserEntity user2 = userService.getUser(user2Id);
        postFacade.likesPost(post1Id, user2);
        batchFacade.addLikesCountToDb();

        postFacade.cancelLikes(post1Id, user2);
        batchFacade.addLikesCountToDb();
        List<NotificationDto> notifiactions = notificationService.getAllNotifiactions(user1Id);
        NotificationDto notification = notifiactions.get(0);

        entityManager.flush();

        PostDto post = postService.getPost(post1Id);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(post.getLikeCount() == 0L).isTrue();
        softAssertions.assertThat(notification).isNotNull();
        softAssertions.assertThat(notification.getUserId() == user1Id).isTrue();
        softAssertions.assertThat(notification.getUserName().equals("김철수")).isTrue();
        softAssertions.assertThat(notification.getMessage().equals("김철수" + "님 좋아요가 1 감소했습니다.")).isTrue();

    }

    @Test
    void test5() {

        UserSave userSave1 = new UserSave("김철수", "fnelclsrn@naver.com", "fnelclsrn", "1234", "010-000-0000");
        Long user1Id = userService.save(userSave1);
        UserEntity user = userService.getUser(user1Id);
        PostSave postSave = new PostSave("안녕하세요", "반갑습니다.");
        Long post1Id = postService.save(postSave, user).getPostId();
        assertThatThrownBy(() ->
                postFacade.likesPost(post1Id, user)).isInstanceOf(SelfLikeNotAllowedException.class);
    }

    @Test
    void test6() {
        UserSave userSave1 = new UserSave("정연호", "fnelclsrn@naver.com", "fnelclsrn", "1234", "010-000-0000");
        UserSave userSave2 = new UserSave("정철호", "fnelclsrn123@naver.com", "fnelclsrn123", "1234", "010-000-0000");
        UserSave userSave3 = new UserSave("정윤화", "fnelclsrn1234@naver.com", "fnelclsrn1234", "1234", "010-000-0000");
        UserSave userSave4 = new UserSave("정정화", "fnelclsrn1234@naver.com", "fnelclsrn1234", "1234", "010-000-0000");

        Long user1Id = userService.save(userSave1);
        Long user2Id = userService.save(userSave2);
        Long user3id = userService.save(userSave3);
        Long user4id = userService.save(userSave4);

        UserEntity user = userService.getUser(user1Id);
        UserEntity user2 = userService.getUser(user2Id);
        UserEntity user3 = userService.getUser(user3id);
        UserEntity user4 = userService.getUser(user4id);

        PostSave postSave = new PostSave("안녕하세요", "반갑습니다.");
        PostSave postSave2 = new PostSave("안녕하세요", "반갑습니다.");
        PostSave postSave3 = new PostSave("안녕하세요", "반갑습니다.");
        Long post1Id = postService.save(postSave, user).getPostId();
        Long post2Id = postService.save(postSave2, user).getPostId();
        Long post3Id = postService.save(postSave3, user).getPostId();

        postFacade.likesPost(post1Id, user2);
        postFacade.likesPost(post1Id, user3);
        postFacade.likesPost(post1Id, user4);

        postFacade.likesPost(post2Id, user2);
        postFacade.likesPost(post2Id, user3);
        postFacade.likesPost(post2Id, user4);

        postFacade.likesPost(post3Id, user2);
        postFacade.likesPost(post3Id, user3);
        postFacade.likesPost(post3Id, user4);

        batchFacade.addLikesCountToDb();

        postFacade.cancelLikes(post1Id, user2);
        postFacade.cancelLikes(post1Id, user4);
        postFacade.cancelLikes(post2Id, user3);
        postFacade.cancelLikes(post3Id, user2);
        postFacade.cancelLikes(post3Id, user3);
        postFacade.cancelLikes(post3Id, user4);


        batchFacade.addLikesCountToDb();
        entityManager.flush();

        assertThatThrownBy(() -> likesService.getLikes(post1Id, user2.getUserId()))
                .isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> likesService.getLikes(post1Id, user4.getUserId()))
                .isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> likesService.getLikes(post2Id, user3.getUserId()))
                .isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> likesService.getLikes(post3Id, user2.getUserId()))
                .isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> likesService.getLikes(post3Id, user3.getUserId()))
                .isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> likesService.getLikes(post3Id, user4.getUserId()))
                .isInstanceOf(NoSuchElementException.class);

        PostDto post = postService.getPost(post1Id);
        PostDto post1 = postService.getPost(post2Id);
        PostDto post2 = postService.getPost(post3Id);

        assertThat(post.getLikeCount() == 1L).isTrue();
        assertThat(post1.getLikeCount() == 2L).isTrue();
        log.info("테스트 진행중");
        assertThat(post2.getLikeCount() == 0L).isTrue();
    }
}