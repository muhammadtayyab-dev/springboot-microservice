package com.microservice.alt.services;

import com.microservice.alt.models.NadaraResponse;
import com.microservice.alt.models.Response;
import com.microservice.alt.models.TranToEnglishResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Iterator;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class ALTService {

    public SOAPElement getFirstBodyElement(SOAPBody body) {
        for (Iterator<?> iterator = body.getChildElements(); iterator.hasNext(); ) {
            Object child = iterator.next();
            if (child instanceof SOAPElement) {
                return (SOAPElement) child;
            }
        }
        return null;
    }

    public Response<TranToEnglishResponse> ALTService(NadaraResponse nadaraResponse) {
        TranToEnglishResponse altResponse = new TranToEnglishResponse();
        String soapAction = "http://tempuri.org/TranToEnglish";
        try {
            SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = connectionFactory.createConnection();
            URL endpoint = new URL(new URL("http://172.16.1.133/"), "AKSA/ALT/OCR/OCR.asmx", new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    URL target = new URL(url.toString());
                    URLConnection connection = target.openConnection();
                    connection.setConnectTimeout(15 * 1000);      // 15 sec
                    connection.setReadTimeout(15 * 1000);  // 15 sec
                    return (connection);
                }
            });
            SOAPMessage soapMessage = soapConnection.call(createSOAPRequest(soapAction, nadaraResponse), endpoint);

            NodeList nodeList = soapMessage.getSOAPBody().getElementsByTagNameNS("http://tempuri.org/", "TranToEnglishResponse");
            if (nodeList.getLength() == 1) {
                NodeList items = nodeList.item(0).getChildNodes();
                int length = nodeList.item(0).getChildNodes().getLength();
                for (int i = 0; i < length; i++) {
                    if (items.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) items.item(i);
                        if (Objects.equals(element.getTagName(), "name_OUT")) {
                            altResponse.name_OUT = element.getTextContent();
                        }
                        if (Objects.equals(element.getTagName(), "fatherName_OUT")) {
                            altResponse.fatherName_OUT = element.getTextContent();
                        }
                        if (Objects.equals(element.getTagName(), "motherName_OUT")) {
                            altResponse.motherName_OUT = element.getTextContent();
                        }
                        if (Objects.equals(element.getTagName(), "address1_OUT")) {
                            altResponse.address1_OUT = element.getTextContent();
                        }
                        if (Objects.equals(element.getTagName(), "address2_OUT")) {
                            altResponse.address2_OUT = element.getTextContent();
                        }
                        if (Objects.equals(element.getTagName(), "placeOfBirth_OUT")) {
                            altResponse.placeOfBirth_OUT = element.getTextContent();
                        }
                        if (Objects.equals(element.getTagName(), "AKSA_TRAN_ID_OUT")) {
                            altResponse.AKSA_TRAN_ID_OUT = element.getTextContent();
                        }
                        if (Objects.equals(element.getTagName(), "CNIC_OUT")) {
                            altResponse.NIC_OUT = element.getTextContent();
                        }
                    }
                }
            }
            System.out.println("Response SOAP Message");
            soapMessage.writeTo(System.out);
            System.out.println("\n");
            soapConnection.close();
        } catch (Exception ex) {
            Response<TranToEnglishResponse> response = new Response<>();
            response.status = false;
            response.message = ex.getMessage();
            return response;

        }
        Response<TranToEnglishResponse> response = new Response<>();
        response.status = true;
        response.message = "Response from the server";
        response.response = altResponse;
        return response;
    }

    public SOAPMessage createSOAPRequest(String soapAction, NadaraResponse nadaraResponse) throws Exception {
        String nameSpace = "tem";
        String nameSpaceURL = "http://tempuri.org/";
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(nameSpace, nameSpaceURL);
        SOAPBody soapBody = envelope.getBody();

//      Request Body
        SOAPElement soapElement = soapBody.addChildElement("TranToEnglish", nameSpace);
        SOAPElement channelIn = soapElement.addChildElement("channel_IN", nameSpace);
        channelIn.addTextNode("AKSA");
        SOAPElement clientAppIn = soapElement.addChildElement("clientApp_IN", nameSpace);
        clientAppIn.addTextNode("OCR");
        SOAPElement loginIn = soapElement.addChildElement("login_IN", nameSpace);
        loginIn.addTextNode("OCR");
        SOAPElement passwordIn = soapElement.addChildElement("password_IN", nameSpace);
        passwordIn.addTextNode("Co$9*O4R");
        SOAPElement tranIdIn = soapElement.addChildElement("Client_TRAN_ID_IN", nameSpace);
        tranIdIn.addTextNode(nadaraResponse.clientTranId());
        SOAPElement name = soapElement.addChildElement("name_IN", nameSpace);
        name.addTextNode(nadaraResponse.name());
        SOAPElement fatherName = soapElement.addChildElement("fatherName_IN", nameSpace);
        fatherName.addTextNode(nadaraResponse.fatherName());
        SOAPElement motherName = soapElement.addChildElement("motherName_IN", nameSpace);
        motherName.addTextNode(nadaraResponse.motherName());
        SOAPElement address1In = soapElement.addChildElement("address1_IN", nameSpace);
        address1In.addTextNode(nadaraResponse.address1());
        SOAPElement address2In = soapElement.addChildElement("address2_IN", nameSpace);
        address2In.addTextNode(nadaraResponse.address2());
        SOAPElement placeOfBirthIn = soapElement.addChildElement("placeOfBirth_IN", nameSpace);
        placeOfBirthIn.addTextNode(nadaraResponse.placeOfBirth());
        SOAPElement cnicIn = soapElement.addChildElement("CNIC_IN", nameSpace);
        cnicIn.addTextNode(nadaraResponse.CNIC());
//      end

        MimeHeaders header = message.getMimeHeaders();
        header.addHeader("SOAPAction", soapAction);
        message.saveChanges();
        System.out.println("Request SOAP Message");
        message.writeTo(System.out);
        System.out.println("\n");
        return message;
    }

}
