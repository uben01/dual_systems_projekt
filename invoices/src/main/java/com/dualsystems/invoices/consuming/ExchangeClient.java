package com.dualsystems.invoices.consuming;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.*;
import java.io.StringReader;
import java.text.NumberFormat;
import java.util.Locale;

public class ExchangeClient {


    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "MNBArfolyamServiceSoapImpl";
        String myNamespaceURI = "http://www.mnb.hu/webservices/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("GetCurrentExchangeRatesRequest", myNamespace);
    }

    public static double getExchangeRate() {
        String soapEndpointUrl = "http://www.mnb.hu/arfolyamok.asmx?wsdl";
        String soapAction = "http://www.mnb.hu/webservices/MNBArfolyamServiceSoap/GetCurrentExchangeRates";
        double rate = 350; // backup if something goes wrong
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
//            System.out.println("Response SOAP Message:");
//
//            soapResponse.writeTo(System.out);
//            System.out.println();

            SOAPBody body = soapResponse.getSOAPBody();
            NodeList GetCurrentExchangeRatesResult = body.getElementsByTagName("GetCurrentExchangeRatesResult");
            String MNBCurrentExchangeRates = GetCurrentExchangeRatesResult.item(0).getTextContent();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;

            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(MNBCurrentExchangeRates)));

            NodeList day = document.getElementsByTagName("Day");
            Node eur = day.item(0).getChildNodes().item(8);

            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            Number number = format.parse(eur.getTextContent());

            rate = number.doubleValue();

            soapConnection.close();

        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
        return rate;
    }

    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
//        System.out.println("Request SOAP Message:");
//        soapMessage.writeTo(System.out);
//        System.out.println("\n");

        return soapMessage;
    }


}