package com.cpitoedifact.converter;

import com.sapstern.openedifact.sax.EdifactSaxParserToXML;
import com.sapstern.openedifact.sax.EdifactSaxParserToFlat;
import com.sapstern.openedifact.sax.EdifactSaxParserToXMLIF;
import com.sapstern.openedifact.sax.EdifactSaxParserToFlatIF;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.IOException;

/**
 * Implementation of EdifactConverter using the sapstern/edifactconverter library.
 * This class provides EDIFACT to XML and XML to EDIFACT conversion capabilities.
 * 
 * The sapstern library provides bidirectional conversion:
 * - EDIFACT to XML using EdifactSaxParserToXML
 * - XML to EDIFACT using EdifactSaxParserToFlat
 */
public class CPIEdifactConverter implements EdifactConverter {
    
    
    public CPIEdifactConverter() {
        System.out.println("CPI EDIFACT converter initialized successfully");
    }
    
    @Override
    public String convertEdifactToXml(String edifactMessage) throws EdifactConversionException {
        if (edifactMessage == null || edifactMessage.trim().isEmpty()) {
            throw new EdifactConversionException("EDIFACT message cannot be null or empty");
        }
        
        try {
            System.out.println("Converting EDIFACT to XML: " + edifactMessage.substring(0, Math.min(100, edifactMessage.length())));
            
            // Use sapstern library to convert EDIFACT to XML
            EdifactSaxParserToXMLIF xmlParser = EdifactSaxParserToXML.factory("UTF-8");
            
            // Convert EDIFACT to XML
            String xmlResult = xmlParser.parseEdifact(edifactMessage);
            
            if (xmlResult == null || xmlResult.trim().isEmpty()) {
                throw new EdifactConversionException("Conversion resulted in empty XML");
            }
            
            System.out.println("Successfully converted EDIFACT to XML");
            return xmlResult;
            
        } catch (Exception e) {
            System.err.println("Failed to convert EDIFACT to XML: " + e.getMessage());
            throw new EdifactConversionException("Failed to convert EDIFACT to XML: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String convertXmlToEdifact(String xmlMessage) throws EdifactConversionException {
        return convertXmlToEdifact(xmlMessage, false);
    }
    
    @Override
    public String convertXmlToEdifact(String xmlMessage, boolean autoCalculateSegments) throws EdifactConversionException {
        if (xmlMessage == null || xmlMessage.trim().isEmpty()) {
            throw new EdifactConversionException("XML message cannot be null or empty");
        }
        
        try {
            System.out.println("Converting XML to EDIFACT: " + xmlMessage.substring(0, Math.min(100, xmlMessage.length())));
            if (autoCalculateSegments) {
                System.out.println("Auto-calculating UNT and UNZ segments enabled");
            }
            
            // Use sapstern library to convert XML to EDIFACT
            EdifactSaxParserToFlatIF flatParser = EdifactSaxParserToFlat.factory(false);
            
            // Convert XML to EDIFACT
            String edifactResult = flatParser.parse(xmlMessage);
            
            if (edifactResult == null || edifactResult.trim().isEmpty()) {
                throw new EdifactConversionException("Conversion resulted in empty EDIFACT");
            }
            
            // Clean up the EDIFACT output to ensure proper single-line format
            String cleanedResult = cleanEdifactOutput(edifactResult);
            
            // Auto-calculate UNT and UNZ segments if requested
            if (autoCalculateSegments) {
                cleanedResult = autoCalculateSegments(cleanedResult);
            }
            
            System.out.println("Successfully converted XML to EDIFACT");
            return cleanedResult;
            
        } catch (Exception e) {
            System.err.println("Failed to convert XML to EDIFACT: " + e.getMessage());
            throw new EdifactConversionException("Failed to convert XML to EDIFACT: " + e.getMessage(), e);
        }
    }
    
    /**
     * Automatically calculates UNT and UNZ segments for EDIFACT messages.
     * UNT segment contains the segment count and message reference number.
     * UNZ segment contains the interchange control count and control reference number.
     * 
     * @param edifactMessage The EDIFACT message
     * @return The EDIFACT message with calculated UNT and UNZ segments
     */
    private String autoCalculateSegments(String edifactMessage) {
        if (edifactMessage == null || edifactMessage.trim().isEmpty()) {
            return edifactMessage;
        }
        
        try {
            // Split the message into segments
            String[] segments = edifactMessage.split("'");
            int segmentCount = 0;
            String messageReference = "";
            String interchangeReference = "";
            
            // Count segments and find references
            for (String segment : segments) {
                if (segment.trim().isEmpty()) continue;
                
                segmentCount++;
                
                // Find UNH segment to get message reference
                if (segment.startsWith("UNH")) {
                    String[] fields = segment.split("\\+");
                    if (fields.length > 1) {
                        messageReference = fields[1];
                    }
                }
                
                // Find UNB segment to get interchange reference
                if (segment.startsWith("UNB")) {
                    String[] fields = segment.split("\\+");
                    if (fields.length > 4) {
                        interchangeReference = fields[4];
                    }
                }
            }
            
            // Build the updated message
            StringBuilder result = new StringBuilder();
            boolean untAdded = false;
            boolean unzAdded = false;
            
            for (int i = 0; i < segments.length; i++) {
                String segment = segments[i];
                if (segment.trim().isEmpty()) continue;
                
                result.append(segment).append("'");
                
                // Add UNT segment after the last message segment (before UNZ)
                if (!untAdded && segment.startsWith("UNH")) {
                    // Find the end of the message (before UNZ)
                    boolean foundEndOfMessage = false;
                    for (int j = i + 1; j < segments.length; j++) {
                        String nextSegment = segments[j];
                        if (nextSegment.trim().isEmpty()) continue;
                        
                        if (nextSegment.startsWith("UNZ")) {
                            // Insert UNT before UNZ
                            String untSegment = "UNT" + (segmentCount - 2) + "+" + messageReference; // -2 to exclude UNT and UNZ
                            result.append(untSegment).append("'");
                            untAdded = true;
                            foundEndOfMessage = true;
                            break;
                        }
                    }
                    
                    if (!foundEndOfMessage) {
                        // No UNZ found, add UNT at the end
                        String untSegment = "UNT" + (segmentCount - 1) + "+" + messageReference; // -1 to exclude UNT
                        result.append(untSegment).append("'");
                        untAdded = true;
                    }
                }
                
                // Add UNZ segment at the very end
                if (!unzAdded && segment.startsWith("UNB")) {
                    // Find the end and add UNZ
                    String unzSegment = "UNZ" + "1" + "+" + interchangeReference; // 1 message in this interchange
                    result.append(unzSegment).append("'");
                    unzAdded = true;
                }
            }
            
            // If we didn't find UNB, add UNZ at the end
            if (!unzAdded) {
                String unzSegment = "UNZ" + "1" + "+" + (interchangeReference.isEmpty() ? "00000001" : interchangeReference);
                result.append(unzSegment).append("'");
            }
            
            System.out.println("Auto-calculated segments - UNT: " + (untAdded ? "added" : "not found") + 
                             ", UNZ: " + (unzAdded ? "added" : "not found") + 
                             ", Total segments: " + segmentCount);
            
            return result.toString();
            
        } catch (Exception e) {
            System.err.println("Error auto-calculating segments: " + e.getMessage());
            return edifactMessage; // Return original message if calculation fails
        }
    }
    
    /**
     * Cleans up the EDIFACT output to ensure proper single-line format.
     * Removes unnecessary whitespace, line breaks, and ensures proper segment termination.
     */
    private String cleanEdifactOutput(String edifactOutput) {
        if (edifactOutput == null) {
            return "";
        }
        
        // Remove all line breaks and extra whitespace, but preserve the structure
        String cleaned = edifactOutput.replaceAll("\\r\\n|\\r|\\n", "");
        
        // Replace multiple spaces with single space
        cleaned = cleaned.replaceAll("\\s+", " ");
        
        // Trim leading/trailing whitespace
        cleaned = cleaned.trim();
        
        // Special handling for UNA segment - fix the format
        // The sapstern library might produce "UNA: + . ? ' '" which should be "UNA:+.? '"
        if (cleaned.startsWith("UNA:")) {
            // Fix UNA segment format - remove spaces between delimiters
            // Pattern: UNA: + . ? ' ' -> UNA:+.? '
            cleaned = cleaned.replaceFirst("UNA: + \\. \\? ' '", "UNA:+.? '");
            // Also handle case without the extra space: UNA: + . ? ' -> UNA:+.? '
            cleaned = cleaned.replaceFirst("UNA: + \\. \\? '", "UNA:+.? '");
        }
        
        // The sapstern library might produce formatted output with spaces between segments
        // We need to convert this to proper EDIFACT format where segments are separated by '
        // Look for patterns where a segment name (3 uppercase letters) is followed by space and then data
        // and replace the space with the segment terminator '
        cleaned = cleaned.replaceAll("\\s+([A-Z]{3})\\s*", "'$1");
        
        // Handle the UNA segment specially - it should be followed by ' not +
        if (cleaned.startsWith("UNA ")) {
            cleaned = cleaned.replaceFirst("UNA ", "UNA");
        }
        
        // If the result doesn't start with UNA, add the UNA segment
        // The UNA segment defines the service string advice (delimiters)
        if (!cleaned.startsWith("UNA")) {
            // Add UNA segment with standard delimiters: :+.? '
            cleaned = "UNA:+.? '" + cleaned;
        }
        
        // Ensure the message ends with proper segment terminator
        if (!cleaned.endsWith("'") && !cleaned.endsWith("\n")) {
            cleaned += "'";
        }
        
        return cleaned;
    }
    
    @Override
    public boolean isValidEdifact(String edifactMessage) {
        if (edifactMessage == null || edifactMessage.trim().isEmpty()) {
            return false;
        }
        
        try {
            // Basic EDIFACT validation - check for common EDIFACT segments
            String trimmed = edifactMessage.trim();
            return trimmed.startsWith("UNA") || trimmed.startsWith("UNB") || trimmed.startsWith("UNH");
        } catch (Exception e) {
            System.err.println("EDIFACT validation failed: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean isValidXml(String xmlMessage) {
        if (xmlMessage == null || xmlMessage.trim().isEmpty()) {
            return false;
        }
        
        try {
            // Basic XML validation - check for XML declaration and root element
            String trimmed = xmlMessage.trim();
            return trimmed.startsWith("<?xml") && trimmed.contains("<") && trimmed.contains(">");
        } catch (Exception e) {
            System.err.println("XML validation failed: " + e.getMessage());
            return false;
        }
    }
}