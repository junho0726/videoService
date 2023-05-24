package com.video.service.repository;


import com.video.service.entity.LikeStateEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeStateRepository extends JpaRepository<LikeStateEntity, Integer> {
    @Query("SELECT l FROM LikeStateEntity l WHERE l.user.userSeq = :userSeq AND l.video.videoSeq = :videoSeq")
    Optional<LikeStateEntity> findByUserSeqAndVideoSeq (@Param("userSeq") int userSeq, @Param("videoSeq") int videoSeq);
}
