package com.cpitoedifact.converter

import spock.lang.Specification

/**
 * Test to verify EDIFACT conversion works with sample data.
 */
class EdifactConversionTest extends Specification {
    
    def "should convert sample EDIFACT to XML correctly"() {
        given:
        def converter = new CPIEdifactConverter()
        def sampleEdifact = """UNA:+.? '
UNB+UNOC:3+1234567890123:14+9876543210987:14+20240101:1234+1'
UNH+1+ORDERS:D:96A:UN'
BGM+220+12345'
DTM+137:20240101:102'
NAD+BY+1234567890123++ACME Corporation'
NAD+SU+9876543210987++Supplier Inc'
LIN+1+IN+ITEM001'
QTY+21:100'
PRICE+AAA:25.50'
UNS+S'
UNT+15+1'"""
        
        when:
        def xmlResult = converter.convertEdifactToXml(sampleEdifact)
        
        then:
        xmlResult != null
        xmlResult.contains("<EDIFACT_MESSAGE>")
        xmlResult.contains("<UNA>:+.? </UNA>")
        xmlResult.contains("<UNB>")
        xmlResult.contains("<UNH>")
        xmlResult.contains("<BGM>")
        xmlResult.contains("<DTM>")
        xmlResult.contains("<NAD>")
        xmlResult.contains("<LIN>")
        xmlResult.contains("<QTY>")
        xmlResult.contains("<PRICE>")
        xmlResult.contains("<UNS>")
        xmlResult.contains("<UNT>")
        
        // Verify specific data elements
        xmlResult.contains("UNB_1>UNOC:3</UNB_1")
        xmlResult.contains("UNB_2>1234567890123:14</UNB_2")
        xmlResult.contains("UNH_1>1</UNH_1")
        xmlResult.contains("UNH_2>ORDERS:D:96A:UN</UNH_2")
    }
    
    def "should convert sample XML to EDIFACT correctly"() {
        given:
        def converter = new CPIEdifactConverter()
        def sampleXml = """<?xml version="1.0" encoding="UTF-8"?>
<EDIFACT_MESSAGE>
  <UNA>:+.? </UNA>
  <UNB>
    <UNB_1>UNOC:3</UNB_1>
    <UNB_2>1234567890123:14</UNB_2>
    <UNB_3>9876543210987:14</UNB_3>
    <UNB_4>20240101:1234</UNB_4>
    <UNB_5>1</UNB_5>
  </UNB>
  <UNH>
    <UNH_1>1</UNH_1>
    <UNH_2>ORDERS:D:96A:UN</UNH_2>
  </UNH>
  <BGM>
    <BGM_1>220</BGM_1>
    <BGM_2>12345</BGM_2>
  </BGM>
</EDIFACT_MESSAGE>"""
        
        when:
        def edifactResult = converter.convertXmlToEdifact(sampleXml)
        
        then:
        edifactResult != null
        edifactResult.contains("UNA:+.? ")
        edifactResult.contains("UNB+UNOC:3+1234567890123:14+9876543210987:14+20240101:1234+1'")
        edifactResult.contains("UNH+1+ORDERS:D:96A:UN'")
        edifactResult.contains("BGM+220+12345'")
    }
    
    def "should handle round-trip conversion"() {
        given:
        def converter = new CPIEdifactConverter()
        def originalEdifact = """UNA:+.? '
UNB+UNOC:3+1234567890123:14+9876543210987:14+20240101:1234+1'
UNH+1+ORDERS:D:96A:UN'
BGM+220+12345'"""
        
        when:
        def xmlResult = converter.convertEdifactToXml(originalEdifact)
        def edifactResult = converter.convertXmlToEdifact(xmlResult)
        
        then:
        xmlResult != null
        edifactResult != null
        edifactResult.contains("UNA:+.? ")
        edifactResult.contains("UNB+UNOC:3")
        edifactResult.contains("UNH+1+ORDERS:D:96A:UN")
        edifactResult.contains("BGM+220+12345")
    }
}
