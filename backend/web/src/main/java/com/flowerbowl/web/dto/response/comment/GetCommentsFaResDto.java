package com.flowerbowl.web.dto.response.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetCommentsFaResDto extends CommentResponseDto {

    public GetCommentsFaResDto(String code, String message) {
        super(code, message);
    }

}
