package com.OOCL.Todo.service;

import com.OOCL.Todo.exception.NoSuchDataException;
import com.OOCL.Todo.exception.NotTheSameIDException;
import com.OOCL.Todo.model.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> getAllTodos();

    Todo insertTodo(Todo todo);

    Todo updateTodo(Integer id, Todo todo) throws NotTheSameIDException, NoSuchDataException;

    boolean deleteById(Integer id) throws NoSuchDataException;
}
