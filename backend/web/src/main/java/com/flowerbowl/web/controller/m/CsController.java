//package com.flowerbowl.web.controller;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//// 나중에 고객센터도 운영하는 걸로 되면 만들기!
//@RestController
//@RequestMapping("api/cs")
//public class CsController {
//
//    // 고객센터 게시글 등록
//    // POST, json 형식
//    @PostMapping(value = "")
//    public String registerCs(@RequestBody Map<String, Object> inputData){
//        return "test /api/cs, POST\n" + inputData;
//    }
//
//    // 고객센터 게시글 수정
//    // PUT
//    @PutMapping(value = "")
//    public String modifyCs(@RequestBody Map<String, Object> inputData){
//        return "test /api/cs, PUT, 고객센터 게시글 수정\n" + inputData;
//    }
//    // 고객센터 게시글 조회
//    // GET
//    @GetMapping("")
//    public String getAllCs(){
//        return "test api/cs";
//    }
//}
