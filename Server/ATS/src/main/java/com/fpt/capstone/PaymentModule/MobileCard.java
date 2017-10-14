/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.PaymentModule;

import com.sun.org.apache.xml.internal.utils.NSInfo;
import com.sun.xml.internal.ws.wsdl.parser.WSDLConstants;
import org.apache.tomcat.util.security.MD5Encoder;

/**
 *
 * @author hp
 */
public class MobileCard {
    
    public void cardPayWebService(String pinCard, String cardSerial, String typeCard,
            String refCode, String clientFullname, String clientMobile, String clientEmail) {
        
        String merchantId = NganLuongConfig.MERCHANT_ID;
        String params = pinCard + "|" + cardSerial + "|" + typeCard + "|" +
            refCode + "|" + clientFullname + "|" + clientMobile + "|" + clientEmail;
        String checksum = MD5Encoder.encode(params.getBytes());
        
        


    }
}
