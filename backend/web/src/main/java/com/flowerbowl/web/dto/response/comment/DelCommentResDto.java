package com.flowerbowl.web.dto.response.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DelCommentResDto extends CommentResponseDto {

    public DelCommentResDto(String code, String message) {
        super(code, message);
    }

}
