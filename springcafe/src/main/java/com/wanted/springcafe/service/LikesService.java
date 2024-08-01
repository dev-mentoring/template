package com.wanted.springcafe.service;

import com.wanted.springcafe.domain.Likes.LikesEntity;
import com.wanted.springcafe.domain.Likes.LikesRepository;
import com.wanted.springcafe.domain.Likes.dto.LikesCountDto;
import com.wanted.springcafe.domain.Likes.dto.LikesDto;
import com.wanted.springcafe.domain.notification.NotificationEntity;
import com.wanted.springcafe.domain.post.PostEntity;
import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.exception.SelfLikeNotAllowedException;
import com.wanted.springcafe.web.likes.dto.response.LikesResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikesService {
    private final String hashKey = "likes";

    private final RedisTemplate redisTemplate;
    private final NotificationService notificationService;
    private final LikesRepository likesRepository;

    @Transactional
    public void addLikesCountToRedis(PostEntity post, UserEntity user) {

        if (user.getUserId().equals(post.getUser().getUserId())) {
            throw new SelfLikeNotAllowedException("자신이 작성한 포스트는 좋아요를 누를 수 없습니다.");
        }

        Long postId = post.getPostId();
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String postKey = "postId::" + postId;
        String userLikeKey = "userId::" + user.getUserId() + "::postId::" + postId;

        redisTemplate.opsForValue().set(userLikeKey, hashKey, 1);
        hashOperations.increment(postKey, hashKey, 1L);
        notificationService.send(new NotificationEntity(post.getUser(), post.getUser().getUsername() + "님 좋아요가 1올랐습니다."));
    }

    @Transactional
    public void cancelLikes(PostEntity post, UserEntity user) {

        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        Long postId = post.getPostId();
        String userLikeKey = "userId::" + user.getUserId() + "::postId::" + postId;

        if (hashOperations.hasKey(userLikeKey, hashKey)) {
            String postKey = "postId::" + postId;
            hashOperations.increment(postKey, hashKey, -1L);
            redisTemplate.delete(userLikeKey);
        } else {
            decreaseLikesCountToRedis(postId, user.getUserId());
        }

        notificationService.send(new NotificationEntity(post.getUser(), post.getUser().getUsername() + "님 좋아요가 1 감소했습니다."));
    }

    public LikesResponseDto getLikes(Long postId, Long userId) {

        LikesEntity likesEntity = likesRepository.findByPost_PostIdAndUser_UserId(postId, userId).orElseThrow();
        return new LikesResponseDto(
                likesEntity.getLikesId(),
                likesEntity.getPost().getPostId(),
                likesEntity.getUser().getUserId());
    }

    public void getLikesDataFromRedis(
            List<LikesDto> likesDtos,
            List<LikesCountDto> likesCountDtos,
            List<LikesDto> cancelLikesDto,
            List<LikesCountDto> cancelLikesCountDto) {

        ScanOptions scanOption1 = ScanOptions.scanOptions().match("postId*").count(10).build();
        Cursor<byte[]> firstKey = redisTemplate.getConnectionFactory().getConnection().scan(scanOption1);

        ScanOptions scanOption2 = ScanOptions.scanOptions().match("userId*").count(10).build();
        Cursor<byte[]> secondKey = redisTemplate.getConnectionFactory().getConnection().scan(scanOption2);

        ScanOptions scanOption3 = ScanOptions.scanOptions().match("delete::postId*").count(10).build();
        Cursor<byte[]> thirdKey = redisTemplate.getConnectionFactory().getConnection().scan(scanOption3);

        ScanOptions scanOption4 = ScanOptions.scanOptions().match("delete::userId*").count(10).build();
        Cursor<byte[]> fourthKey = redisTemplate.getConnectionFactory().getConnection().scan(scanOption4);

        while (firstKey.hasNext()) {
            String data = new String(firstKey.next());
            getLikesCount(likesCountDtos, data);
        }

        while (secondKey.hasNext()) {
            String data = new String(secondKey.next());
            getLikesLog(likesDtos, data);
        }

        while (thirdKey.hasNext()) {
            String data = new String(thirdKey.next());
            getLikesCancellationCount(cancelLikesCountDto, data);
        }

        while (fourthKey.hasNext()) {
            String data = new String(fourthKey.next());
            getLikesCancellationLog(cancelLikesDto, data);
        }
    }

    private void decreaseLikesCountToRedis(Long postId, Long userId) {

        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String postKey = "delete::postId::" + postId;
        String userLikeKey = "delete::userId::" + userId + "::postId::" + postId;
        redisTemplate.opsForValue().set(userLikeKey, hashKey, 1);
        hashOperations.increment(postKey, hashKey, 1L);
    }


    private void getLikesLog(List<LikesDto> likesDtos, String data) {

        long userId = Long.parseLong(data.split("::")[1]);
        long postId = Long.parseLong(data.split("::")[3]);
        redisTemplate.delete(data);
        likesDtos.add(new LikesDto(postId, userId));
    }

    private void getLikesCount(List<LikesCountDto> likesCountDtos, String data) {

        long postId = Long.parseLong(data.split("::")[1]);
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String postKey = "postId::" + postId;
        Object value = hashOperations.get(postKey, hashKey);
        long count = value != null ? Long.parseLong(value.toString()) : 0L;

        likesCountDtos.add(new LikesCountDto(postId, count));
        redisTemplate.opsForHash().delete(data, hashKey);
    }

    private void getLikesCancellationLog(List<LikesDto> cancelLikesDto, String data) {

        long userId = Long.parseLong(data.split("::")[2]);
        long postId = Long.parseLong(data.split("::")[4]);
        redisTemplate.delete(data);
        cancelLikesDto.add(new LikesDto(postId, userId));
    }

    private void getLikesCancellationCount(List<LikesCountDto> cancelLikesCountDto, String data) {

        long postId = Long.parseLong(data.split("::")[2]);
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String postKey = "delete::postId::" + postId;
        Object value = hashOperations.get(postKey, hashKey);
        long count = value != null ? Long.parseLong(value.toString()) : 0L;
        cancelLikesCountDto.add(new LikesCountDto(postId, count));
        redisTemplate.opsForHash().delete(data, hashKey);
    }
}
