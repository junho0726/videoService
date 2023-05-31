package com.video.service.repository;


import com.video.service.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    @Query("SELECT c FROM CommentEntity c WHERE c.video.videoSeq = :videoSeq")
    List<CommentEntity> findByVideoSeq(@Param("videoSeq") int videoSeq);
}
