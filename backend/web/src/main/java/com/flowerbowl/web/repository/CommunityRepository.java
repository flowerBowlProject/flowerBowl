package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    Optional<Community> findByCommunityNo(Long communityNo);

    Page<Community> findAllByOrderByCommunityNoDesc(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE community " +
            "SET " +
            "   community_writer = :newNickname " +
            "WHERE " +
            "   user_no = (SELECT user_no FROM user WHERE user_id = :userId) ", nativeQuery = true)
    void updateCommunityWriter(@Param("userId") String userId, @Param("newNickname") String newNickname);
}
