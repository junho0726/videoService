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
public class ThumbnailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int thumbnailSeq;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileFullPath;

    @Column(nullable = false)
    private String fileOriginName;

    @Column(nullable = false)
    private String fileName;

    @OneToOne
    @JoinColumn(name = "fileSeq")
    private FileEntity file;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createDate;

}
