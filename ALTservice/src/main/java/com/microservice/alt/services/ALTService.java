package com.microservice.alt.services;

import com.microservice.alt.models.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.soap.*;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

@Slf4j
@Service
@AllArgsConstructor
public class ALTService {


    public Response<String> ALTService() {
//        String endpoint = "http://172.16.1.133/AKSA/ALT/OCR/OCR.asmx";
        String soapAction = "http://tempuri.org/TranToEnglish";
        try {
            SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = connectionFactory.createConnection();
            URL endpoint =
                    new URL(new URL("http://172.16.1.133/"),
                            "AKSA/ALT/OCR/OCR.asmx",
                            new URLStreamHandler() {
                                @Override
                                protected URLConnection openConnection(URL url) throws IOException {
                                    URL target = new URL(url.toString());
                                    URLConnection connection = target.openConnection();
                                    connection.setConnectTimeout (15 * 1000);      // 15 sec
                                    connection.setReadTimeout (15 * 1000);  // 15 sec
                                    return(connection);
                                }
                            });
            SOAPMessage soapMessage = soapConnection.call(createSOAPRequest(soapAction), endpoint);
            System.out.println("Response SOAP Message");
            soapMessage.writeTo(System.out);
            System.out.println("\n");
            soapConnection.close();
        } catch (Exception ex) {
            Response<String> response = new Response<>();
            response.status = false;
            response.message = ex.getMessage();
            return response;

        }
        Response<String> response = new Response<>();
        response.status = true;
        response.message = "Response from the server";
        return response;
    }

    public SOAPMessage createSOAPRequest(String soapAction) throws Exception {
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

        SOAPElement element4 = soapElement.addChildElement("password_IN", nameSpace);
        element4.addTextNode("Co$9*O4R");

        SOAPElement tranIdIn = soapElement.addChildElement("Client_TRAN_ID_IN", nameSpace);
        tranIdIn.addTextNode("20220620150002");

        SOAPElement name = soapElement.addChildElement("name_IN", nameSpace);
        name.addTextNode("20220620150002");

        SOAPElement fatherName = soapElement.addChildElement("fatherName_IN", nameSpace);
        fatherName.addTextNode("OCR");

        SOAPElement motherName = soapElement.addChildElement("motherName_IN", nameSpace);
        motherName.addTextNode("OCR");

        SOAPElement address1In = soapElement.addChildElement("address1_IN", nameSpace);
        address1In.addTextNode("OCR");

        SOAPElement address2In = soapElement.addChildElement("address2_IN", nameSpace);
        address2In.addTextNode("OCR");

        SOAPElement placeOfBirthIn = soapElement.addChildElement("placeOfBirth_IN", nameSpace);
        placeOfBirthIn.addTextNode("OCR");

        SOAPElement cnicIn = soapElement.addChildElement("CNIC_IN", nameSpace);
        cnicIn.addTextNode("OCR");

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
