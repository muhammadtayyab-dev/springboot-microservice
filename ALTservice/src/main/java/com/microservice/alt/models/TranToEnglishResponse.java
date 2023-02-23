package com.microservice.alt.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "TranToEnglishResponse", namespace = "http://tempuri.org/")
public class TranToEnglishResponse implements Serializable {
   @XmlAttribute(name = "name_OUT")
   public String name_OUT;
   @XmlAttribute(name = "fatherName_OUT")
   public String fatherName_OUT;
   @XmlAttribute(name = "motherName_OUT")
   public String motherName_OUT;
   @XmlAttribute(name = "address1_OUT")
   public String address1_OUT;
   @XmlAttribute(name = "address2_OUT")
   public String address2_OUT;
   @XmlAttribute(name = "placeOfBirth_OUT")
   public String placeOfBirth_OUT;
   @XmlAttribute(name = "NIC_OUT")
   public String NIC_OUT;
   @XmlAttribute(name = "AKSA_TRAN_ID_OUT")
   public String AKSA_TRAN_ID_OUT;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   @XmlAttribute(name = "message_OUT")
   public String message_OUT;
}
