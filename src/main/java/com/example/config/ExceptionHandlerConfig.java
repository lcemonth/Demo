package com.example.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.service.CurrencyServiceImpl;

@ControllerAdvice
public class ExceptionHandlerConfig {
	
	
	static final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handle(Exception exception){
		
		log.error("Exception: " + exception.getLocalizedMessage());
		
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("ResponseCode", "400");
		resMap.put("ResponseMessage", "Exception: " + exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(resMap);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> handle(RuntimeException exception){
		

		log.error("RuntimeException: " + exception.getLocalizedMessage());
		
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("ResponseCode", "503");
		resMap.put("ResponseMessage", "RuntimeException: " + exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(resMap);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handle(IllegalArgumentException exception){
		

		log.error("IllegalArgumentException: " + exception.getLocalizedMessage());
		
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("ResponseCode", "400");
		resMap.put("ResponseMessage", "IllegalArgumentException: " + exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(resMap);
	}
}
