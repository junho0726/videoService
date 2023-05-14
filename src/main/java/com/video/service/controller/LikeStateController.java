package com.video.service.controller;

import com.video.service.entity.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class LikeStateController {

    @PostMapping(value = "insert/likeState")
    public LikeState insertLikeState(@RequestBody LikeState likeState) {
        return likeState;
    }

}
