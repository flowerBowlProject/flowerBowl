package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.RecipeLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeLikeRepository  extends JpaRepository<RecipeLike, Long> {

    List<RecipeLike> findAllByUser_UserNo(Long userNo);
}