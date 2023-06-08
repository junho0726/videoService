package com.video.service.controller;

import com.video.service.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/playList/")
public class PlayListController {

    @Autowired
    private PlayListService playListService;


}
