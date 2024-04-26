package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long commentNo;

    @Column(name = "comment_content")
    private String commentContent;

    @Column(name = "comment_date")
    private LocalDateTime commentDate;

    @Column(name = "parent_no")
    private Long parentNo;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_no")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "community_no")
    private Community community;

    public void updateContent(String commentContent) {
        this.commentContent = commentContent;
    }

}
