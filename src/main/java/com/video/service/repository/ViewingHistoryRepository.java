package com.video.service.repository;

import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import com.video.service.entity.ViewingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViewingHistoryRepository extends JpaRepository<ViewingHistoryEntity, Integer> {

    Optional<ViewingHistoryEntity> findByUserAndVideo(UserEntity userEntity, VideoEntity videoEntity);

}
