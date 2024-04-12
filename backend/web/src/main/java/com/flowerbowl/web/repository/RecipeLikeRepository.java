package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.RecipeLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long> {

    List<RecipeLike> findAllByUser_UserNo(Long userNo);
}

