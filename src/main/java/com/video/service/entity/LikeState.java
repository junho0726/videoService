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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
public class LikeState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeSeq;

    @Column(nullable = false)
    private String likeState;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videoSeq", nullable = false)
    private Video video;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userSeq", nullable = false)
    private User user;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createDate;

}
