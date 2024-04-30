package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_like_no")
    private Long recipeLikeNo;

    @ManyToOne
    @JoinColumn(name = "recipe_no")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

}
