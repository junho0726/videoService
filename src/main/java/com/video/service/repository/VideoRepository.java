package com.video.service.repository;

import com.video.service.entity.VideoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository extends JpaRepository<VideoEntity, Integer> {

    @Query("SELECT v FROM VideoEntity v WHERE v.channel.channelSeq = :channelSeq")
    Page<VideoEntity> findAllByChannel(int channelSeq, Pageable pageable);

    @Query("SELECT v FROM VideoEntity v WHERE v.title LIKE %:q%")
    Page<VideoEntity> findAllBySearch(Pageable pageable, @Param("q") String q);

    @Query(" SELECT v FROM VideoEntity v WHERE v.channel.channelSeq = :channelSeq ")
    List<VideoEntity> findAllByChannelSeq(int channelSeq);

    @Query(value = "SELECT v.* FROM videoentity v " +
            "LEFT OUTER JOIN subscribeentity s ON v.channelSeq = s.channelSeq " +
            "LEFT OUTER JOIN viewinghistoryentity vh ON vh.videoSeq = v.videoSeq " +
            "WHERE vh.userSeq = ?1" , nativeQuery = true)
    List<VideoEntity> findVideoByUserSeq(int userSeq);
}