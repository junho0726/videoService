package com.video.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
public class LikeStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeStateSeq;

    @Column(nullable = false)
    private String likeState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videoSeq")
    private VideoEntity video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userSeq")
    private UserEntity user;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createDate;

}
