package com.video.service.service;


import com.video.service.entity.ThumbnailEntity;
import com.video.service.repository.ThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThumbnailService {

    @Autowired
    private ThumbnailRepository thumbnailRepository;

    public ThumbnailEntity insertThumbnail(ThumbnailEntity thumbnailEntity) throws Exception {
        return thumbnailRepository.save(thumbnailEntity);
    }


}
