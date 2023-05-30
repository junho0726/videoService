package com.video.service.repository;


import com.video.service.entity.SubscribeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Integer> {
    @Query("SELECT s FROM SubscribeEntity s WHERE s.user.userSeq = :userSeq AND s.channel.channelSeq = :channelSeq")
    Optional<SubscribeEntity> findByUserSeqAndChannelSeq (@Param("userSeq") int userSeq, @Param("channelSeq") int channelSeq);

    @Transactional
    @Modifying
    @Query("DELETE FROM SubscribeEntity s WHERE s.user.userSeq = :userSeq AND s.channel.channelSeq = :channelSeq")
    void deleteByChannelSeqAndUserSeq(@Param("userSeq") int userSeq, @Param("channelSeq") int channelSeq);

    @Query("SELECT s FROM SubscribeEntity s WHERE s.user.userSeq = :userSeq")
    List<SubscribeEntity> findByUserSeq(@Param("userSeq") int userSeq);

    @Query("SELECT COUNT(s) FROM SubscribeEntity s WHERE s.channel.channelSeq = :channelSeq")
    int subscribeCountByChannel(@Param("channelSeq") int channelSeq);
}
