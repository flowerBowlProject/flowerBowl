package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CommunityFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_file_no")
    private Long communityFileNo;

    @Column(name = "community_file_oname")
    private String communityFileOname;

    @Column(name = "community_file_sname")
    private String communityFileSname;

    @ManyToOne
    @JoinColumn(name = "community_no")
    private Community community;

}
