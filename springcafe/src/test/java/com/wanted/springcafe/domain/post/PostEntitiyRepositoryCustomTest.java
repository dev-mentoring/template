package com.wanted.springcafe.domain.post;

import com.wanted.springcafe.service.PostService;
import com.wanted.springcafe.web.post.dto.response.PostListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
class PostEntitiyRepositoryCustomTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepositoryCustom postRepositoryCustom;

    @Test
    void test1() {
        List<PostListDto> list = postRepositoryCustom.search(1000L, "실시간", 10);
    }
}