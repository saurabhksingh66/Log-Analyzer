package com.loganalyzer.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class handles all the exceptions that may occur in application.
 * 
 * @author Saurabh
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public String handleExceptions() {
		return "exception";
	}
}
