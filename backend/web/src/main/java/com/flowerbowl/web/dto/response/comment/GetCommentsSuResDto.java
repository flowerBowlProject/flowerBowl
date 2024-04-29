package com.flowerbowl.web.dto.response.comment;

import com.flowerbowl.web.dto.object.comment.CommentPageInfo;
import com.flowerbowl.web.dto.object.comment.GetCommentsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetCommentsSuResDto extends CommentResponseDto {

    private List<GetCommentsDto> comments;

    private CommentPageInfo pageInfo;

    public GetCommentsSuResDto(String code, String message, List<GetCommentsDto> comments, CommentPageInfo pageInfo) {
        super(code, message);
        this.comments = comments;
        this.pageInfo = pageInfo;
    }

}
