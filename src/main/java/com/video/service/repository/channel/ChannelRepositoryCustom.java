package com.video.service.repository.channel;

import com.video.service.entity.ChannelEntity;

import java.util.Optional;

public interface ChannelRepositoryCustom {

    Optional<ChannelEntity> findByUserSeq(int userSeq);
}
