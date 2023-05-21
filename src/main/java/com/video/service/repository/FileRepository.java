package com.video.service.repository;

import com.video.service.dto.FileDto;
import com.video.service.entity.FileEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository <FileEntity, Integer> {

    @Query(value = "SELECT f FROM FileEntity f WHERE f.video = :video")
    List<FileDto>  findByVideo(VideoEntity video);

}
