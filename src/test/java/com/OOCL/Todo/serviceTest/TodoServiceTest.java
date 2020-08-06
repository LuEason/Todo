package com.OOCL.Todo.serviceTest;

import com.OOCL.Todo.exception.NoSuchDataException;
import com.OOCL.Todo.exception.NotTheSameIDException;
import com.OOCL.Todo.model.Todo;
import com.OOCL.Todo.repository.TodoRepository;
import com.OOCL.Todo.service.TodoService;
import com.OOCL.Todo.service.serviceImpl.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void should_throw_NotTheSameIDException_when_updateTodo_given_id_and_sourceTodo() {
        //given
        int id = 1;
        Todo sourceTodo = new Todo();
        sourceTodo.setId(2);

        //when
        Exception exception = assertThrows(NotTheSameIDException.class, () -> todoService.updateTodo(id, sourceTodo));

        //then
        assertEquals(NotTheSameIDException.class, exception.getClass());
    }

    @Test
    void should_throw_NoSuchDataException_when_updateTodo_given_id_and_sourceTodo() {
        //given
        int id = 1;
        Todo sourceTodo = new Todo();
        sourceTodo.setId(1);
        when(mockedTodoRepository.findById(id)).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(NoSuchDataException.class, () -> todoService.updateTodo(id, sourceTodo));

        //then
        assertEquals(NoSuchDataException.class, exception.getClass());
    }

    @Test
    void should_update_todo_when_updateTodo_given_id_and_sourceTodo() throws NoSuchDataException, NotTheSameIDException {
        //given
        int id = 1;
        Todo sourceTodo = new Todo("text1", "DONE");
        sourceTodo.setId(1);
        Todo targetTodo = new Todo("text1", "UNDONE");
        targetTodo.setId(1);
        when(mockedTodoRepository.findById(id)).thenReturn(Optional.of(targetTodo));
        targetTodo.setStatus("DONE");
        when(mockedTodoRepository.save(targetTodo)).thenReturn(targetTodo);

        //when
        Todo updatedTodo = todoService.updateTodo(id, sourceTodo);

        //then
        assertEquals(targetTodo, updatedTodo);
    }

    @Test
    void should_throw_NoSuchDataException_when_deleteById_given_id() {
        //given
        int id = 1;
        when(mockedTodoRepository.findById(id)).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(NoSuchDataException.class, () -> todoService.deleteById(id));

        //then
        assertEquals(NoSuchDataException.class, exception.getClass());
    }

    @Test
    void should_delete_todo_when_deleteById_given_id() throws NoSuchDataException {
        //given
        int id = 1;
        when(mockedTodoRepository.findById(id)).thenReturn(Optional.of(new Todo()));

        //when
        boolean isDelete = todoService.deleteById(id);

        //then
        assertTrue(isDelete);
        Mockito.verify(mockedTodoRepository).deleteById(id);
    }
}
