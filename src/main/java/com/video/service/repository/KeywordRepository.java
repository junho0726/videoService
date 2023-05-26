package com.video.service.repository;

import com.video.service.entity.CategoryEntity;
import com.video.service.entity.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Integer> {

    @Query("SELECT k FROM KeywordEntity k WHERE k.category.categorySeq = :categorySeq")
    List<KeywordEntity> findByCategorySeq (@Param("categorySeq") int categorySeq);
}
