package com.video.service.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.video.service.entity.CommentEntity;
import com.video.service.entity.QCommentEntity;
import com.video.service.entity.VideoEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CommentEntity> findByVideoSeq(int videoSeq) {
        QCommentEntity qCommentEntity = QCommentEntity.commentEntity;

        List<CommentEntity> findCommentEntityList = jpaQueryFactory
                .selectFrom(qCommentEntity)
                .where(qCommentEntity.video.videoSeq.eq(videoSeq))
                .fetch();

        return findCommentEntityList;
    }

    @Override
    public List<CommentEntity> findAllByVideoAndParentIsNull(int videoSeq) {
        QCommentEntity qCommentEntity = QCommentEntity.commentEntity;

        List<CommentEntity> findCommentEntityList = jpaQueryFactory
                .selectFrom(qCommentEntity)
                .where(qCommentEntity.video.videoSeq.eq(videoSeq))
                .where(qCommentEntity.parent.isNull())
                .fetch();

        return findCommentEntityList;
    }
}
