package com.video.service.service;


import com.video.service.entity.*;
import com.video.service.repository.FileRepository;
import com.video.service.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private KeywordService keywordService;


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



    @Transactional
    public VideoEntity findById(int videoSeq) throws  Exception {
        VideoEntity videoEntity = videoRepository.findById(videoSeq).orElseThrow(() -> {
            return new IllegalArgumentException("존재하지 않는 비디오입니다..");
        });
        videoEntity.setCount(videoEntity.getCount() + 1);

        return videoEntity;
    }

    public Page<VideoEntity> findAllByChannel(ChannelEntity channelEntity, Pageable pageable) throws Exception {

        Page<VideoEntity> videoPage = videoRepository.findAllByChannel(channelEntity.getChannelSeq(), pageable);

        return videoPage;
    }

    public Page<VideoEntity> findAllByCategory(int categorySeq, Pageable pageable) throws Exception {
        List<VideoEntity> videos;
        List<KeywordEntity> keywords = keywordService.findByKeyword(categorySeq);
        List<VideoEntity> matchedVideos = new ArrayList<>();
        videos = findByCategory();

        for (VideoEntity video : videos) {
            String title = video.getTitle();
            String content = video.getContent();
            boolean matchFound = false;

            for (KeywordEntity keyword : keywords) {
                String keywordValue = keyword.getKeyword();

                if (title.indexOf(keywordValue) != -1 || content.indexOf(keywordValue) != -1) {
                    matchFound = true;
                    break;
                }
            }
            if (matchFound) {
                matchedVideos.add(video);
            }
        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), matchedVideos.size());
        Page<VideoEntity> page = new PageImpl<>(matchedVideos.subList(start, end), pageable, matchedVideos.size());

        return page;

    }

    public List<VideoEntity> findByCategory() throws Exception {

        List<VideoEntity> videoPage = videoRepository.findAll();

        return videoPage;
    }



}
