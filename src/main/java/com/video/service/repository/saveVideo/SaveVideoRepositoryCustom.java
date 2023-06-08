package com.video.service.repository.saveVideo;

import com.video.service.entity.SaveVideoEntity;

import java.util.Optional;

public interface SaveVideoRepositoryCustom {

    Optional<SaveVideoEntity> findByUserSeqAndVideoSeq(int userSeq, int videoSeq);
}
