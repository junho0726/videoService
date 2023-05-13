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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int channelSeq;

    @OneToOne
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Video> video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriberSeq")
    private User subscriber;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createDate;

}
