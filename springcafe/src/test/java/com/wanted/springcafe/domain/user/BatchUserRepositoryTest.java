package com.wanted.springcafe.domain.user;

import com.wanted.springcafe.utility.CsvReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class BatchUserRepositoryTest {

    @Autowired
    private BatchUserRepository batchUserRepository;

    @Test
    public void insert() throws IOException {
        CsvReader csvReader = new CsvReader();
        List<UserEntity> users = csvReader.readUser();
        batchUserRepository.insert(users);
    }

}