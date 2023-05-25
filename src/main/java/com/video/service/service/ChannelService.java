package com.video.service.service;

import com.video.service.entity.ChannelEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import com.video.service.repository.ChannelRepository;
import com.video.service.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    public ChannelEntity channelSave(ChannelEntity channel) throws Exception{
        return channelRepository.save(channel);
    }

    public List<ChannelEntity> channelFindAll(){
        return channelRepository.findAll();
    }

    public ChannelEntity findById(int channelSeq) throws Exception {

        Optional <ChannelEntity>  channel =  channelRepository.findById(channelSeq);

        return channel.get();
    }
}
