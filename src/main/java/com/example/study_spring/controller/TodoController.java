package com.example.study_spring.controller;

import com.example.study_spring.dto.ResponseDto;
import com.example.study_spring.dto.TodoDto;
import com.example.study_spring.model.TodoEntity;
import com.example.study_spring.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {
    @Autowired
    TodoService service;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDto dto) {
        try {
            String tempUserId = "temp-user";

//            TodoEntity로 변환
            TodoEntity entity = TodoDto.toEntity(dto);

//            id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 때문이다.
            entity.setId(null);

//            임시 사용자 아이디를 설정 -> 후에 수정 예정
//            지금은 한 사용자만 로그인 없이 사용할 수 있는 애플리케이션
            entity.setUserId(tempUserId);

//            서비스를 이용해 To do 엔티티를 생성
            List<TodoEntity> entities = service.create(entity);

//            자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDto 리스트로 변환
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

//            변환된 TodoDto 리스트를 이용해 ResponseDto를 초기화
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                    .data(dtos)
                    .build();

//            ResponseDto를 리턴
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
//            예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴
            String error = e.getMessage();
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                    .error(error)
                    .build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String tempUserId = "temp-user";

//        서비스 메소드의 retrieve() 메소드를 이용해 To do 리스트를 가져온다
        List<TodoEntity> entities = service.retrieve(tempUserId);

//        자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDto 리스트로 변환
        List<TodoDto> dtos = entities.stream().map(TodoDto::new)
                .collect(Collectors.toList());

//        변환된 TodoDto 리스트를 이용해 ResponseDto를 초기화
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();

        //            ResponseDto를 리턴
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDto dto) {
        String tempUserId = "temp-user";

//        dto를 entity로 변환
        TodoEntity entity = TodoDto.toEntity(dto);

//        id를 tempUserId로 초기화
        entity.setUserId(tempUserId);

//        서비스를 이용해 entity를 업데이트
        List<TodoEntity> entities = service.update(entity);

//         자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDto 리스트로 변환
        List<TodoDto> dtos = entities.stream().map(TodoDto::new)
                .collect(Collectors.toList());

//        변환된 TodoDto 리스트를 이용해 ResponseDto를 초기화
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDto dto) {
        try {
            String tempUserId = "temp-user";

//            TodoEntity로 변환
            TodoEntity entity = TodoDto.toEntity(dto);

//            id를 tempUserId로 초기화
            entity.setUserId(tempUserId);

//            서비스를 이용해 entity 삭제
            List<TodoEntity> entities = service.delete(entity);

//            자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDto 리스트로 변환
            List<TodoDto> dtos = entities.stream().map(TodoDto::new)
                    .collect(Collectors.toList());

//        변환된 TodoDto 리스트를 이용해 ResponseDto를 초기화
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                    .error(error)
                    .build();

            return ResponseEntity.badRequest().body(response);
        }

    }
}
