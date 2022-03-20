package com.example.study_spring.controller;

import com.example.study_spring.dto.ResponseDto;
import com.example.study_spring.dto.TestRequestBodyDto;
import com.example.study_spring.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("todo")
public class TestController {

    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testController() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);

        ResponseDto<String> response = ResponseDto.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

//    @GetMapping("/{id}")
//    public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
//        return "Hello World! ID " + id;
//    }
//
//    @GetMapping("/testRequestParam")
//    public String testControllerRequestParam(@RequestParam(required = false) int id) {
//        return "Hello World! ID " + id;
//    }
//
//    @GetMapping("/testRequestBody")
//    public String testControllerRequestBody(@RequestBody TestRequestBodyDto testRequestBodyDto) {
//        return "Hello World ID " + testRequestBodyDto.getId() + " Message : " + testRequestBodyDto.getMessage();
//    }
//
//    @GetMapping("/testResponseBody")
//    public ResponseDto<String> testControllerResponseBody() {
//        List<String> list = new ArrayList<>();
//        list.add("Hello World! I'm ResponseDto");
//        ResponseDto<String> response = ResponseDto.<String>builder()
//                .data(list)
//                .build();
//        return response;
//    }
//
//    @GetMapping("/testResponseEntity")
//    public ResponseEntity<?> testControllerResponseEntity() {
//        List<String> list = new ArrayList<>();
//        list.add("Hello Wolrd! I'm ResponseEntity. And you got 400!");
//        ResponseDto<String> response = ResponseDto.<String>builder()
//                .data(list)
//                .build();
//        return ResponseEntity.ok().body(response);
//    }
}
