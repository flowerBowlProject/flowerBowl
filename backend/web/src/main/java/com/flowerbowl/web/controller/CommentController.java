package com.flowerbowl.web.controller;

import com.flowerbowl.web.dto.request.comment.CrCommentReqDto;
import com.flowerbowl.web.dto.request.comment.UpCommentReqDto;
import com.flowerbowl.web.dto.response.comment.CommentResponseDto;
import com.flowerbowl.web.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    private ResponseEntity<? extends CommentResponseDto> createComment(@RequestBody CrCommentReqDto request, @AuthenticationPrincipal String userId) throws Exception {
        return commentService.createComment(request, userId);
    }

    @PutMapping("/{comment_no}")
    private ResponseEntity<? extends CommentResponseDto> updateComment(@RequestBody UpCommentReqDto request, @PathVariable Long comment_no, @AuthenticationPrincipal String userId) throws Exception {
        return commentService.updateComment(request, comment_no, userId);
    }

}
