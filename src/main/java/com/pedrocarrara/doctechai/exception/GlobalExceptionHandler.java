package com.pedrocarrara.doctechai.exception;

import com.pedrocarrara.doctechai.dto.ErroResponse;
import com.pedrocarrara.doctechai.dto.ErroCampoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DocumentacaoNaoEncontradaException.class)
    public ErroResponse handleDocumentacaoNaoEncontrada(DocumentacaoNaoEncontradaException ex) {
        return new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                List.of(new ErroCampoResponse("id", ex.getMessage()))
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErroCampoResponse> errors = ex.getBindingResult().getFieldErrors().stream().map(
                erro -> new ErroCampoResponse(erro.getField(), erro.getDefaultMessage())).toList();
        return new ErroResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), errors);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PdfGenerationException.class)
    public ErroResponse handlePdfGeneration(PdfGenerationException ex) {
        return new ErroResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                List.of(new ErroCampoResponse("pdf", ex.getMessage()))
        );
    }
}
