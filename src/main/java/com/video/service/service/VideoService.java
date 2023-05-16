package com.video.service.service;


import com.video.service.entity.VideoEntity;
import com.video.service.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public VideoEntity insertVideo(VideoEntity video) throws Exception{
        return videoRepository.save(video);
    }

}
