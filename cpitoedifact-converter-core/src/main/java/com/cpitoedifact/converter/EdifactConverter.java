package com.cpitoedifact.converter;

/**
 * Interface for EDIFACT conversion operations.
 * Provides methods to convert between EDIFACT and XML formats.
 */
public interface EdifactConverter {
    
    /**
     * Converts EDIFACT message to XML format.
     * 
     * @param edifactMessage The EDIFACT message as a string
     * @return The converted XML message as a string
     * @throws EdifactConversionException if conversion fails
     */
    String convertEdifactToXml(String edifactMessage) throws EdifactConversionException;
    
    /**
     * Converts XML message to EDIFACT format.
     * 
     * @param xmlMessage The XML message as a string
     * @return The converted EDIFACT message as a string
     * @throws EdifactConversionException if conversion fails
     */
    String convertXmlToEdifact(String xmlMessage) throws EdifactConversionException;
    
    /**
     * Converts XML message to EDIFACT format with automatic segment calculation.
     * 
     * @param xmlMessage The XML message as a string
     * @param autoCalculateSegments If true, automatically calculates UNT and UNZ segments
     * @return The converted EDIFACT message as a string
     * @throws EdifactConversionException if conversion fails
     */
    String convertXmlToEdifact(String xmlMessage, boolean autoCalculateSegments) throws EdifactConversionException;
    
    /**
     * Validates if the input string is a valid EDIFACT message.
     * 
     * @param edifactMessage The message to validate
     * @return true if valid EDIFACT, false otherwise
     */
    boolean isValidEdifact(String edifactMessage);
    
    /**
     * Validates if the input string is a valid XML message.
     * 
     * @param xmlMessage The message to validate
     * @return true if valid XML, false otherwise
     */
    boolean isValidXml(String xmlMessage);
}
