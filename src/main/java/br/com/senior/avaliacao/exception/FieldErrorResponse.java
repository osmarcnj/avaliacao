package br.com.senior.avaliacao.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FieldErrorResponse.
 */
public class FieldErrorResponse {
	
	private final String field;
    
	/** The rejected value. */
	@JsonProperty("rejected_value")
    private final String rejectedValue;
    
	/** The message. */
	private final String message;

    /**
     * Constructor with all fields
     *
     * @param field         the field
     * @param rejectedValue the rejected value
     * @param message       the message
     */
    public FieldErrorResponse(String field, String rejectedValue, String message) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    /**
     * gets the field
     *
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * gets the rejected value
     *
     * @return the rejected value
     */
    public String getRejectedValue() {
        return rejectedValue;
    }

    /**
     * gets the message
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
