package com.video.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categorySeq;

    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties("category")
    @ManyToMany(mappedBy = "category", cascade = CascadeType.DETACH)
    private List<VideoEntity> video;

}
