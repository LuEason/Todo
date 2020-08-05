package com.OOCL.Todo.service;

import com.OOCL.Todo.model.Todo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TodoService {
    List<Todo> getAllTodos();

    Todo insertTodo(Todo todo);

    Todo updateTodo(Integer id, Todo todo);

    boolean deleteById(Integer id);
}
