package com.cpitoedifact.converter;

/**
 * Exception thrown when EDIFACT conversion operations fail.
 */
public class EdifactConversionException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public EdifactConversionException(String message) {
        super(message);
    }
    
    public EdifactConversionException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public EdifactConversionException(Throwable cause) {
        super(cause);
    }
}
