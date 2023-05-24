package com.video.service.service;

import com.video.service.dto.LikeStateDto;
import com.video.service.entity.LikeStateEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;

import com.video.service.repository.LikeStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeStateService {

    @Autowired
    private LikeStateRepository likeRepository;

    public LikeStateDto likeInsert(LikeStateDto likeState) {
        try {
            int userSeq = likeState.getUserSeq();
            int videoSeq = likeState.getVideoSeq();

            Optional<LikeStateEntity> likeStateOptional = likeRepository.findByUserSeqAndVideoSeq(userSeq, videoSeq);

            if (likeStateOptional.isPresent()) {
                LikeStateEntity DBStateEntity = likeStateOptional.get();

                if (DBStateEntity.getLikeState().equals(likeState.getLikeState())) {
                    likeRepository.delete(DBStateEntity);
                    LikeStateDto deletedLikeStateDto = new LikeStateDto();
                    deletedLikeStateDto.setLikeState("삭제");
                    deletedLikeStateDto.setUserSeq(userSeq);
                    deletedLikeStateDto.setVideoSeq(videoSeq);
                    return deletedLikeStateDto;
                } else {
                    DBStateEntity.setLikeState(likeState.getLikeState());
                    LikeStateEntity updatedLikeStateEntity = likeRepository.save(DBStateEntity);

                    LikeStateDto updatedLikeStateDto = new LikeStateDto();
                    updatedLikeStateDto.setLikeState(updatedLikeStateEntity.getLikeState());
                    updatedLikeStateDto.setUserSeq(updatedLikeStateEntity.getUser().getUserSeq());
                    updatedLikeStateDto.setVideoSeq(updatedLikeStateEntity.getVideo().getVideoSeq());

                    return updatedLikeStateDto;
                }
            } else {
                LikeStateEntity newLikeStateEntity = new LikeStateEntity();
                UserEntity user = new UserEntity();
                user.setUserSeq(userSeq);
                VideoEntity video = new VideoEntity();
                video.setVideoSeq(videoSeq);
                newLikeStateEntity.setUser(user);
                newLikeStateEntity.setVideo(video);
                newLikeStateEntity.setLikeState(likeState.getLikeState());
                LikeStateEntity savedLikeStateEntity = likeRepository.save(newLikeStateEntity);

                LikeStateDto savedLikeStateDto = new LikeStateDto();
                savedLikeStateDto.setLikeState(savedLikeStateEntity.getLikeState());
                savedLikeStateDto.setUserSeq(savedLikeStateEntity.getUser().getUserSeq());
                savedLikeStateDto.setVideoSeq(savedLikeStateEntity.getVideo().getVideoSeq());

                return savedLikeStateDto;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("좋아요/싫어요 처리 중 오류가 발생하였습니다.");
        }
    }
}