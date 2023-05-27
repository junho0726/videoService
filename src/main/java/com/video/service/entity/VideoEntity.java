package com.video.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int videoSeq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private int count;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channelSeq")
    private ChannelEntity channel;

    @OneToOne
    @JoinColumn(name = "fileSeq")
    private FileEntity file;

    @OneToOne
    @JoinColumn(name = "thumbnailSeq")
    private ThumbnailEntity thumbnail;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="video_category",
            joinColumns = @JoinColumn(name = "video_videoSeq"),
            inverseJoinColumns = @JoinColumn(name = "category_categorySeq"))
    private List<CategoryEntity> category;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createDate;

}
