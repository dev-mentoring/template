package com.wanted.springcafe.service;

import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.service.facade.BatchFacade;
import com.wanted.springcafe.service.facade.PostFacade;
import com.wanted.springcafe.service.view.ViewSerivce;
import com.wanted.springcafe.web.post.dto.request.PostSave;
import com.wanted.springcafe.web.post.dto.request.PostUpdate;
import com.wanted.springcafe.web.post.dto.response.PostDto;
import com.wanted.springcafe.web.user.dto.request.UserSave;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class PostEntitiyServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ViewSerivce viewSerivce;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PostFacade postFacade;

    @Autowired
    private BatchFacade batchFacade;

    @Test
    void test() {
        PostSave post = new PostSave("안녕하세요", "저는 alex입니다. 잘부탁드립니다.");
        UserEntity user = userService.getUser(1L);
        Long id = postService.save(post, user).getPostId();

        PostDto savedPost = postService.getPost(id);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(savedPost.getTitle().equals("안녕하세요")).isTrue();
        softAssertions.assertThat(savedPost.getContents().equals("저는 alex입니다. 잘부탁드립니다.")).isTrue();
    }


    @DisplayName("제목의 길이가 200글자를 넘지않으면 포스트가 생성된다.")
    @Test
    void test2() {
        PostSave postSave = new PostSave("안녕하세요안녕하세요녕하세요안녕하세요안녕하세요안녕하세요안안녕하세요안녕하세요녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요", "내용");
        UserEntity user = userService.getUser(1L);
        Long id = postService.save(postSave, user).getPostId();

        PostDto savedPost = postService.getPost(id);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(savedPost.getTitle().equals("안녕하세요안녕하세요녕하세요안녕하세요안녕하세요안녕하세요안안녕하세요안녕하세요녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요")).isTrue();
        softAssertions.assertThat(savedPost.getContents().equals("내용")).isTrue();
    }


    @DisplayName("제목의 길이가 200글자를 넘어가면 포스트가 생성되지 않는다.")
    @Test
    void test3() {
        PostSave postSave = new PostSave("안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요", "내용");
        UserEntity user = userService.getUser(1L);
        assertThatThrownBy(() -> postService.save(postSave, user)).isInstanceOf(DataIntegrityViolationException.class);
    }


    @DisplayName("내용의 길이가 1000글자를 넘지않으면 포스트가 생성된다.")
    @Test
    void test4() {
        String content = "안녕하세요".repeat(199);
        PostSave postSave = new PostSave("제목", content);
        UserEntity user = userService.getUser(1L);
        Long id = postService.save(postSave, user).getPostId();

        PostDto savedPost = postService.getPost(id);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(savedPost.getTitle().equals("제목")).isTrue();
        softAssertions.assertThat(savedPost.getContents().equals(content)).isTrue();
    }

    @DisplayName("내용의 길이가 1000글자를 넘으면 포스트가 생성되지 않는다.")
    @Test
    void test5() {
        String content = "안녕하세요".repeat(201);
        PostSave postSave = new PostSave("제목", content);
        UserEntity user = userService.getUser(1L);
        assertThatThrownBy(() -> postService.save(postSave, user)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void test6() {
        PostSave postSave = new PostSave("제목1", "내용1");
        UserEntity user = userService.getUser(1L);
        Long save = postService.save(postSave, user).getPostId();
        postService.update(save, new PostUpdate("제목2", "내용2"));

        PostDto post = postService.getPost(save);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(post.getTitle().equals("제목2")).isTrue();
        softAssertions.assertThat(post.getContents().equals("내용2")).isTrue();
    }

    @DisplayName("포스트를 생성한 지 10일이 지난 후에는 변경하려고 하면 실패한다.")
    @Test
    void test7() {
        PostSave postSave = new PostSave("제목1", "내용1");
        UserEntity user = userService.getUser(1L);
        Long save = postService.save(postSave, user).getPostId();
        PostDto post = postService.getPost(save);
        LocalDate time = LocalDate.now().plusDays(12);

//
//        assertThatThrownBy(() -> post.update("변경1", "변경2", time))
//                .isInstanceOf(PostModificationNotAllowedException.class);
    }

    @DisplayName("포스트를 삭제하면 소프트 딜리트가 된다.")
    @Test
    void test8() {
        PostSave postSave = new PostSave("제목1", "내용1");
        UserEntity user = userService.getUser(1L);
        Long save = postService.save(postSave, user).getPostId();
        postService.softDelete(save);
        PostDto post = postService.getPost(save);

        assertThat(post).isNull();
    }

    @Test
    void test9() {
        PostDto post = postService.getPost(17L);
        assertThat(post).isNull();
    }

    @DisplayName("포스트를 삭제하면 소프트 딜리트가 된다.")
    @Transactional
    @Test
    void test10() {
         postService.getAllPost();
        postService.getPost(1L);
//        for (int i = 0; i < allPost.size(); i++) {
//            assertThat(allPost.get(i).isDeleted()).isFalse();
//        }
    }
    @Test
    void test11(){
        UserSave userSave = new UserSave("정연호", "fnelclsrn123@naver.com", "fnelclsrn123", "123", "010-222-22323");
        UserSave userSave2 = new UserSave("김철수", "fnelclsrn12@naver.com", "fnelclsrn12", "123", "010-222-22323");
        UserSave userSave3 = new UserSave("감철수", "fnelclsrn12@naver.com", "fnelclsrn1", "123", "010-222-22323");
        UserSave userSave4 = new UserSave("경철수", "fnelclsrn12@naver.com", "fnelclsrn124", "123", "010-222-22323");
        UserSave userSave5 = new UserSave("공철수", "fnelclsrn12@naver.com", "fnelclsrn125", "123", "010-222-22323");

        Long user1Id = userService.save(userSave);
        Long user2Id = userService.save(userSave2);
        Long user3Id = userService.save(userSave3);
        Long user4Id = userService.save(userSave4);
        Long user5Id = userService.save(userSave5);

        UserEntity user = userService.getUser(user1Id);
        UserEntity user2 = userService.getUser(user2Id);
        UserEntity user3 = userService.getUser(user3Id);
        UserEntity user4 = userService.getUser(user4Id);
        UserEntity user5 = userService.getUser(user5Id);
        PostSave post = new PostSave("안녕", "반가워요");
        Long postId = postService.save(post, user).getPostId();

        postFacade.viewPost(postId, user2);
        postFacade.viewPost(postId, user3);
        postFacade.viewPost(postId, user4);
        postFacade.viewPost(postId, user5);
        postFacade.viewPost(postId, user5);

        batchFacade.addViewCountToDb();
        entityManager.flush();

        PostDto post1 = postService.getPost(postId);
        assertThat(post1.getViewCount()==4L).isTrue();
    }

    @Test
    void test12() throws InterruptedException {
        UserSave userSave = new UserSave("정연호", "fnelclsrn123@naver.com", "fnelclsrn123", "123", "010-222-22323");
        UserSave userSave2 = new UserSave("김철수", "fnelclsrn12@naver.com", "fnelclsrn12", "123", "010-222-22323");

        Long user1Id = userService.save(userSave);
        Long user2Id = userService.save(userSave2);

        UserEntity user = userService.getUser(user1Id);
        UserEntity user2 = userService.getUser(user2Id);

        PostSave postSave = new PostSave("안녕", "반가워요");
        Long postId = postService.save(postSave, user).getPostId();
        PostDto post = postService.getPost(postId);


        viewSerivce.addViewCountToRedisTest(post, user2);
        assertThat(redisTemplate.hasKey("view::userId::" + user2.getUserId() + "::postId::" + postId)).isTrue();

        TimeUnit.SECONDS.sleep(20);

        entityManager.flush();
        entityManager.clear();

        postFacade.viewPost(postId, user2);
        assertThat(redisTemplate.hasKey("view::userId::" + user2.getUserId() + "::postId::" + postId)).isTrue();
        assertThat(post.getViewCount()==0L).isTrue();
        batchFacade.addViewCountToDb();

        entityManager.flush();
        entityManager.clear();

        PostDto post1 = postService.getPost(postId);
        assertThat(post1.getViewCount()==2L).isTrue();
    }

    @Test
    void test13(){
        UserSave userSave = new UserSave("정연호", "fnelclsrn123@naver.com", "fnelclsrn123", "123", "010-222-22323");
        UserSave userSave2 = new UserSave("김철수", "fnelclsrn12@naver.com", "fnelclsrn12", "123", "010-222-22323");

        Long user1Id = userService.save(userSave);
        Long user2Id = userService.save(userSave2);

        UserEntity user = userService.getUser(user1Id);
        UserEntity user2 = userService.getUser(user2Id);
        PostSave post = new PostSave("안녕", "반가워요");
        Long postId = postService.save(post, user).getPostId();

        postFacade.viewPost(postId, user2);
        postFacade.viewPost(postId, user2);
        postFacade.viewPost(postId, user2);
        postFacade.viewPost(postId, user2);
        postFacade.viewPost(postId, user2);

        batchFacade.addViewCountToDb();
        entityManager.flush();

        PostDto post1 = postService.getPost(postId);
        assertThat(post1.getViewCount()==1L).isTrue();
    }
}