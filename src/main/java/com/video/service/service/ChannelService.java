package com.video.service.service;

import com.video.service.entity.ChannelEntity;
import com.video.service.repository.channel.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
        return channelRepository.findById(channelSeq).orElseThrow(() -> {
            return new IllegalArgumentException("존재하지 않는 채널입니다.");
        });
    }

    public ChannelEntity findByUserSeq(int userSeq) throws Exception {
        return channelRepository.findByUserSeq(userSeq).orElseThrow(() -> {
            return new IllegalArgumentException("존재하지 않는 채널입니다.");
        });
    }
}
