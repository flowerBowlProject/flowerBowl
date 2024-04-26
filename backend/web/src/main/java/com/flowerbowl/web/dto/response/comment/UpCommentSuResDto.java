package com.flowerbowl.web.dto.response.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpCommentSuResDto extends CommentResponseDto {

    private Long comment_no;

    public UpCommentSuResDto(String code, String message, Long commentNo) {
        super(code, message);
        this.comment_no = commentNo;
    }

}
