package com.flowerbowl.web.dto.request.review;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchReviewRequestDto {

    @NotNull
    private Integer review_score;

    private String review_content;
}
