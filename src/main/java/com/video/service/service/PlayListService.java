package com.video.service.service;

import com.video.service.repository.playList.PlayListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayListService {

    @Autowired
    private PlayListRepository playListRepository;
}
