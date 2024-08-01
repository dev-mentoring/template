package com.wanted.springcafe.web.post;

import com.wanted.springcafe.service.PostService;
import com.wanted.springcafe.service.facade.PostFacade;
import com.wanted.springcafe.web.post.dto.request.PostSave;
import com.wanted.springcafe.web.post.dto.response.PostListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.wanted.springcafe.constant.Post.BLOCK_LIMIT;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {

    private final PostService postService;
    private final PostFacade postFacade;

    @GetMapping("list/paging")
    public void paging(@PageableDefault(page = 1) Pageable pageable) {
        Page<PostListDto> paging =
                postService.paging(pageable);

        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / BLOCK_LIMIT))) - 1) * BLOCK_LIMIT + 1;
        int endPage = Math.min((startPage + BLOCK_LIMIT - 1), paging.getTotalPages());
    }

    @PostMapping
    public void save(@RequestPart PostSave post, @RequestPart MultipartFile file) throws IOException {
        postFacade.writePost(post, file);
    }
}
