package com.flowerbowl.web.dto.response.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpCommentFaResDto extends CommentResponseDto {

    public UpCommentFaResDto(String code, String message) {
        super(code, message);
    }

}
