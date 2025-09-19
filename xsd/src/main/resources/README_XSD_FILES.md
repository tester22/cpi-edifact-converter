# EDIFACT XSD Files

This directory contains XSD (XML Schema Definition) files required for EDIFACT to XML and XML to EDIFACT conversions using the sapstern/edifactconverter library.

## Available XSD Files

### EDIFACT Version 01B
- `EDIFACTINTERCHANGE_INVOIC_01B.xsd` - Invoice messages (D.01B)
- `EDIFACTINTERCHANGE_ORDERS_01B.xsd` - Order messages (D.01B)

### EDIFACT Version 07B
- `EDIFACTINTERCHANGE_INVOIC_07B.xsd` - Invoice messages (D.07B)
- `EDIFACTINTERCHANGE_UTILMD_07B.xsd` - Utility messages (D.07B)

### EDIFACT Version 11A
- `EDIFACTINTERCHANGE_APERAK_11A.xsd` - Application Error and Acknowledgment messages (D.11A)

### EDIFACT Version 17B
- `EDIFACTINTERCHANGE_INVOIC_17B.xsd` - Invoice messages (D.17B)
- `EDIFACTINTERCHANGE_UTILMD_17B.xsd` - Utility messages (D.17B)

### EDIFACT Version 96A
- `EDIFACTINTERCHANGE_INVOIC_96A.xsd` - Invoice messages (D.96A)
- `EDIFACTINTERCHANGE_ORDCHG_96A.xsd` - Order Change messages (D.96A)
- `EDIFACTINTERCHANGE_ORDERS_96A.xsd` - Order messages (D.96A)
- `ORDRSP_INVOIC_96A.xsd` - Order Response for Invoice messages (D.96A)
- `ORDRSP_ORDCHG_96A.xsd` - Order Response for Order Change messages (D.96A)
- `ORDRSP_ORDRSP_96A.xsd` - Order Response messages (D.96A)

## Usage

These XSD files are automatically loaded by the sapstern/edifactconverter library when performing conversions. The library will automatically detect the appropriate XSD file based on the EDIFACT message type and version.

## Message Types Supported

- **INVOIC** - Invoice messages
- **ORDERS** - Order messages
- **ORDCHG** - Order Change messages
- **ORDRSP** - Order Response messages
- **APERAK** - Application Error and Acknowledgment messages
- **UTILMD** - Utility messages

## EDIFACT Versions Supported

- **D.01B** - Directory 01B
- **D.07B** - Directory 07B
- **D.11A** - Directory 11A
- **D.17B** - Directory 17B
- **D.96A** - Directory 96A

## Notes

- These XSD files are generated from the official UN/EDIFACT directories
- The files are used for validation and structure definition during conversion
- The sapstern library automatically selects the appropriate XSD based on the message content
- All files are in UTF-8 encoding and follow the UN/EDIFACT standard
