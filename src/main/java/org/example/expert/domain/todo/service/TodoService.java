package org.example.expert.domain.todo.service;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.example.expert.client.WeatherClient;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.log.service.LogService;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.InvalidIsolationLevelException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final WeatherClient weatherClient;
    private final LogService logService;

    @Transactional
    public TodoSaveResponse saveTodo(AuthUser authUser, TodoSaveRequest todoSaveRequest) {
        User user = User.fromAuthUser(authUser);

//        String weather = weatherClient.getTodayWeather();
        String weather = null;

        try {
            if (weather == null) {
                throw new InvalidIsolationLevelException("날씨 데이터 오류");
            }

            Todo newTodo = new Todo(
                    todoSaveRequest.getTitle(),
                    todoSaveRequest.getContents(),
                    weather,
                    user
            );
            Todo savedTodo = todoRepository.save(newTodo);

            return new TodoSaveResponse(
                    savedTodo.getId(),
                    savedTodo.getTitle(),
                    savedTodo.getContents(),
                    weather,
                    new UserResponse(user.getId(), user.getEmail(), user.getNickname())
            );
        } catch (InvalidRequestException e) {
            logService.saveLog("일정 등록", "FAIL");
            throw e;
        }
    }

    public Page<TodoSearchResponse> getTodos(int page, int size, String title, String nickname, String weather, LocalDateTime startDate, LocalDateTime endDate) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return todoRepository.searchTodos(pageable, title, nickname, weather, startDate, endDate);
    }

    public TodoResponse getTodo(long todoId) {
        Todo todo = todoRepository.findByIdWithUser(todoId)
                .orElseThrow(() -> new InvalidRequestException("Todo not found"));

        User user = todo.getUser();

        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(user.getId(), user.getEmail(), todo.getUser().getNickname()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }
}
