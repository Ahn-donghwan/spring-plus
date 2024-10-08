package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;

import java.util.Optional;

public interface TodoRepositoryCustom {
    //TodoRepository 에 QueryDSL 을 위한 커스텀 인터페이스 추가
    Optional<Todo> findByIdWithUser(Long todoId);
}
