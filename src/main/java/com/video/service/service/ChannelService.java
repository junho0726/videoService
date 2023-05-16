package com.video.service.service;

import com.video.service.entity.ChannelEntity;
import com.video.service.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    public ChannelEntity ChannelSave(ChannelEntity channel) throws Exception{
        return channelRepository.save(channel);
    }

}
