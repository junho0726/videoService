package com.video.service.repository;

import com.video.service.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository <FileEntity, Integer> {

}
