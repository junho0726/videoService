package com.video.service.repository;

import com.video.service.entity.TokenEntity;
import com.video.service.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

    @Query("SELECT t FROM TokenEntity t WHERE t.user.userSeq = :userSeq")
    TokenEntity findByUserSeq(int userSeq);

    @Modifying
    @Query("UPDATE TokenEntity t SET t.accessToken = :accessToken WHERE t.user.userSeq = :userSeq")
    void updateAccessToken(@Param("accessToken") String accessToken, @Param("userSeq") int userSeq);

}
