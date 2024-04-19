package com.flowerbowl.web.domain;

import com.flowerbowl.web.common.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_file_no")
    private Long recipeFileNo;

    @Column(name = "recipe_file_oname")
    @Convert(converter = StringListConverter.class)
    private List<String> recipeFileOname;

    @Column(name = "recipe_file_sname")
    @Convert(converter = StringListConverter.class)
    private List<String> recipeFileSname;

    @ManyToOne
    @JoinColumn(name = "recipe_no")
    private Recipe recipe;

    public void updateFileOname(List<String> recipeFileOname) {
        this.recipeFileOname = recipeFileOname;
    }

    public void updateFileSname(List<String> recipeFileSname) {
        this.recipeFileSname = recipeFileSname;
    }

}
