package com.video.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class LikeState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeStateSeq;

    @Column(nullable = false)
    private String likeState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videoSeq")
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userSeq")
    private User user;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createDate;

}
