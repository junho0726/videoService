package com.video.service.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.video.service.entity.FileEntity;
import com.video.service.entity.ThumbnailEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public FileEntity saveFile(MultipartFile multipartFile) throws IOException {
    System.out.println("bucket="+bucket);
        String filename =  createFileName(multipartFile.getOriginalFilename());
        System.out.println("filename="+filename);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        AmazonS3 s3Client = new AmazonS3Client();
        s3Client.setEndpoint("http://s3.amazonaws.com");

        amazonS3.putObject(bucket, filename, multipartFile.getInputStream(), metadata);

        String filePath = "http://" + amazonS3.getUrl(bucket, filename).getHost() + "/" + filename;

        FileEntity fileEntity = new FileEntity();

        fileEntity.setFileName(filename);
        fileEntity.setFileOriginName(multipartFile.getOriginalFilename());
        fileEntity.setFileFullPath(filePath);
        fileEntity.setFilePath(filePath);

        return fileEntity;
    }


    public ThumbnailEntity saveThumbnail(MultipartFile multipartFile) throws IOException {

        String filename =  createFileName(multipartFile.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, filename, multipartFile.getInputStream(), metadata);

        String filePath = amazonS3.getUrl(bucket, filename).toString();

        ThumbnailEntity thumbnailEntity = new ThumbnailEntity();

        thumbnailEntity.setFileName(filename);
        thumbnailEntity.setFileOriginName(multipartFile.getOriginalFilename());
        thumbnailEntity.setFileFullPath(filePath);
        thumbnailEntity.setFilePath(filePath);

        return thumbnailEntity;
    }


    public void deleteImage(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }
}
