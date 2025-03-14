package org.example.expert.domain.todo.service;

import org.example.expert.client.WeatherClient;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;
    @Mock
    private WeatherClient weatherClient;

    @InjectMocks
    private TodoService todoService;

    private User user;

    private AuthUser authUser;



    @Test
    void saveTodo성공하기() {
        //given
        TodoSaveRequest request = new TodoSaveRequest("3/14 할일", "테스트코드짜기");
        String weather = "맑음";
        when(weatherClient.getTodayWeather()).thenReturn(weather);
        Todo newTodo = new Todo(request.getTitle(),request.getContents(),weather,user);

        //when
        TodoSaveResponse response = todoService.saveTodo(authUser, request);

        //then
        assertEquals(newTodo.getId(), response.getId());
        assertEquals(newTodo.getTitle(), response.getTitle());
        assertEquals(newTodo.getContents(), response.getContents());
        assertEquals(weather, response.getWeather());
        assertEquals(user.getId(), response.getUser().getId());
        assertEquals(user.getEmail(), response.getUser().getEmail());

    }

    @Test
    void getTodos() {
    }

    @Test
    void getTodo() {
    }

    @Test
    void searchTodos() {
    }
}