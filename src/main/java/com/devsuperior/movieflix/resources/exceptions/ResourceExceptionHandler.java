package com.devsuperior.movieflix.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.movieflix.services.exceptions.UnauthorizedException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<OAuthCustomError> unauthorized(UnauthorizedException exception, HttpServletRequest request) {
		OAuthCustomError err = new OAuthCustomError("Unauthorized", exception.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException exception,
			HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource not found");
		err.setMessage(exception.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

}
