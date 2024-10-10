package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepositoryCustom {
    //TodoRepository 에 QueryDSL 을 위한 커스텀 인터페이스 추가
    Optional<Todo> findByIdWithUser(Long todoId);

    Page<TodoSearchResponse> searchTodos(
            Pageable pageable,
            String title,
            String nickname,
            String weather,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
