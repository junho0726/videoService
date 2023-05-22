package com.video.service.repository;

import com.video.service.entity.ThumbnailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbnailRepository extends JpaRepository <ThumbnailEntity, Integer> {

}
