package com.wanted.springcafe.service;

import com.wanted.springcafe.domain.file.PostFileEntity;
import com.wanted.springcafe.domain.file.PostFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostFileService {

    private final PostFileRepository postFileRepository;

    public void saveFile(PostFileEntity file){
        postFileRepository.save(file);
    }
}
