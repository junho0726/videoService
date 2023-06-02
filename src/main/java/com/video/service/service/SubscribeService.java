package com.video.service.service;

import com.video.service.dto.ChannelDto;
import com.video.service.dto.SubscribeDto;
import com.video.service.entity.SubscribeEntity;
import com.video.service.entity.VideoEntity;
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

    public SubscribeDto insertSubscribe(SubscribeEntity subscribeEntity) throws Exception{
        try {
            ModelMapper modelMapper = new ModelMapper();
            SubscribeDto subscribeDto = new SubscribeDto();
            Optional<SubscribeEntity> subscribeOptional = subscribeRepository.findByUserSeqAndChannelSeq(subscribeEntity.getUser().getUserSeq(), subscribeEntity.getChannel().getChannelSeq());
            if (subscribeOptional.isPresent()) {
                subscribeRepository.deleteByChannelSeqAndUserSeq(subscribeEntity.getUser().getUserSeq(), subscribeEntity.getChannel().getChannelSeq());
                int count = subscribeRepository.subscribeCountByChannel(subscribeEntity.getChannel().getChannelSeq());
                subscribeDto.setSubscribeCount(count);
                return subscribeDto;
            } else {
                SubscribeEntity subscribe = subscribeRepository.save(subscribeEntity);
                subscribeDto = modelMapper.map(subscribe, SubscribeDto.class);
                int count = subscribeRepository.subscribeCountByChannel(subscribeEntity.getChannel().getChannelSeq());
                subscribeDto.setSubscribeState("Y");
                subscribeDto.setSubscribeCount(count);
                return subscribeDto;
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("구독 처리 중 오류가 발생하였습니다.");
        }
    }

    public List<ChannelDto> findByUserSeq(int userSeq) throws  Exception{
        System.out.println("userSeq="+userSeq);
        List<ChannelDto> channelDtos = new ArrayList<>();
        List<SubscribeEntity> subscribeEntities = new ArrayList<>();
        try {
            subscribeEntities = subscribeRepository.findByUserSeq(userSeq);
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

    public int subscribeCountByChannel(int channelSeq) throws Exception {
        int count = subscribeRepository.subscribeCountByChannel(channelSeq);

        return count;
    }

    public SubscribeDto findByUserSeqAndChannelSeq(int userSeq, int channelSeq) throws  Exception{
        SubscribeDto subscribeDto = new SubscribeDto();
        try {
            Optional<SubscribeEntity> subscribeOptional = subscribeRepository.findByUserSeqAndChannelSeq(userSeq, channelSeq);
            if (subscribeOptional.isPresent()) {
                subscribeDto.setSubscribeState("Y");
            } else {
                subscribeDto.setSubscribeState(null);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
            return subscribeDto;
    }

  public List<VideoEntity> findVideoByUserSeq(int userSeq) throws Exception {
      List<VideoEntity> aa = new ArrayList<>();
      try{
          aa =  subscribeRepository.findVideoByUserSeq(userSeq);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return aa;
  }


}
