package com.wanted.springcafe.utility;

import com.wanted.springcafe.domain.post.PostEntity;
import com.wanted.springcafe.domain.user.UserEntity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private final String POST_PATH = "C:\\Users\\정연호\\Desktop\\db데이터\\random_posts_ko.csv";
    private final String USER_PATH = "C:\\Users\\정연호\\Desktop\\db데이터\\random_user.csv";
    private final String COMMAN = ",";

    public List<PostEntity> readPost() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(POST_PATH, StandardCharsets.UTF_8));
        List<PostEntity> list = new ArrayList<PostEntity>();
        reader.readLine();

        String line ="";
//
//        while((line=reader.readLine())!=null) {
//            String[] split = line.split(COMMAN);
//            PostEntity post = new PostEntity(split[0],
//                    split[1],
//                    Boolean.parseBoolean(split[2]),
//                    LocalDate.parse(split[3]),
//                    LocalDate.parse(split[4]));
//            list.add(post);
//        }

        reader.close();
        return list;
        }

    public List<UserEntity> readUser() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(USER_PATH, StandardCharsets.UTF_8));
        List<UserEntity> list = new ArrayList<>();
        reader.readLine();
        String line ="";

        while((line=reader.readLine())!=null) {
            String[] split = line.split(COMMAN);
//            UserEntity user = new UserEntity(
//                    split[0],
//                    split[1],
//                    split[2],
//                    split[3],
//                    split[4]
//            );

//            list.add(user);
        }
        reader.close();
        return list;
    }

    }

