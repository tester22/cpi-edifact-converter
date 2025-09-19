package com.cpitoedifact.converter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SapsternConversionTest {

    @Test
    public void testEdifactToXmlConversion() throws Exception {
        CPIEdifactConverter converter = new CPIEdifactConverter();

        String edifactInput = "UNB+UNOC:3+SENDER ID:ZZZ:A1B2C3D4E5+RECEIVER ID:ZZZ:A1B2C3D4E5+20151128:1037+1+++1++1'UNH+1+ORDERS:D:01B:UN:EAN010'BGM+220+PO357893+9'DTM+2:20150813:102'DTM+137:201511281130:203'DTM+235:201509071025:203'FTX+DEL+1++INCLUDE TIME IN DATE OF DELIVERY'RFF+AAN:APPTNO123445'NAD+BY+BUYEREANID12345::9'LOC+7+BUYERPLACE::92'CTA+PD+A1B2C3D4E5:A1B2C3D4E5'COM+EMAIL@DOMAIN:EM'COM+9055557766:FX'NAD+ST+SHIPTOEANID3457::9'LOC+7+SHIPTOLOCATION::92'CTA+PD+A1B2C3D4E5:A1B2C3D4E5'COM+8185556789:FX'COM+2135559898:TE'NAD+SU+SUPPLIEREANID9876::9'LOC+7+SUPPLIERLOCATION::92'CTA+PD+A1B2C3D4E5:A1B2C3D4E5'COM+SALES@DOMAIN:EM'COM+3105553344:TE'LIN+1+1+GTIN1233456:SRV'PIA+5+VDKF9T:IN+ERG34T2233:IN'IMD+F+++PRODUCT 1'MEA+PD+ULY+NAR:24'QTY+21:45:KGM'DTM+364:20151217:102'PRI+1E:190.45'LIN+2+1+GTIN8734652:SRV'PIA+5+LKKSDFK56324:SA+K3IF5N58FN4:SA'IMD+F+++THIS IS PRODUCT2'MEA+PD+LAY+NAR:6'QTY+59:3:KGM'DTM+364:20090716:102'PRI+1E:123456.50'UNS+S'MOA+86:37535.34'CNT+2:2'UNT+40+1'UNZ+1+1'";

        System.out.println("Testing EDIFACT to XML conversion...");
        String xmlResult = converter.convertEdifactToXml(edifactInput);
        System.out.println("XML Result:");
        System.out.println(xmlResult);

        assertNotNull(xmlResult);
        assertFalse(xmlResult.isEmpty());
        assertTrue(xmlResult.contains("<EDIFACTINTERCHANGE>"), "Should contain EDIFACTINTERCHANGE root element");
        assertTrue(xmlResult.contains("<S_UNB>"), "Should contain UNB segment");
        assertTrue(xmlResult.contains("<S_UNH>"), "Should contain UNH segment");
        assertTrue(xmlResult.contains("<S_BGM>"), "Should contain BGM segment");
    }

    @Test
    public void testXmlToEdifactConversion() throws Exception {
        CPIEdifactConverter converter = new CPIEdifactConverter();

        String xmlInput = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<INTERCHANGE>\n" +
                "<S_UNB>\n" +
                "<C_S001>\n" +
                "<D_0001>UNOC</D_0001>\n" +
                "<D_0002>3</D_0002>\n" +
                "</C_S001>\n" +
                "<C_S002>\n" +
                "<D_0004>SENDER ID</D_0004>\n" +
                "<D_0007>ZZZ</D_0007>\n" +
                "<D_0008>A1B2C3D4E5</D_0008>\n" +
                "</C_S002>\n" +
                "<C_S003>\n" +
                "<D_0010>RECEIVER ID</D_0010>\n" +
                "<D_0007>ZZZ</D_0007>\n" +
                "<D_0014>A1B2C3D4E5</D_0014>\n" +
                "</C_S003>\n" +
                "<C_S004>\n" +
                "<D_0017>20151128</D_0017>\n" +
                "<D_0019>1037</D_0019>\n" +
                "</C_S004>\n" +
                "<D_0020>1</D_0020>\n" +
                "<C_S005/>\n" +
                "<D_0026/>\n" +
                "<D_0029/>\n" +
                "<D_0031>1</D_0031>\n" +
                "<D_0032/>\n" +
                "<D_0035>1</D_0035>\n" +
                "</S_UNB>\n" +
                "<M_ORDERS>\n" +
                "<S_UNH>\n" +
                "<D_0062>1</D_0062>\n" +
                "<C_S009>\n" +
                "<D_0065>ORDERS</D_0065>\n" +
                "<D_0052>D</D_0052>\n" +
                "<D_0054>01B</D_0054>\n" +
                "<D_0051>UN</D_0051>\n" +
                "<D_0057>EAN010</D_0057>\n" +
                "</C_S009>\n" +
                "</S_UNH>\n" +
                "<S_BGM>\n" +
                "<C_C002>\n" +
                "<D_1001>220</D_1001>\n" +
                "</C_C002>\n" +
                "<C_C106>\n" +
                "<D_1004>PO357893</D_1004>\n" +
                "</C_C106>\n" +
                "<D_1225>9</D_1225>\n" +
                "</S_BGM>\n" +
                "<S_DTM>\n" +
                "<C_C507>\n" +
                "<D_2005>2</D_2005>\n" +
                "<D_2380>20150813</D_2380>\n" +
                "<D_2379>102</D_2379>\n" +
                "</C_C507>\n" +
                "</S_DTM>\n" +
                "<S_UNT>\n" +
                "<D_0074>40</D_0074>\n" +
                "<D_0062>1</D_0062>\n" +
                "</S_UNT>\n" +
                "</M_ORDERS>\n" +
                "<S_UNZ>\n" +
                "<D_0036>1</D_0036>\n" +
                "<D_0020>1</D_0020>\n" +
                "</S_UNZ>\n" +
                "</INTERCHANGE>";

        System.out.println("Testing XML to EDIFACT conversion...");
        String edifactResult = converter.convertXmlToEdifact(xmlInput);
        System.out.println("EDIFACT Result:");
        System.out.println(edifactResult);

        assertNotNull(edifactResult);
        assertFalse(edifactResult.isEmpty());
        assertTrue(edifactResult.contains("UNB+UNOC:3+SENDER ID:ZZZ:A1B2C3D4E5+RECEIVER ID:ZZZ:A1B2C3D4E5+20151128:1037+1++++1++1"), "Should contain UNB segment");
        assertTrue(edifactResult.contains("UNH+1+ORDERS:D:01B:UN:EAN010"), "Should contain UNH segment");
        assertTrue(edifactResult.contains("BGM+220+PO357893+9"), "Should contain BGM segment");
        assertTrue(edifactResult.contains("DTM+2:20150813:102"), "Should contain DTM segment");
    }

    @Test
    public void testValidation() throws Exception {
        CPIEdifactConverter converter = new CPIEdifactConverter();

        // Test EDIFACT validation
        assertTrue(converter.isValidEdifact("UNB+UNOC:3+SENDER+RECEIVER+20151128:1037+1+++1++1'"), "Should be valid EDIFACT");
        assertFalse(converter.isValidEdifact("INVALID MESSAGE"), "Should be invalid EDIFACT");
        assertFalse(converter.isValidEdifact(""), "Empty string should be invalid EDIFACT");
        assertFalse(converter.isValidEdifact(null), "Null should be invalid EDIFACT");

        // Test XML validation
        assertTrue(converter.isValidXml("<?xml version=\"1.0\"?><root>test</root>"), "Should be valid XML");
        assertFalse(converter.isValidXml("INVALID XML"), "Should be invalid XML");
        assertFalse(converter.isValidXml(""), "Empty string should be invalid XML");
        assertFalse(converter.isValidXml(null), "Null should be invalid XML");
    }
}
