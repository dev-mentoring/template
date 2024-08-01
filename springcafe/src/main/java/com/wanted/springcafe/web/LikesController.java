package com.wanted.springcafe.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikesController {

    @PutMapping("/post/{postId}")
    public void likePost(@PathVariable Long postId){

    }
}
