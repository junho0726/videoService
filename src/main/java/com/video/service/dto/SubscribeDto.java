package com.video.service.dto;

import com.video.service.entity.ChannelEntity;
import com.video.service.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubscribeDto {

    private ChannelEntity channel;

    private UserEntity user;

}
