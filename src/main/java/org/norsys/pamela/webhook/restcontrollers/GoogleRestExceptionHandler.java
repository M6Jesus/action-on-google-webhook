package org.norsys.pamela.webhook.restcontrollers;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GoogleRestExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GoogleRestExceptionHandler.class);
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public String HandleNoSuchElementException(HttpServletRequest request, Exception ex) {
		logger.debug("l'Exception {} a été lever dans la requete {}", ex.getMessage(), request.getRequestURL());
		return ex.getMessage();
	}
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public String HandleIllegaleArgumentException(HttpServletRequest request, Exception ex) {
		logger.debug("l'Exception {} a été lever dans la requete {}", ex.getMessage(), request.getRequestURL());
		return ex.getMessage();
	}
	

}
