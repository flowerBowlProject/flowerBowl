package com.flowerbowl.web.repository;

import com.flowerbowl.web.domain.EmailCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmailCertificationRepository extends JpaRepository<EmailCertification, Long> {

    boolean existsByEmail(String userEmail);

    @Transactional
    void deleteByEmail(String userEmail);

    EmailCertification findByEmail(String userEmail);
}
