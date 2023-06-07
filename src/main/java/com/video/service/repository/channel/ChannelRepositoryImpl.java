package com.video.service.repository.channel;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.video.service.entity.ChannelEntity;
import com.video.service.entity.QChannelEntity;
import com.video.service.entity.QSubscribeEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ChannelRepositoryImpl implements ChannelRepositoryCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<ChannelEntity> findByUserSeq(int userSeq) {
        QChannelEntity qChannel = QChannelEntity.channelEntity;

        ChannelEntity channelEntity = jpaQueryFactory
                .selectFrom(qChannel)
                .where(qChannel.user.userSeq.eq(userSeq))
                .fetchOne();

        return Optional.ofNullable(channelEntity);
    }
}
