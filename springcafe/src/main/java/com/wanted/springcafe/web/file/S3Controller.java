package com.wanted.springcafe.web.file;

import com.wanted.springcafe.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/file")
    public void save(@RequestPart MultipartFile file) throws IOException {
        s3Service.saveFile(file);
    }
}
