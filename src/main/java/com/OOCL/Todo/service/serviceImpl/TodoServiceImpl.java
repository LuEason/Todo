package com.OOCL.Todo.service.serviceImpl;

import com.OOCL.Todo.exception.NoSuchDataException;
import com.OOCL.Todo.exception.NotTheSameIDException;
import com.OOCL.Todo.model.Todo;
import com.OOCL.Todo.repository.TodoRepository;
import com.OOCL.Todo.service.TodoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo insertTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Integer id, Todo sourceTodo) throws NotTheSameIDException, NoSuchDataException {
        if (!sourceTodo.getId().equals(id)){
            throw new NotTheSameIDException();
        }
        Todo targetTodo = todoRepository.findById(id).orElse(null);
        if (targetTodo == null) {
            throw new NoSuchDataException();
        }
        BeanUtils.copyProperties(sourceTodo, targetTodo);
        return todoRepository.save(targetTodo);
    }

    public boolean deleteById(Integer id) {
        todoRepository.deleteById(id);
        return !todoRepository.findById(id).isPresent();
    }
}
