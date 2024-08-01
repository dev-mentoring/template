package com.wanted.springcafe.service;

import com.wanted.springcafe.domain.post.PostEntity;
import com.wanted.springcafe.domain.post.PostRepository;
import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.constant.Filter;
import com.wanted.springcafe.utility.FilterManager;
import com.wanted.springcafe.web.post.dto.PostMapper;
import com.wanted.springcafe.web.post.dto.request.PostSave;
import com.wanted.springcafe.web.post.dto.request.PostUpdate;
import com.wanted.springcafe.web.post.dto.response.PostDto;
import com.wanted.springcafe.web.post.dto.response.PostListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.wanted.springcafe.constant.Post.HARD_DELETED_THRESHOLD;
import static com.wanted.springcafe.constant.Post.POST_LIMIT;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final FilterManager filterManager;

    public PostEntity save(PostSave postSave, UserEntity user){
        PostEntity post = new PostEntity(postSave.getTitle(), postSave.getContent());
        post.setUser(user);
        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public PostDto getPost(Long id){
        PostEntity post = postRepository.getPost(id, false);
        return PostMapper.toDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostEntity> getAllPost(){
        List<PostEntity> allPost = postRepository.findAllByIsDeleted(false);

        return allPost;
    }

    @Transactional
    public void update(Long id, PostUpdate postUpdate){
        PostEntity post = postRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트가 존재하지 않습니다."));

        post.update(
                postUpdate.getTitle(),
                postUpdate.getContent(),
                LocalDate.now());
    }

    public void softDelete(Long id){
        postRepository.deleteById(id);
    }

    public PostEntity getSoftDeletedPost(Long id){
        filterManager.enableFilter(
                Filter.POST_FILTER,
                Filter.FILTER_PARAM,
                true);

        PostEntity post = filterManager.getEntityManager().createQuery("SELECT p FROM PostEntity p WHERE p.postId = :id", PostEntity.class)
                .setParameter("id", id)
                .getSingleResult();
        filterManager.disableFilter(Filter.POST_FILTER);
        return post;

    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void hardDeleteOldSoftDeletedPosts(){
        hardDelete(LocalDate.now());
    }

    public void hardDelete(LocalDate now) {
        LocalDate thresholdDate = now.minusDays(HARD_DELETED_THRESHOLD);
        List<Long> ids = postRepository.findIdByDeletedAt(thresholdDate);
        postRepository.deleteAllByInQuery(ids);
    }

    public Page<PostListDto> paging(Pageable pageable){
        int page = pageable.getPageNumber() -1;
        Page<PostEntity> postPages = postRepository.findAll(PageRequest.of(page, POST_LIMIT, Sort.by(Sort.Direction.DESC, "id")));

        return postPages.map(i->new PostListDto(i.getPostId(), i.getTitle(), i.getPublishDate()));
    }

}


