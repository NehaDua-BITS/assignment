package com.intuit.profilevalidationsystem.exceptions.handler;

import com.intuit.profilevalidationsystem.exceptions.types.InvalidInputException;
import com.intuit.profilevalidationsystem.exceptions.types.SubmitRequestException;
import com.intuit.profilevalidationsystem.exceptions.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler({SubmitRequestException.class, InvalidInputException.class})
    @ResponseBody
    public ResponseEntity<Object> processSubmitException(SubmitRequestException ex) {
        trackException(ex);
        return new ResponseEntity(new ErrorResponse(ex.getCode(), ex.getMessage()), HttpStatus.valueOf(ex.getHttpStatus()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity processException(Exception ex) {
        trackException(ex);
        return new ResponseEntity(new ErrorResponse("500",
                String.format("Exception Type : %s ; Message : %s", ex.getClass().getCanonicalName(), ex.getMessage())),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void trackException(Exception ex) {
        log.error("Tracking Exception : ", ex);
    }

}
