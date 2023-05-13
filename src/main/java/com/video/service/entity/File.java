package com.video.service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileSeq;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileFullPath;

    @Column(nullable = false)
    private String fileOriginName;

    @OneToOne
    private Video video;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createDate;

}
