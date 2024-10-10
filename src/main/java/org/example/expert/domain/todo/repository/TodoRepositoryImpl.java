package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        QTodo todo = QTodo.todo;

        Todo result = queryFactory.selectFrom(todo)
                .leftJoin(todo.user).fetchJoin() // User 엔티티를 fetch join 으로 가져오기
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(Pageable pageable, String title, String nickname, String weather, LocalDateTime startDate, LocalDateTime endDate) {
        List<TodoSearchResponse> results = queryFactory
                .select(Projections.constructor(
                        TodoSearchResponse.class,
                        todo.id,
                        todo.title,
                        todo.user.countDistinct(),  // 담당자 수
                        comment.countDistinct()    // 댓글 수
                ))
                .from(todo)
                .leftJoin(todo.user, user)
                .leftJoin(todo.comments, comment)
                .where(
                        titleContains(title),
                        nicknameContains(nickname),
                        createdBetween(startDate, endDate),
                        weatherEquals(weather)  // 날씨 조건 추가
                )
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())  // 최신순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        // NullPointerException 방지를 위한 fetchOne() 처리
        long total = Optional.ofNullable(queryFactory
                .select(todo.count())
                .from(todo)
                .where(
                        titleContains(title),
                        nicknameContains(nickname),
                        createdBetween(startDate, endDate),
                        weatherEquals(weather)
                )
                .fetchOne()).orElse(0L); // Null 값 처리, 기본값 0 설정
        return new PageImpl<>(results, pageable, total);
    }
    private BooleanExpression titleContains(String title) {
        return title != null ? todo.title.containsIgnoreCase(title) : null;
    }
    private BooleanExpression nicknameContains(String nickname) {
        return nickname != null ? user.nickname.containsIgnoreCase(nickname) : null;
    }
    private BooleanExpression createdBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            return todo.createdAt.between(startDate, endDate);
        } else if (startDate != null) {
            return todo.createdAt.goe(startDate);
        } else if (endDate != null) {
            return todo.createdAt.loe(endDate);
        }
        return null;
    }
    private BooleanExpression weatherEquals(String weather) {
        return weather != null ? todo.weather.eq(weather) : null;
    }
}
