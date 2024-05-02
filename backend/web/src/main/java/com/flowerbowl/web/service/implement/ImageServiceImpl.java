package com.flowerbowl.web.service.implement;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.dto.response.image.*;
import com.flowerbowl.web.handler.WrongFileExtensionException;
import com.flowerbowl.web.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public ResponseEntity<? extends ImageResponseDto> uploadImage(MultipartFile multipartFile, String dirName) throws Exception {
        try {
            // 파일 확장자가 이미지 종류가 맞는지 검사
            if (!validateFileExtension(multipartFile)) {
                throw new WrongFileExtensionException();
            }

            File file = convertMultipartFileToFile(multipartFile).orElseThrow(IllegalArgumentException::new);

            String fileName = randomFileName(file, dirName);
            String imageUrl = putS3(file, fileName);

            // S3에 업로드 하는 과정 중 로컬에 저장된 파일 삭제
            removeFile(file);

            if (Objects.equals(dirName, "temp/content")) {
                UpContentImageSuResDto responseBody = new UpContentImageSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, fileName, imageUrl);
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            } else if (Objects.equals(dirName, "temp/thumbnail")) {
                UpThumbnailImageSuResDto responseBody = new UpThumbnailImageSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, fileName, imageUrl);
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            } else if (Objects.equals(dirName, "temp/profile")) {
                UpProfileImageSuResDto responseBody = new UpProfileImageSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, fileName, imageUrl);
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            } else {
                UpBannerImageSuResDto responseBody = new UpBannerImageSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, fileName, imageUrl);
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            }

        } catch (WrongFileExtensionException e) {
            logPrint(e);

            UpImageFaResDto responseBody = new UpImageFaResDto(ResponseCode.WRONG_FILE_EXTENSION, ResponseMessage.WRONG_FILE_EXTENSION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (IllegalArgumentException e) {
            logPrint(e);

            UpImageFaResDto responseBody = new UpImageFaResDto(ResponseCode.CONVERT_FAIL, ResponseMessage.CONVERT_FAIL);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        } catch (AmazonS3Exception e) {
            logPrint(e);

            UpImageFaResDto responseBody = new UpImageFaResDto(ResponseCode.UPLOAD_FAIL, ResponseMessage.UPLOAD_FAIL);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            UpImageFaResDto responseBody = new UpImageFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    private String randomFileName(File file, String dirName) {
        return dirName + "/" + UUID.randomUUID() + file.getName();
    }

    // 실제로 S3의 버킷에 객체(이미지)를 업로드하는 메소드
    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return getS3(bucket, fileName);
    }

    // S3에서 제공하는 URL 가져오기
    private String getS3(String bucket, String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    // S3에 업로드 하는 과정 중 로컬에 저장된 파일 삭제
    private void removeFile(File file) {
        file.delete();
    }

    private Boolean validateFileExtension(MultipartFile multipartFile) {
        List<String> allowedExtension = Arrays.asList("jpg", "png", "gif", "jpeg");
        String originalFileName = multipartFile.getOriginalFilename();

        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();

        return allowedExtension.contains(fileExtension);
    }

    private Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // 로컬에서 저장할 파일 경로 : user.dir -> 현재 디렉토리
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }

    @Override
    public void copyS3(String oldFileName, String newFileName) {
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucket, oldFileName, bucket, newFileName);
        copyObjectRequest.setCannedAccessControlList(CannedAccessControlList.PublicRead);
        amazonS3.copyObject(copyObjectRequest);
    }

    @Override
    public void deleteS3(String fileName) {
        amazonS3.deleteObject(bucket, fileName);
    }

    private void logPrint(Exception e) {
        log.error("Exception [Err_Msg]: {}", e.getMessage());
        log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);
    }

}
