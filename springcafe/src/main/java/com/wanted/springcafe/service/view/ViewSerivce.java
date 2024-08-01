package com.wanted.springcafe.service.view;

import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.web.post.dto.response.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ViewSerivce {

    private final String hashKey = "visit";
    private final RedisTemplate redisTemplate;

    public void addViewCountToRedisWithMidnightExpiration(PostDto post, UserEntity user) {

        //포스트 작성자와 글 조회를 한 유저가 동일하면 조회수에 반영하지 않는다.
        if(post.getUser().getUserId().equals(user.getUserId())){
            return;
        }
        Long postId = post.getPostId();
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String postKey = "view::postId::" + postId;
        String userViewKey = "view::userId::" + user.getUserId() + "::postId::" + postId;
        if (!redisTemplate.hasKey(userViewKey)) {
            redisTemplate.opsForValue().set(userViewKey, hashKey, 1);
            hashOperations.increment(postKey, hashKey, 1L);
        }
        // Expire the key at midnight
        redisTemplate.expireAt(userViewKey, getExpireDateAtMidnight());
    }

    public void addViewCountToRedisTest(PostDto post, UserEntity user) {


        Long postId = post.getPostId();

        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String postKey = "view::postId::" + postId;
        String userViewKey = "view::userId::" + user.getUserId() + "::postId::" + postId;
        if (!redisTemplate.hasKey(userViewKey)) {
            redisTemplate.opsForValue().set(userViewKey, hashKey, 1);
            hashOperations.increment(postKey, hashKey, 1L);
        }

        // 조회수가 하루 뒤에 counting되는지 확인하는 테스트 목적으로, 레디스의 키가 1분뒤 사라지도록 설정
        redisTemplate.expireAt(userViewKey, getExpireDateAfterTenSeconds());
    }

    public void getViewCountFromRedis(List<ViewCountDto> addViewsDto) {
        ScanOptions scanOption1 = ScanOptions.scanOptions().match("view::postId*").count(10).build();
        Cursor<byte[]> key = redisTemplate.getConnectionFactory().getConnection().scan(scanOption1);

        while (key.hasNext()) {
            String data = new String(key.next());
            getViewCount(addViewsDto, data);
        }
    }

    private void getViewCount(List<ViewCountDto> addViewsDto, String data) {
        long postId = Long.parseLong(data.split("::")[2]);
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String postKey = "view::postId::" + postId;
        Object value = hashOperations.get(postKey, hashKey);
        long count = value != null ? Long.parseLong(value.toString()) : 0L;

        addViewsDto.add(new ViewCountDto(postId, count));
        redisTemplate.delete(data);
    }

    private Date getExpireDateAtMidnight() {
        LocalDateTime midnight = LocalDateTime.now().toLocalDate().plusDays(1).atStartOfDay();
        return Date.from(midnight.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date getExpireDateAfterTenSeconds() {
        LocalDateTime expirationTime = LocalDateTime.now().plusSeconds(10);
        return Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}

