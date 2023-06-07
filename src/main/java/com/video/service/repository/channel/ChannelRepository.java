package com.video.service.repository.channel;

import com.video.service.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<ChannelEntity, Integer>, ChannelRepositoryCustom {

}
