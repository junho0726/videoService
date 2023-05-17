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
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userSeq;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String ip;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private ChannelEntity channel;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private TokenEntity token;

//    @JsonIgnoreProperties("subscriber")
//    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.DETACH)
//    private List<Channel> subscribeChannel;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createDate;

}
