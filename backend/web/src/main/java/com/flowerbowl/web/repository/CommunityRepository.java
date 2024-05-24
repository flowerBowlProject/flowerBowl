package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    Optional<Community> findByCommunityNo(Long communityNo);

    Page<Community> findAllByOrderByCommunityNoDesc(Pageable pageable);

}
