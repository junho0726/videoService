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
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentSeq;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userSeq")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videoSeq")
    private VideoEntity video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentSeq")
    private CommentEntity parent;

    @OneToMany(mappedBy = "parent")
    private List<CommentEntity> children;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createDate;
}
