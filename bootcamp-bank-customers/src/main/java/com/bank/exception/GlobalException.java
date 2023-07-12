package com.bank.exception;

import com.bank.openapi.model.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageResponse> throwNotFoundException(ResourceNotFoundException e) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setCode(HttpStatus.NOT_FOUND.value());
        messageResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(messageResponse,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AttributeException.class)
    public ResponseEntity<MessageResponse> throwAttributeException(AttributeException e) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setCode(HttpStatus.NOT_FOUND.value());
        messageResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(messageResponse,
                HttpStatus.BAD_REQUEST);
    }

}
