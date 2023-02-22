package com.excelapplication.globalexecption;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.excelapplication.exception.FilesException;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(FilesException.class)
	public ResponseEntity<String> fileExecptionHandller(FilesException e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> ExecptionHandller(Exception e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
}
