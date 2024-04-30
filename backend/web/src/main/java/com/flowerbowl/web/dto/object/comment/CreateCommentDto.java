package com.flowerbowl.web.dto.object.comment;

import com.flowerbowl.web.domain.Comment;
import com.flowerbowl.web.domain.Community;
import com.flowerbowl.web.domain.Recipe;
import com.flowerbowl.web.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CreateCommentDto {

    private String commentContent;

    private LocalDateTime commentDate;

    private Long parentNo;

    private User user;

    private Recipe recipe;

    private Community community;

    public Comment toEntity() {
        return Comment.builder()
                .commentContent(commentContent)
                .commentDate(commentDate)
                .parentNo(parentNo)
                .user(user)
                .recipe(recipe)
                .community(community)
                .build();
    }

}
