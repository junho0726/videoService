package com.video.service.service;

import com.video.service.dto.ChannelDto;
import com.video.service.dto.SubscribeDto;
import com.video.service.entity.SubscribeEntity;
import com.video.service.entity.UserEntity;
import com.video.service.repository.SubscribeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscribeService{

    @Autowired
    private SubscribeRepository subscribeRepository;

    public Boolean insertSubscribe(SubscribeEntity subscribeEntity) throws Exception{
        try {
            Optional<SubscribeEntity> subscribeOptional = subscribeRepository.findByUserSeqAndChannelSeq(subscribeEntity.getUser().getUserSeq(), subscribeEntity.getChannel().getChannelSeq());
            if (subscribeOptional.isPresent()) {
                subscribeRepository.deleteByChannelSeqAndUserSeq(subscribeEntity.getUser().getUserSeq(), subscribeEntity.getChannel().getChannelSeq());
                return false;
            } else {
                subscribeRepository.save(subscribeEntity);
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("구독 처리 중 오류가 발생하였습니다.");
        }
    }

    public List<ChannelDto> findByUserSeq(SubscribeEntity subscribeEntity) throws  Exception{
        List<ChannelDto> channelDtos = new ArrayList<>();
        List<SubscribeEntity> subscribeEntities = new ArrayList<>();
        try {
            subscribeEntities = subscribeRepository.findByUserSeq(subscribeEntity.getUser().getUserSeq());
            ModelMapper modelMapper = new ModelMapper();
            for (SubscribeEntity subscribe : subscribeEntities) {
                ChannelDto channelDto = modelMapper.map(subscribe.getChannel(), ChannelDto.class);
                channelDtos.add(channelDto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return channelDtos;
    }
}
