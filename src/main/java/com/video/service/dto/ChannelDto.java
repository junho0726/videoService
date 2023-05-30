package com.video.service.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.video.service.entity.ChannelEntity;
import com.video.service.entity.FileEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChannelDto
{

    private int channelSeq;

    private String userId;

    private String userName;

    private String channelName;

    private List<FileDto> files;

}
