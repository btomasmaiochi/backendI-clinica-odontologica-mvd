package com.dh.clinicaOdontologica.exceptions;


import com.dh.clinicaOdontologica.controller.TurnoController;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {
    private static final Logger logger = Logger.getLogger(TurnoController.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> tratamientoErrorResourceNotFound(ResourceNotFoundException rnfe){
        logger.error(rnfe.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ATENCIÓN: ERROR " + rnfe.getMessage() /*+ "\n" + rnfe.getStackTrace()*/);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> tratamientoBadRequest(BadRequestException bre){
       logger.error(bre.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ATENCIÓN: ERROR " + bre.getMessage());
    }

}

