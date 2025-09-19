package com.cpitoedifact.converter

import spock.lang.Specification
import spock.lang.Shared

/**
 * Spock specification for CPIEdifactConverter.
 */
class CPIEdifactConverterSpec extends Specification {
    
    @Shared
    CPIEdifactConverter converter
    
    def setup() {
        converter = new CPIEdifactConverter()
    }
    
    def "should create converter instance"() {
        expect:
        converter != null
    }
    
    def "should validate EDIFACT message correctly"() {
        when:
        def result = converter.isValidEdifact(edifactMessage)
        
        then:
        result == expected
        
        where:
        edifactMessage | expected
        "UNA:+.? '" | true
        "UNB+UNOC:3+1234567890123:14+1234567890123:14+200101:1234+1'" | true
        "UNH+1+ORDERS:D:96A:UN'" | true
        "" | false
        null | false
        "INVALID MESSAGE" | false
        "<?xml version='1.0'?><root></root>" | false
    }
    
    def "should validate XML message correctly"() {
        when:
        def result = converter.isValidXml(xmlMessage)
        
        then:
        result == expected
        
        where:
        xmlMessage | expected
        "<?xml version='1.0'?><root></root>" | true
        "<root><child>value</child></root>" | true
        "" | false
        null | false
        "INVALID XML" | false
        "UNA:+.? '" | false
    }
    
    def "should throw exception for null EDIFACT input"() {
        when:
        converter.convertEdifactToXml(null)
        
        then:
        thrown(EdifactConversionException)
    }
    
    def "should throw exception for empty EDIFACT input"() {
        when:
        converter.convertEdifactToXml("")
        
        then:
        thrown(EdifactConversionException)
    }
    
    def "should throw exception for null XML input"() {
        when:
        converter.convertXmlToEdifact(null)
        
        then:
        thrown(EdifactConversionException)
    }
    
    def "should throw exception for empty XML input"() {
        when:
        converter.convertXmlToEdifact("")
        
        then:
        thrown(EdifactConversionException)
    }
    
    def "should handle conversion errors gracefully"() {
        when:
        converter.convertEdifactToXml("INVALID EDIFACT")
        
        then:
        thrown(EdifactConversionException)
    }
    
    def "should handle XML conversion errors gracefully"() {
        when:
        converter.convertXmlToEdifact("INVALID XML")
        
        then:
        thrown(EdifactConversionException)
    }
    
}
