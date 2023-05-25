package com.video.service.service;


import com.video.service.entity.FileEntity;
import com.video.service.entity.ThumbnailEntity;
import com.video.service.entity.VideoEntity;
import com.video.service.repository.ThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThumbnailService {

    @Autowired
    private ThumbnailRepository thumbnailRepository;

    public ThumbnailEntity insertThumbnail(ThumbnailEntity thumbnailEntity) throws Exception {
        return thumbnailRepository.save(thumbnailEntity);
    }


    public ThumbnailEntity findByVideo(VideoEntity videoEntity) {
        Optional<ThumbnailEntity> thumbnail = thumbnailRepository.findById(videoEntity.getFile().getFileSeq());

        return thumbnail.orElseGet(() -> null);
    }

}
