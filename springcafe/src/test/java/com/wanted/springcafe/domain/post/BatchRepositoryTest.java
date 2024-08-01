package com.wanted.springcafe.domain.post;

import com.wanted.springcafe.domain.Likes.BatchLikesRepository;
import com.wanted.springcafe.domain.Likes.dto.LikesDto;
import com.wanted.springcafe.domain.user.BatchUserRepository;
import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.utility.CsvReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

@SpringBootTest
class BatchRepositoryTest {

    @Autowired
    private BatchPostRepository batchPostRepository;

    @Autowired
    private BatchUserRepository batchUserRepository;

    @Autowired
    private BatchLikesRepository batchLikesRepository;

    @Test
    void insertUser() throws IOException {

        CsvReader csvReader = new CsvReader();
        List<UserEntity> list = csvReader.readUser();
        batchUserRepository.insert(list);
    }

    @Test
    void insertPost() throws IOException {

        CsvReader csvReader = new CsvReader();
        List<PostEntity> list = csvReader.readPost();
        int batchSize = 500000 / 5;
        for (int i = 0; i < 5; i++) {
            int fromIndex = i * batchSize;
            int toIndex = (i == 4) ? list.size() : fromIndex + batchSize;
            List<PostEntity> subList = list.subList(fromIndex, toIndex);
            batchPostRepository.insert(subList);
        }
    }

    @Test
    void insertLikes() {
        List<LikesDto> list = new ArrayList<>();
        Set<String> uniqueLikes = new HashSet<>();
        Random rnd = new Random();

        for (int i = 0; i < 500000; i++) {
            Long userId = (long) (rnd.nextInt(1000) + 1);
            Long postId = (long) (rnd.nextInt(400000) + 1);
            String uniqueKey = userId + "-" + postId;
            if (!uniqueLikes.contains(uniqueKey)) {
                list.add(new LikesDto(userId, postId));
                uniqueLikes.add(uniqueKey);
            }
        }

        int batchSize = 500000 / 5;
        for (int i = 0; i < 5; i++) {
            int fromIndex = i * batchSize;
            int toIndex = (i == 4) ? list.size() : fromIndex + batchSize;
            List<LikesDto> sublist = list.subList(fromIndex, toIndex);
            batchLikesRepository.insert(sublist);
        }
    }
}