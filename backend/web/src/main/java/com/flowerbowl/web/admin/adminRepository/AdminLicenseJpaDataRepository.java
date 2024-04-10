package com.flowerbowl.web.admin.adminRepository;

import com.flowerbowl.web.domain.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminLicenseJpaDataRepository extends JpaRepository<License, Long> {
    // 쉐프후보 전체 조회
    List<License> findAllByLicenseStatus(Boolean license_status);
    // 쉐프 승인
    Boolean existsLicenseByUser_UserNo(Long user_userNo);
    Boolean existsLicenseByLicenseNo(Long license_no);
    License findByUser_UserNo(Long user_userNo);
    void deleteLicenseByLicenseNo(Long license_no);
    License findLicenseByLicenseNo(Long license_no);
}
