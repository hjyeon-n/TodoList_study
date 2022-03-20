package com.example.study_spring.service;

import com.example.study_spring.model.TodoEntity;
import com.example.study_spring.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService() {
//        TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
//        TodoEntity 저장
        repository.save(entity);
//        TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        validate(entity);
        repository.save(entity);
        log.info("Entity Id : " + entity.getId() + "is saved");

        return repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
//        저장할 엔티티가 유효한지 확인
        validate(entity);

//        넘겨 받은 엔티티 id를 이용해 TodoEntity를 가져옴
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
//            반환된 todoEntity가 존재하면 값을 새 entity 값으로 덮어 씌움
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

//            데이터베이스에 새 값을 저장
            repository.save(todo);
        });

//        if (original.isPresent()) {
//            final TodoEntity todo = original.get();
//            todo.setTitle(entity.getTitle());
//            todo.setDone(entity.isDone());
//
//            repository.save(entity);
//        }

//        사용자의 모든 To do 리스트를 리턴
        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity) {
//        저장할 엔티티가 유효한지 확인
        validate(entity);

        try {
//            엔티티 삭제
            repository.delete(entity);
        } catch (Exception e) {
//            id와 exception을 로깅
            log.error("error deleting entity ", entity.getId(), e);

//            컨트롤러로 exception을 보낸다
            throw new RuntimeException("error deleting entity " + entity.getId());
        }
        return retrieve(entity.getUserId());
    }

    private void validate(final TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null.");
        }

        if (entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
