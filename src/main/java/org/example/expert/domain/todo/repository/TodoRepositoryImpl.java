package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.dto.response.TodoSearchResultDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Todo> findAllByOrderByModifiedAtDesc(
            String weather, LocalDateTime startAt,LocalDateTime endAt,
            Pageable pageable){

        List<Todo> results = queryFactory.selectFrom(todo)
                .where(
                        weather == null ? null : todo.weather.eq(weather),
                        startAt == null ? null : todo.modifiedAt.goe(startAt),
                        endAt == null ? null : todo.modifiedAt.loe(endAt)
                )
                .orderBy(todo.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(todo.count())
                .from(todo)
                .where(
                        weather == null ? null : todo.weather.eq(weather),
                        startAt == null ? null : todo.modifiedAt.goe(startAt),
                        endAt == null ? null : todo.modifiedAt.loe(endAt)
                )
                .fetchOne();

        return new PageImpl<>(results, pageable, total == null ? 0 : total);
    }


    @Override
    public Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId){

        Todo result = queryFactory.selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();
        return Optional.ofNullable(result);

    }

    @Override
    public Page<TodoSearchResultDto> searchTodo(String keyword, LocalDateTime modifiedAt,
                                                String nickname, Pageable pageable) {

        JPAQuery<TodoSearchResultDto> query = queryFactory
                .select(Projections.constructor(
                TodoSearchResultDto.class,
                todo.title,
                todo.managers.size(),
                todo.comments.size()
        )).from(todo)
                .leftJoin(todo.managers,manager)
                .where(
                        containKeyword(todo.title, keyword),
                        modifiedAt == null ? null : todo.modifiedAt.loe(modifiedAt),
                        containNickname(manager.user.nickname, nickname)
                )
                .orderBy(todo.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        List<TodoSearchResultDto> results = query.fetch();
        Long total = queryFactory.select(todo.count())
                .from(todo)
                .leftJoin(todo.managers, manager)
                .where(
                        containKeyword(todo.title, keyword),
                        modifiedAt == null ? null : todo.modifiedAt.loe(modifiedAt),
                        containNickname(manager.user.nickname, nickname)
                )
                .fetchOne();
        return new PageImpl<>(results, pageable, total == null ? 0 : total);
    }
    private BooleanExpression containKeyword(StringPath path, String keyword) {
        return keyword == null || keyword.isEmpty() ? null : path.contains(keyword);
    }

    private BooleanExpression containNickname(StringPath path, String nickname) {
        return nickname == null || nickname.isEmpty() ? null : path.contains(nickname);
    }
}
