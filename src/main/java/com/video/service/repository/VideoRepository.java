package com.video.service.repository;

import com.video.service.entity.VideoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideoRepository extends JpaRepository<VideoEntity, Integer> {

    @Query("SELECT v FROM VideoEntity v WHERE v.channel.channelSeq = :channelSeq")
    Page<VideoEntity> findAllByChannel(int channelSeq, Pageable pageable);
}