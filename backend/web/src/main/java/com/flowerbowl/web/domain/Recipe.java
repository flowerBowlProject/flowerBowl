package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_no")
    private Long recipeNo;

    @Column(name = "recipe_title")
    private String recipeTitle;

    @Column(name = "recipe_date")
    private LocalDate recipeDate;

    @Column(name = "recipe_stuff")
    private String recipeStuff;

    @Column(name = "recipe_oname")
    private String recipeOname;

    @Column(name = "recipe_sname")
    private String recipeSname;

    @Column(name = "recipe_content", columnDefinition = "TEXT")
    private String recipeContent;

    @Column(name = "recipe_category")
    @Enumerated(EnumType.STRING)
    private Category recipeCategory;

    @Column(name = "recipe_writer")
    private String recipeWriter;

    @Column(name = "recipe_views")
    private Long recipeViews;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeLike> recipeLikes;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeFile> recipeFiles;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
