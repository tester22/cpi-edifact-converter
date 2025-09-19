/*
    This is a sample Groovy script to convert EDIFACT to XML and vice versa in SAP CPI.
    It uses the EdifactConverterFactory from the cpitoedifact library.

    https://github.com/tester22/cpiedifact

*/
import com.sap.gateway.ip.core.customdev.util.Message
import com.cpitoedifact.converter.EdifactConverterFactory

def Message processData(Message message) {

    def converter = EdifactConverterFactory.getDefaultConverter()

    // Get the message payload
    def messageBody = message.getBody()
    def messageLog = messageLogFactory.getMessageLog(message)

    def MessageDirection = message.getHeaders().get('MessageDirection') ?: 'EDIFACT_TO_XML'
    def StepId = message.getHeaders().get('StepId') ?: 'Step 1'
    def LogPayload = message.getHeaders().get('LogPayload') ?: 'true'

    if (messageLog != null && LogPayload == 'true') {
        messageLog.addAttachmentAsString('Payload before: ' + StepId, messageBody, 'text/plain')
    }

    // Convert based on message direction
    if (MessageDirection == 'EDIFACT_TO_XML') {
        def xmlResult = converter.convertEdifactToXml(messageBody)
        message.setBody(xmlResult)
        if (messageLog != null && LogPayload == 'true') {
            messageLog.addAttachmentAsString('Payload after (XML): ' + StepId, xmlResult, 'text/xml')
        }
   } else if (MessageDirection == 'XML_TO_EDIFACT') {
        def edifactResult = converter.convertXmlToEdifact(messageBody)
        message.setBody(edifactResult)
        if (messageLog != null && LogPayload == 'true') {
            messageLog.addAttachmentAsString('Payload after (EDIFACT): ' + StepId, edifactResult, 'text/plain')
        }
    }

    return message

}
