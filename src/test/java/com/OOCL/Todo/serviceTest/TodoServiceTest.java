package com.OOCL.Todo.serviceTest;

import com.OOCL.Todo.model.Todo;
import com.OOCL.Todo.repository.TodoRepository;
import com.OOCL.Todo.service.TodoService;
import com.OOCL.Todo.service.serviceImpl.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TodoServiceTest {
    private TodoRepository mockedTodoRepository;
    private TodoService  todoService;

    @BeforeEach
    void init() {
        mockedTodoRepository = Mockito.mock(TodoRepository.class);
        todoService = new TodoServiceImpl(mockedTodoRepository);
    }

    @Test
    void should_return_No_data_when_call_getAllTodos_given_none() {
        //when
        List<Todo> todos = todoService.getAllTodos();

        //then
        assertEquals(0, todos.size());
    }

    @Test
    void should_return_all_todo_when_call_getAllTodos_given_none() {
        //given
        Todo todo1 = new Todo();
        Todo todo2 = new Todo();
        Todo todo3 = new Todo();
        when(mockedTodoRepository.findAll()).thenReturn(asList(todo1, todo2, todo3));

        //when
        List<Todo> todos = todoService.getAllTodos();

        //then
        assertEquals(3, todos.size());
    }

    @Test
    void should_return_inserted_todo_when_call_insertTodo_given_todo() {
        //given
        Todo todo = new Todo("text", "UNDONE");
        Todo insertedTodo = new Todo("text", "UNDONE");
        insertedTodo.setId(1);
        when(mockedTodoRepository.save(todo)).thenReturn(insertedTodo);

        //when
        Todo returnedTodo = todoService.insertTodo(todo);

        //then
        assertEquals(insertedTodo, returnedTodo);
    }
}
