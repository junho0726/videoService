package com.video.service.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.video.service.entity.ChannelEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ChannelDto
{

    private String userId;

    private String userName;


        public ChannelDto(ChannelEntity channelEntity){
            userId = channelEntity.getUser().getId();
            userName = channelEntity.getUser().getName();
        }

}
