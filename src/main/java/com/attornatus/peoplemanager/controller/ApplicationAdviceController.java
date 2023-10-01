package com.attornatus.peoplemanager.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.attornatus.peoplemanager.dto.ErrorResponseDTO;
import com.attornatus.peoplemanager.exception.WarningException;

@RestControllerAdvice
public class ApplicationAdviceController {

	@ExceptionHandler(WarningException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponseDTO handlingWarningException(Exception ex) {
		String erroStr = ex.getMessage();
		
		return new ErrorResponseDTO(erroStr);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponseDTO handlingMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<String> erroStrList = ex.getBindingResult()
				.getAllErrors()
				.stream()
				.map(erroStr -> erroStr.getDefaultMessage())
				.collect(Collectors.toList());
		
		return new ErrorResponseDTO(erroStrList);
	}
	
}
