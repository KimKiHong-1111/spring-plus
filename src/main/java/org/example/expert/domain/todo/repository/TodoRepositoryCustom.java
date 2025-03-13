package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoSearchResultDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepositoryCustom {
    Page<Todo> findAllByOrderByModifiedAtDesc(String weather, LocalDateTime startAt, LocalDateTime endAt, Pageable pageable);

    Optional<Todo> findByIdWithUser(Long todoId);

    Page<TodoSearchResultDto> searchTodo(String search, LocalDateTime modifiedAt,
                                         String nickname, Pageable pageable);
}

