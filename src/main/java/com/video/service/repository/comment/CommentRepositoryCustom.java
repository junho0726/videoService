package com.video.service.repository.comment;

import com.video.service.entity.CommentEntity;
import com.video.service.entity.VideoEntity;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentEntity> findByVideoSeq(int videoSeq);

    List<CommentEntity> findAllByVideoAndParentIsNull(int videoSeq);
}
