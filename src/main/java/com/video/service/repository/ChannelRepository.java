package com.video.service.repository;

import com.video.service.entity.Channel;
import com.video.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {

}
