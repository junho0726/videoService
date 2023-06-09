package com.video.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
public class ChannelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int channelSeq;

    @Column(nullable = false)
    private String channelName;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "userSeq")
    private UserEntity user;

    @OneToMany(mappedBy = "channel")
    private List<VideoEntity> video;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createDate;

}
