package br.com.senior.avaliacao.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController("apiErrorHandler")
@ControllerAdvice(annotations = RestController.class)
public class ApiErrorHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiErrorHandler.class);
	
	private static final String CONTENT_TYPE = "application/json; charset=utf-8";
	
	private static final String MALFORMED_PAYLOAD = "Malformed Payload";
	
	private HttpHeaders headersResponse;
	
	public ApiErrorHandler() {
		headersResponse = new HttpHeaders();
		headersResponse.setContentType(MediaType.valueOf(CONTENT_TYPE));
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(ObjectNotFoundException ex) {
		LOGGER.warn(ex.getMessage());
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AvaliacaoException.class)
	public ResponseEntity<ErrorResponse> handleAvaliacaoException(AvaliacaoException ex) {
		LOGGER.warn(ex.getMessage());
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConfigurationException.class)
	public ResponseEntity<ErrorResponse> handleConfigurationException(ConfigurationException ex) {
		LOGGER.warn(ex.getMessage());
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnexpectedTypeException.class)
	public ResponseEntity<ErrorResponse> handleUnexpectedTypeException(UnexpectedTypeException ex) {
		LOGGER.warn(ex.getMessage());
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<ErrorResponse> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
		LOGGER.warn(ex.getMessage());
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BeanCreationException.class)
	public ResponseEntity<ErrorResponse> handleBeanCreationException(BeanCreationException ex) {
		LOGGER.warn(ex.getMessage());
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	/**
     * Handle {@link ConstraintViolationException}
     *
     * @param request the request
     * @param ex      the exception
     * @return BadRequest
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorResponse>> handleConstraintViolationException(final HttpServletRequest request, final ConstraintViolationException ex) {
    	LOGGER.warn(ex.getMessage());
        ArrayList<ConstraintViolation<?>> constraintViolations = new ArrayList<>(ex.getConstraintViolations());
        List<ErrorResponse> errors = constraintViolations
            .stream()
            .map(c -> new ErrorResponse(c.getMessage()))
            .collect(Collectors.toList());

        return new ResponseEntity<>(errors, headersResponse, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * This method create a bad request with response message
     * @param ex HttpMessageNotReadableException
     * @param headers Request headers
     * @param status Request status
     * @param request request
     * @return Response with bad request and error message
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	LOGGER.warn(ex.getMessage());
        final Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            return new ResponseEntity<>(buildErrorResponse((InvalidFormatException) cause), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ErrorResponse(MALFORMED_PAYLOAD), headersResponse, status);

    }
    
    /**
     * Handle method argument not valid.
     *
     * @param ex the ex
     * @param headers the headers
     * @param status the status
     * @param request the request
     * @return the response entity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
    	LOGGER.warn(ex.getMessage());
    	List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    	List<FieldErrorResponse> fieldErrorResponses = buildErrorResponse(fieldErrors);
    	
        return new ResponseEntity<>(fieldErrorResponses, headersResponse, HttpStatus.BAD_REQUEST);
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
    	LOGGER.warn(ex.getMessage());
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
    
    /**
     * This Method build a Response based on fieldErrors
     * @param fieldErrors fieldErrors
     * @return list of errors
     */
    private List<FieldErrorResponse> buildErrorResponse(List<FieldError> fieldErrors) {
        return fieldErrors.stream().map(fieldError -> {
            String rejectedValue = fieldError.getRejectedValue() == null ? null : fieldError.getRejectedValue().toString();
            return new FieldErrorResponse(fieldError.getField(), rejectedValue, fieldError.getDefaultMessage());
        }).collect(Collectors.toList());
    }
    
    /**
     * This Method build a Response based on InvalidFormatException
     * @param exception InvalidFormatException
     * @return Field error
     */
    private FieldErrorResponse buildErrorResponse(InvalidFormatException exception) {
        String fieldPath = exception.getPath().stream().filter(ref -> StringUtils.isNotEmpty(ref.getFieldName()))
            .map(JsonMappingException.Reference::getFieldName).collect(Collectors.joining("."));

        return new FieldErrorResponse(fieldPath, exception.getValue().toString(), "Invalid format on field");
    }
    
}
