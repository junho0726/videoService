package com.video.service.repository.playList;

import com.video.service.entity.PlayListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayListRepository extends JpaRepository<PlayListEntity, Integer>, PlayListRepositoryCustom {
}
