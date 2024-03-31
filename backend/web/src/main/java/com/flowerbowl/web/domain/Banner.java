package com.flowerbowl.web.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_no")
    private Long bannerNo;


    @Column(name = "banner_content")
    private String bannerContent;


    @Column(name = "banner_file_sname")
    private String bannerFileSname;


    @Column(name = "banner_file_oname")
    private String baanerFileOname;

}
