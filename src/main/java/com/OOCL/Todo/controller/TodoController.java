package com.OOCL.Todo.controller;

import com.OOCL.Todo.exception.NoSuchDataException;
import com.OOCL.Todo.exception.NotTheSameIDException;
import com.OOCL.Todo.model.Todo;
import com.OOCL.Todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAll() {
        return todoService.getAllTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo insertTodo(@RequestBody Todo todo) {
        return todoService.insertTodo(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable int id, @RequestBody Todo todo) throws NoSuchDataException, NotTheSameIDException {
        return todoService.updateTodo(id, todo);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTodo(@PathVariable int id) {
        return todoService.deleteById(id);
    }
}
