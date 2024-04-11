package com.flowerbowl.web.dto.object.community;

import com.flowerbowl.web.domain.Community;
import com.flowerbowl.web.domain.CommunityFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CreateCommunityFileDto {

    private List<String> communityFileOname;

    private List<String> communityFileSname;

    private Community community;

    public CommunityFile toEntity() {
        return CommunityFile.builder()
                .communityFileOname(communityFileOname)
                .communityFileSname(communityFileSname)
                .community(community)
                .build();
    }

}
