package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Optional<Community> findByCommunityNo(Long communityNo);

}
