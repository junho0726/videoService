package com.video.service.controller;

import com.video.service.entity.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @PostMapping(value = "/user")
    public User user(@RequestBody User user) {
        return user;
    }

    @PostMapping(value = "/video")
    public Video video(@RequestBody Video video) {
        return video;
    }

    @PostMapping(value = "/category")
    public Category category(@RequestBody Category category) {
        return category;
    }

    @PostMapping(value = "/channel")
    public Channel channel(@RequestBody Channel channel) {
        return channel;
    }

    @PostMapping(value = "/file")
    public File file(@RequestBody File file) {
        return file;
    }

    @PostMapping(value = "/keyword")
    public Keyword keyword(@RequestBody Keyword keyword) {
        return keyword;
    }

    @PostMapping(value = "/likeState")
    public LikeState likeState(@RequestBody LikeState likeState) {
        return likeState;
    }
}
