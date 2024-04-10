//package com.flowerbowl.web.repository;
//
//
//import com.flowerbowl.web.domain.License;
//import jakarta.persistence.EntityManager;
//import org.hibernate.engine.spi.SessionLazyDelegator;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class JpaDataLicenseRepositoryTest {
//    //    EntityManager entityManager;
////    JpaLicenseRepository repository = new JpaLicenseRepository(entityManager);
//    @Autowired
//    private JpaDataLicenseRepository jpaDataLicenseRepository;
//
//    @Test
//    void testJpa(){
//        Optional<List<License>> testResult = this.jpaLicenseRepository.findByLicense_status(false);
//        if(testResult.isEmpty()){
//            System.out.println("test is empty!");
//        }else{
//            System.out.println(testResult);
//        }
//
//    }
//}
