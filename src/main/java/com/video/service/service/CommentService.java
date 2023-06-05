package com.video.service.service;

import com.video.service.dto.CommentDto;
import com.video.service.entity.CommentEntity;
import com.video.service.entity.UserEntity;
import com.video.service.entity.VideoEntity;
import com.video.service.repository.comment.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public CommentEntity insertComment(CommentDto commentDto) throws Exception {
        CommentEntity commentEntity = new CommentEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserSeq(commentDto.getUserSeq());
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setVideoSeq(commentDto.getVideoSeq());

        commentEntity.setContent(commentDto.getContent());
        commentEntity.setUser(userEntity);
        commentEntity.setVideo(videoEntity);
        if (commentDto.getParentSeq() != 0) {
            commentEntity.setParent(new CommentEntity());
            commentEntity.getParent().setCommentSeq(commentDto.getParentSeq());
        }

        CommentEntity comment = commentRepository.save(commentEntity);

        return comment;
    }

    public List<CommentDto> findByVideoSeq(int videoSeq) throws Exception {

        List<CommentEntity> commentEntities = commentRepository.findByVideoSeq(videoSeq);
        List<CommentDto> commentDtos = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        for (CommentEntity commentEntity : commentEntities) {
            CommentDto commentDto = modelMapper.map(commentEntity, CommentDto.class);
            commentDto.setCommentSeq(commentEntity.getCommentSeq());
            if(commentEntity.getParent() != null) {
                commentDto.setParentSeq(commentEntity.getParent().getCommentSeq());
            }
            commentDto.setVideoSeq(commentEntity.getVideo().getVideoSeq());
            commentDto.setUserName(commentEntity.getUser().getName());
            List<CommentDto> childDtos = new ArrayList<>();
            for (CommentEntity childEntity : commentEntity.getChildren()) {
                CommentDto childDto = modelMapper.map(childEntity, CommentDto.class);
                childDtos.add(childDto);
            }
            commentDto.setChildren(childDtos);
            commentDtos.add(commentDto);
        }
        return commentDtos;

    }

    public List<CommentDto> findParentByVideoEntity(VideoEntity videoEntity) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        commentRepository.findAllByVideoAndParentIsNull((videoEntity.getVideoSeq())).forEach(commentEntity -> {
            commentDtoList.add(new CommentDto(commentEntity));
        });
        return commentDtoList;
    }
}