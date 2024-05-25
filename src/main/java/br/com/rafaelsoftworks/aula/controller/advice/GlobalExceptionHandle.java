package br.com.rafaelsoftworks.aula.controller.advice;

import br.com.rafaelsoftworks.aula.exception.AbstractMinhaException;
import br.com.rafaelsoftworks.aula.model.json.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@RestController
@ControllerAdvice
public class GlobalExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAbstractMinhaException(AbstractMinhaException ex, HttpServletRequest request) throws IOException {
        return ResponseEntity.internalServerError().body(new ExceptionResponse(ex, request.getRequestURI()));
    }
}