package com.uri.progweb.userdep.exceptions.handler;

import com.uri.progweb.userdep.exceptions.ErrorMessage;
import com.uri.progweb.userdep.exceptions.TarefaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TarefaNotFoundException.class)
    private ResponseEntity<ErrorMessage> tarefaNotFoundHandler(TarefaNotFoundException exception) {
        ErrorMessage response = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
