package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_no")
    private Long communityNo;

    @Column(name = "community_title")
    private String communityTitle;

    @Column(name = "community_content", columnDefinition = "TEXT")
    private String communityContent;

    @Column(name = "community_date")
    private LocalDate communityDate;

    @Column(name = "community_writer")
    private String communityWriter;

    @Column(name = "community_views")
    private Long communityViews;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<CommunityFile> communityFiles = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

}

