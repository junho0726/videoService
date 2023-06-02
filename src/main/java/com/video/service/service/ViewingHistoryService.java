package com.video.service.service;

import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import com.video.service.entity.ViewingHistoryEntity;
import com.video.service.repository.ViewingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class ViewingHistoryService {

    @Autowired
    private ViewingHistoryRepository viewingHistoryRepository;

    @Transactional
    public ViewingHistoryEntity findByUserAndVideo(UserEntity userEntity, VideoEntity videoEntity) {
        ViewingHistoryEntity viewingHistoryEntity = viewingHistoryRepository.findByUserAndVideo(userEntity, videoEntity).orElseGet(() -> {
            ViewingHistoryEntity viewingHistory = new ViewingHistoryEntity();
            viewingHistory.setUser(userEntity);
            viewingHistory.setVideo(videoEntity);
            return viewingHistoryRepository.save(viewingHistory);
        });

        viewingHistoryEntity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        return viewingHistoryEntity;
    }

    @Transactional
    public void save(ViewingHistoryEntity viewingHistory) {
        viewingHistoryRepository.save(viewingHistory);

    }
}
