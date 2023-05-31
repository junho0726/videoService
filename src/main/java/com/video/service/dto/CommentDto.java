package com.video.service.dto;

import com.video.service.entity.CommentEntity;
import com.video.service.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentDto {

    private int commentSeq;

    private int videoSeq;

    private int userSeq;

    private String userName;

    private int parentSeq;

    private String content;

    private List<CommentDto> children;

    private Timestamp createDate;


    public CommentDto (CommentEntity commentEntity){
        commentSeq = commentEntity.getCommentSeq();
        videoSeq = commentEntity.getVideo().getVideoSeq();
        userSeq = commentEntity.getUser().getUserSeq();
        userName = commentEntity.getUser().getName();
        parentSeq = (commentEntity.getParent() != null) ? commentEntity.getParent().getCommentSeq() : 0;
        content = commentEntity.getContent();
        createDate = commentEntity.getCreateDate();
        children = new ArrayList<>();
        if (commentEntity.getChildren() != null) {
            for (CommentEntity childEntity : commentEntity.getChildren()) {
                CommentDto childDto = new CommentDto(childEntity);
                children.add(childDto);
            }
        }
    }

}