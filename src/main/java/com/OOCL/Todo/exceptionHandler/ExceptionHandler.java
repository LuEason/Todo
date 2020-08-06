package com.OOCL.Todo.exceptionHandler;

import com.OOCL.Todo.exception.NoSuchDataException;
import com.OOCL.Todo.exception.NotTheSameIDException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchDataException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleNoSuchDataException() {
        return "No such data.";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotTheSameIDException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleNotTheSameIDException() {
        return "These two ids are different.";
    }
}
