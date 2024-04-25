package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.CommunityFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityFileRepository extends JpaRepository<CommunityFile, Long> {

    CommunityFile findByCommunity_CommunityNo(Long communityNo);

}
