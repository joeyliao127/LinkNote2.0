package com.joeyliao.linknoteresource.generic.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {


  @org.springframework.web.bind.annotation.ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<Object> sqlInegerityConstraintViolationHanlder(SQLIntegrityConstraintViolationException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("result", false, "msg", "重複的資料"));
  }
}
