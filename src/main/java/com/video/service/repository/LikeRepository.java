package com.video.service.repository;


import com.video.service.entity.LikeStateEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeStateEntity, Integer> {
    Optional<LikeStateEntity> findByUserEntityAndLikeStateEntity (UserEntity user, VideoEntity video);
}
