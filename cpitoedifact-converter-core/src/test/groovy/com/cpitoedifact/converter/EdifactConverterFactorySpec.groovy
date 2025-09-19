package com.cpitoedifact.converter

import spock.lang.Specification

/**
 * Spock specification for EdifactConverterFactory.
 */
class EdifactConverterFactorySpec extends Specification {
    
    def "should create converter instance"() {
        when:
        def converter = EdifactConverterFactory.createConverter()
        
        then:
        converter != null
        converter instanceof CPIEdifactConverter
    }
    
    def "should return same default converter instance"() {
        when:
        def converter1 = EdifactConverterFactory.getDefaultConverter()
        def converter2 = EdifactConverterFactory.getDefaultConverter()
        
        then:
        converter1 == converter2
        converter1 != null
    }
    
    def "should create new converter after reset"() {
        given:
        def originalConverter = EdifactConverterFactory.getDefaultConverter()
        
        when:
        EdifactConverterFactory.resetDefaultConverter()
        def newConverter = EdifactConverterFactory.getDefaultConverter()
        
        then:
        newConverter != originalConverter
        newConverter != null
    }
    
    def "should create converter with config path"() {
        when:
        def converter = EdifactConverterFactory.createConverter("test-config.xml")
        
        then:
        converter != null
        converter instanceof CPIEdifactConverter
    }
    
    def "should handle multiple threads accessing default converter"() {
        when:
        def results = []
        def threads = []
        
        10.times {
            threads << Thread.start {
                results << EdifactConverterFactory.getDefaultConverter()
            }
        }
        
        threads.each { it.join() }
        
        then:
        results.size() == 10
        results.every { it != null }
        results.unique().size() == 1 // All should be the same instance
    }
}
