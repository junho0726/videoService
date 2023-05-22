package com.video.service.service;


import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import com.video.service.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public VideoEntity insertVideo(VideoEntity video) throws Exception{
        return videoRepository.save(video);
    }


    @Transactional
    public VideoEntity updateVideo(VideoEntity video) throws Exception {
        VideoEntity videoEntity = videoRepository.findById(video.getVideoSeq()).orElseThrow(() -> {
            return new IllegalArgumentException("존재하지 않는 비디오입니다..");
        });
        videoEntity.setContent(video.getContent());
        videoEntity.setTitle(video.getTitle());
        videoEntity.setThumbnail(video.getThumbnail());

        return videoEntity;
    }


/*    @Transactional
    public VideoEntity videoUpdate(VideoEntity video) throws Exception {
        VideoEntity videoEntity = videoRepository.findById(video.getVideoSeq()).orElseThrow(() -> {
            return new IllegalArgumentException("�������� �ʴ� �����Դϴ�..");
        });

        videoEntity.setTitle(video.getTitle());
        videoEntity.setContent(video.getContent());

        return videoEntity;
    }*/

}
