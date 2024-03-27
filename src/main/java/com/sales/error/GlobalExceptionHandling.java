package com.sales.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandling  {
	@ExceptionHandler(BindException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleBindException(BindException ex, HttpServletRequest request, HttpServletResponse response) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Server Error", HttpStatus.BAD_REQUEST.value(), details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Server Error", HttpStatus.BAD_REQUEST.value(), details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(CustomException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Server Exception", HttpStatus.BAD_REQUEST.value(), details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}


}