package com.flowerbowl.web.dto.request.review;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertReviewRequestDto {

    @NotBlank
    private String review_content;

    @NotNull
    private Integer review_score;

    @NotNull
    private Long lesson_no;
}
