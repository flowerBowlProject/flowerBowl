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
public class CommunityFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_file_no")
    private Long communityFileNo;

    @Column(name = "community_file_oname")
    @Convert(converter = StringListConverter.class)
    private List<String> communityFileOname;

    @Column(name = "community_file_sname")
    @Convert(converter = StringListConverter.class)
    private List<String> communityFileSname;

    @ManyToOne
    @JoinColumn(name = "community_no")
    private Community community;

    public void updateFileOname(List<String> communityFileOname) {
        this.communityFileOname = communityFileOname;
    }

    public void updateFileSname(List<String> communitiyFileSname) {
        this.communityFileSname = communitiyFileSname;
    }

}
