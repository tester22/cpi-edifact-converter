# CPI to EDIFACT Converter

A comprehensive converter library for SAP Cloud Platform Integration (CPI) that provides seamless conversion between EDIFACT and XML formats using the [sapstern/edifactconverter](https://github.com/sapstern/edifactconverter) library.

## Features

- **EDIFACT to XML Conversion**: Convert EDIFACT messages to XML format
- **XML to EDIFACT Conversion**: Convert XML messages to EDIFACT format
- **Message Validation**: Built-in validation for both EDIFACT and XML messages
- **CPI Integration**: Designed specifically for SAP Cloud Platform Integration
- **Comprehensive Testing**: Full test coverage using Spock framework

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven 3.6 or higher
- SAP Cloud Platform Integration tenant (for deployment)

### CPI Integration

1. **Download JAR file**:
   - Download the JAR files from github.
   - Open the xsd.jar file in 7 Zip and add all the xsd files that you need for your project

2. **Deploy to CPI**:
   - Upload the both JAR file to your CPI integration flow as an Archive
   - Add a groovy script step in your iflow.

3. **Groovy Script Example**:
   ```groovy
   import com.cpitoedifact.converter.EdifactConverterFactory

   def converter = EdifactConverterFactory.getDefaultConverter()
   
   // Get the message payload
   def messageBody = message.getBody(String.class)
   
   // Convert based on message direction
   if (message.getHeaders().get("MessageDirection") == "EDIFACT_TO_XML") {
       def xmlResult = converter.convertEdifactToXml(messageBody)
       message.setBody(xmlResult)
   } else if (message.getHeaders().get("MessageDirection") == "XML_TO_EDIFACT") {
       def edifactResult = converter.convertXmlToEdifact(messageBody)
       message.setBody(edifactResult)
   }
   ```

## Dependencies

- **edifactconverter**: Core EDIFACT conversion library

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [sapstern/edifactconverter](https://github.com/sapstern/edifactconverter) - Core EDIFACT conversion functionality
- [engswee/equalize-cpi-converter](https://github.com/engswee/equalize-cpi-converter) - Inspiration for CPI converter architecture

## Support

For issues and questions:
- Create an issue in the GitHub repository
- Check the [documentation](docs/) for detailed guides
- Review the [test cases](src/test/) for usage examples
