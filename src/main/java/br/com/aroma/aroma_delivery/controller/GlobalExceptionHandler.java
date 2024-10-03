package br.com.aroma.aroma_delivery.controller;

import br.com.aroma.aroma_delivery.exceptions.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.Path.Node;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * GlobalExceptionHandler is a controller advice class that handles exceptions globally and provides
 * a consistent way to handle and respond to those exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles ConstraintViolationException and returns a ResponseEntity object with the appropriate
   * HTTP status code and error details.
   *
   * @param ex The ConstraintViolationException to be handled.
   * @return A ResponseEntity object containing a map of errors and the HTTP status code.
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();

    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      String propertyPath = getViolationPropertyName(violation);
      errors.put(propertyPath, violation.getMessage());
    }
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  /**
   * Retrieves the property name from the {@link ConstraintViolation}. If the violation pertains to
   * method parameters, it skips over the method name and retrieves the actual parameter name.
   *
   * @param violation the {@link ConstraintViolation} from which to extract the property name
   * @return the property name as a {@link String}
   */
  private String getViolationPropertyName(ConstraintViolation<?> violation) {
    Path propertyPath = violation.getPropertyPath();

    if (isMethodParameterViolation(violation)) {
      Iterator<Node> iterator = propertyPath.iterator();
      iterator.next(); // Skip the method name

      if (iterator.hasNext()) {
        return iterator.next().getName();
      }
    }
    return propertyPath.toString();
  }

  /**
   * Determines whether the {@link ConstraintViolation} pertains to a method parameter by checking
   * if the violation contains executable parameters.
   *
   * @param violation the {@link ConstraintViolation} to check
   * @return {@code true} if the violation is related to a method parameter, {@code false} otherwise
   */
  private boolean isMethodParameterViolation(ConstraintViolation<?> violation) {
    return violation.getExecutableParameters() != null
        && violation.getExecutableParameters().length > 0;
  }

  /**
   * Handles NotFoundException and returns a ResponseEntity object with the appropriate HTTP status
   * code and error details.
   *
   * @param ex The NotFoundException to be handled.
   * @return A ResponseEntity object containing a map of errors and the HTTP status code.
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("message", ex.getMessage());
    return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
      String fieldName = fieldError.getField();
      String errorMessage = fieldError.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}