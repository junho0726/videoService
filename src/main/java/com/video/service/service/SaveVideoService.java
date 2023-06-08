package com.video.service.service;

import com.video.service.dto.saveVideo.ReqSaveVideoDto;
import com.video.service.dto.saveVideo.ResSaveVideoDto;
import com.video.service.entity.SaveVideoEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import com.video.service.repository.saveVideo.SaveVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveVideoService {

    @Autowired
    private SaveVideoRepository saveVideoRepository;

    @Transactional
    public ResSaveVideoDto handle(ReqSaveVideoDto saveVideoDto) {
        SaveVideoEntity saveVideoEntity = saveVideoRepository.findById(saveVideoDto.getSaveVideoSeq()).orElseGet(() -> {
            return saveVideoRepository.save(SaveVideoEntity
                    .builder()
                    .state(saveVideoDto.getState())
                    .video(VideoEntity
                            .builder()
                            .videoSeq(saveVideoDto.getVideoSeq())
                            .build())
                    .user(UserEntity
                            .builder()
                            .userSeq(saveVideoDto.getUserSeq())
                            .build())
                    .build());
        });

        saveVideoEntity.setState(saveVideoDto.getState());

        return ResSaveVideoDto
                .builder()
                .state(saveVideoEntity.getState())
                .saveVideoSeq(saveVideoEntity.getSaveVideoSeq())
                .build();
    }

    public SaveVideoEntity findByUserSeqAndVideoSeq(int userSeq, int videoSeq) {
        SaveVideoEntity saveVideoEntity = saveVideoRepository.findByUserSeqAndVideoSeq(userSeq, videoSeq)
                .orElse(SaveVideoEntity
                        .builder()
                        .state("N")
                        .build());
        return saveVideoEntity;
    }
}
