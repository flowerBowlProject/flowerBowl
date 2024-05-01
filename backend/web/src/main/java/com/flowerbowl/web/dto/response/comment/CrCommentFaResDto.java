package com.flowerbowl.web.dto.response.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrCommentFaResDto extends CommentResponseDto {

    public CrCommentFaResDto(String code, String message) {
        super(code, message);
    }

}
