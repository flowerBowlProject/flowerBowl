package com.flowerbowl.web.domain;

import jakarta.persistence.*;

@Entity
public class RecipeFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_file_no")
    private Long recipeFileNo;

    @Column(name = "recipe_file_oname")
    private String recipeFileOname;

    @Column(name = "recipe_file_sname")
    private String recipeFileSname;

    @ManyToOne
    @JoinColumn(name = "recipe_no")
    private Recipe recipe;
}
