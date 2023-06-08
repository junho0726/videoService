package com.video.service.repository.saveVideo;

import com.video.service.entity.SaveVideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveVideoRepository extends JpaRepository<SaveVideoEntity, Integer>, SaveVideoRepositoryCustom {


}
