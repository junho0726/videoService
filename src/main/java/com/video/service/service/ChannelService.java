package com.video.service.service;

import com.video.service.entity.Channel;
import com.video.service.entity.User;
import com.video.service.repository.ChannelRepository;
import com.video.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    public Channel ChannelSave(Channel channel) throws Exception{
        return channelRepository.save(channel);
    }

}
