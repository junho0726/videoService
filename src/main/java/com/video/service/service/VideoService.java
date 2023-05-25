package com.video.service.service;


import com.video.service.entity.ChannelEntity;
import com.video.service.entity.FileEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import com.video.service.repository.FileRepository;
import com.video.service.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

        videoEntity.setThumbnail(video.getThumbnail());

        return videoEntity;
    }

    public Page<VideoEntity> findAll(Pageable pageable) throws Exception {

        Page<VideoEntity> videoPage = videoRepository.findAll(pageable);

        return videoPage;
    }

    public VideoEntity findById(int videoSeq) throws  Exception {
        Optional <VideoEntity> video = videoRepository.findById(videoSeq);

        return video.get();
    }


    public Page<VideoEntity> findAllByChannel(ChannelEntity channelEntity, Pageable pageable) throws Exception {

        Page<VideoEntity> videoPage = videoRepository.findAllByChannel(channelEntity.getChannelSeq(), pageable);

        return videoPage;
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
