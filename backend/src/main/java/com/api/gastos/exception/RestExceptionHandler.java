package com.api.gastos.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(EntityNotFoundException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno");
        problemDetail.setTitle("Integridade de dados violada");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("stacktrace", e.getStackTrace());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
        problemDetail.setTitle("Parâmetro inválido");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("stacktrace", e.getStackTrace());
        return problemDetail;
    }

    @ExceptionHandler(CsvException.class)
    public ProblemDetail handleCsvException(CsvException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno");
        problemDetail.setTitle("Erro ao processar arquivo");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("stacktrace", e.getStackTrace());
        return problemDetail;
    }

    @ExceptionHandler(IOException.class)
    public ProblemDetail handleIOException(IOException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno");
        problemDetail.setTitle("Erro ao processar arquivo");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("stacktrace", e.getStackTrace());
        return problemDetail;
    }

    @ExceptionHandler(NumberFormatException.class)
    public ProblemDetail handleNumberFormatException(NumberFormatException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, "Valor inválido");
        problemDetail.setTitle("Erro ao processar arquivo, valor inválido");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("stacktrace", e.getStackTrace());
        return problemDetail;
    }

}
