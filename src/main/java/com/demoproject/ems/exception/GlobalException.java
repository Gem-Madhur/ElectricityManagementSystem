package com.demoproject.ems.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

@Slf4j
@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    /**
     * to handle Exceptions when Data is not found
     * @param ex exception
     * @return Response Entity
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorDetails> globalDataNotFoundException (final DataNotFoundException ex){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Data not found",ex.getMessage());
        log.error("Data not found");
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }

    /**
     * to handle Exception when ID is not found in Database
     * @param ex exception
     * @return Response Entity
     */
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorDetails> globalIdNotFoundExceptionHandler(final IdNotFoundException ex){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Invalid ID",ex.getMessage());
        log.error("Invalid id",ex);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * to handle Exception when there are Invalid Reading
     * @param ex exception
     * @return Response Entity
     */
    @ExceptionHandler(InvalidReadingException.class)
    public ResponseEntity<ErrorDetails> globalInvalidReadingExceptionHandler(final InvalidReadingException ex){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Invalid Reading",ex.getMessage());
        log.error("Invalid Reading");
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * to handle Exception when Resources are missing
     * @param ex exception
     * @return Response Entity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> globalResourceNotFoundExceptionHandler(final ResourceNotFoundException ex){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Resource not found", ex.getMessage());
        log.error("Resource not found",ex);
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }
}
