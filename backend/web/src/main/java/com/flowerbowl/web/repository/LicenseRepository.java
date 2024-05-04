package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends JpaRepository <License, Long>{
}
