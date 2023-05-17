package com.video.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tokenSeq;

    @Column(nullable = true)
    private String accessToken;

    @OneToOne
    @JoinColumn(name = "userSeq", unique = true)
    private UserEntity user;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createDate;

}
