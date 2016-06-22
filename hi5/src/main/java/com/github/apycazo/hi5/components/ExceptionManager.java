package com.github.apycazo.hi5.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by manager on 22/08/15.
 */
@Slf4j
@ControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> processException (Exception ex) {

        log.error("Captured exception {} {}", ex.getClass().getSimpleName(), ex.getMessage());

        return new ResponseEntity<String>("Captured exception " + ex.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
