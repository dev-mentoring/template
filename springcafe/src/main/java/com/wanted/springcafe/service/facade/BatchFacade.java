package com.wanted.springcafe.service.facade;

import com.wanted.springcafe.domain.Likes.BatchLikesRepository;
import com.wanted.springcafe.domain.Likes.dto.LikesCountDto;
import com.wanted.springcafe.domain.Likes.dto.LikesDto;
import com.wanted.springcafe.domain.post.BatchPostRepository;
import com.wanted.springcafe.service.LikesService;
import com.wanted.springcafe.service.view.ViewCountDto;
import com.wanted.springcafe.service.view.ViewSerivce;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Component
public class BatchFacade {

    private final EntityManager entityManager;
    private final BatchPostRepository batchPostRepository;
    private final BatchLikesRepository batchLikesRepository;
    private final ViewSerivce viewSerivce;
    private final LikesService likesService;

    public void addViewCountToDb() {
        List<ViewCountDto> addViewsDto = new ArrayList<>();
        viewSerivce.getViewCountFromRedis(addViewsDto);
        batchPostRepository.addViewCount(addViewsDto);
        entityManager.flush();
        entityManager.clear();
    }

    public void addLikesCountToDb() {
        List<LikesDto> addLikesDto = new ArrayList<>();
        List<LikesCountDto> addLikesCountDto = new ArrayList<>();
        List<LikesDto> cancelLikesDto = new ArrayList<>();
        List<LikesCountDto> cancelLikesCountDto = new ArrayList<>();
        likesService.getLikesDataFromRedis(addLikesDto, addLikesCountDto, cancelLikesDto, cancelLikesCountDto);

        batchLikesRepository.insert(addLikesDto);
        batchPostRepository.addLikesCount(addLikesCountDto);
        batchLikesRepository.delete(cancelLikesDto);
        batchPostRepository.decreaseLikesCount(cancelLikesCountDto);
        entityManager.flush();
        entityManager.clear();
    }

}
