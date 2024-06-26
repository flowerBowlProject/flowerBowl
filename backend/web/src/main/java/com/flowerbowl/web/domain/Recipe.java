package com.flowerbowl.web.domain;

import com.flowerbowl.web.common.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
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
    @Convert(converter = StringListConverter.class)
    private List<String> recipeStuff;

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

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE)
    private List<RecipeFile> recipeFiles;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public void updateTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public void updateCategory(Category recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    public void updateStuff(List<String> recipeStuff) {
        this.recipeStuff = recipeStuff;
    }
//    public void updateStuff(String recipeStuff) {
//        this.recipeStuff = recipeStuff;
//    }

    public void updateContent(String recipeContent) {
        this.recipeContent = recipeContent;
    }

    public void updateOname(String recipeOname) {
        this.recipeOname = recipeOname;
    }

    public void updateSname(String recipeSname) {
        this.recipeSname = recipeSname;
    }

    public void updateView(Long recipeViews) {
        this.recipeViews = recipeViews;
    }

}
