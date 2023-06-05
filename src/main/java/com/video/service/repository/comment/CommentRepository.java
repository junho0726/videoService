package com.video.service.repository.comment;


import com.video.service.entity.CommentEntity;
import com.video.service.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer>, CommentRepositoryCustom {

}
