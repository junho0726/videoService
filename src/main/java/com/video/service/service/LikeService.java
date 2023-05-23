package com.video.service.service;

import com.video.service.entity.LikeStateEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;

import com.video.service.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public LikeStateEntity likeInsert(UserEntity user, VideoEntity video, String likeState) {
        Optional<LikeStateEntity> likeStateOptional = likeRepository.findByUserEntityAndLikeStateEntity(user, video);

        if (likeStateOptional.isPresent()) {
            LikeStateEntity likeStateEntity = likeStateOptional.get();
            likeStateEntity.setLikeState(likeState);
            return likeStateEntity;
        } else {
            LikeStateEntity newLikeStateEntity = new LikeStateEntity();
            newLikeStateEntity.setUser(user);
            newLikeStateEntity.setVideo(video);
            newLikeStateEntity.setLikeState(likeState);
            return likeRepository.save(newLikeStateEntity);
        }
    }

}
