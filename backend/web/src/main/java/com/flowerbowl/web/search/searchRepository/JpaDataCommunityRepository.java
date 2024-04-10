package com.flowerbowl.web.search.searchRepository;

import com.flowerbowl.web.domain.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDataCommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findAllByCommunityTitleContainingOrCommunityContentContainingOrderByCommunityNoDesc(String keyword1, String keyword2, Pageable pageable);
}
