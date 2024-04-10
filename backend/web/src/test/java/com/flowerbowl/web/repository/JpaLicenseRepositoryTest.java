//package com.flowerbowl.web.repository;
//
//import com.flowerbowl.web.domain.License;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.Optional;
//
//public class JpaLicenseRepositoryTest {
//
//    @Autowired
//    private JpaLicenseRepository jpaLicenseRepository;
//
////    @Autowired
////    public JpaLicenseRepositoryTest(JpaLicenseRepository jpaLicenseRepository){
////        this.jpaLicenseRepository = jpaLicenseRepository;
////    }
//
//    @Test
//    void test() throws Exception{
//        Optional<List<License>> testResult = jpaLicenseRepository.findByLicense_status(false);
//        if(testResult.isEmpty()){
//            System.out.println("result is empty");
//        }else{
//            System.out.println(testResult);
//        }
//    }
//}
