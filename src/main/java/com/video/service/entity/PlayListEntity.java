package com.video.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class PlayListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playListSeq;

    @Column(nullable = false)
    private String playListName;

    @JsonIgnore
    @OneToMany(mappedBy = "playList")
    private List<VideoEntity> video;

}
