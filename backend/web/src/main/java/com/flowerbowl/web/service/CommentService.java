package com.flowerbowl.web.service;

import com.flowerbowl.web.dto.request.comment.CrCommentReqDto;
import com.flowerbowl.web.dto.request.comment.UpCommentReqDto;
import com.flowerbowl.web.dto.response.comment.CommentResponseDto;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    public ResponseEntity<? extends CommentResponseDto> createComment(CrCommentReqDto request, String userId) throws Exception;

    public ResponseEntity<? extends CommentResponseDto> updateComment(UpCommentReqDto request, Long comment_no, String userId) throws Exception;

    public ResponseEntity<? extends CommentResponseDto> deleteComment(Long comment_no, String userId) throws Exception;

}
