package com.video.service.repository.saveVideo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.video.service.entity.QSaveVideoEntity;
import com.video.service.entity.SaveVideoEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class SaveVideoRepositoryImpl implements SaveVideoRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    private QSaveVideoEntity qSaveVideo = QSaveVideoEntity.saveVideoEntity;

    @Override
    public Optional<SaveVideoEntity> findByUserSeqAndVideoSeq(int userSeq, int videoSeq) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qSaveVideo)
                .where(qSaveVideo.user.userSeq.eq(userSeq))
                .where(qSaveVideo.video.videoSeq.eq(videoSeq))
                .fetchOne());
    }
}
