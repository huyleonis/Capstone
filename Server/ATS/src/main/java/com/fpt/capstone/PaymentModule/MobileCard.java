/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.PaymentModule;

import com.fpt.capstone.Utils.RequestServer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import org.apache.tomcat.util.security.MD5Encoder;

/**
 *
 * @author hp
 */
public class MobileCard {
    
    public void cardPayWebService(String pinCard, String cardSerial, String typeCard,
            String refCode, String clientFullname, String clientMobile, String clientEmail) throws MalformedURLException, IOException {
                
        byte[] input = 
                (NganLuongConfig.MERCHANT_ID + "|" + 
                        NganLuongConfig.MERCHANT_PASSWORD).getBytes();
        String password = MD5Encoder.encode(input);
        
        Map<String, String> params = new HashMap<>();
        params.put("func", NganLuongConfig.FUNCTION);
        params.put("version", NganLuongConfig.VERSION);
        params.put("merchant_id", NganLuongConfig.MERCHANT_ID);
        params.put("merchant_account", NganLuongConfig.EMAIL_RECEIVE_MONEY);
        params.put("merchant_password", password);
        params.put("pin_card", pinCard);
        params.put("card_serial", cardSerial);	
        params.put("type_card", typeCard);
        params.put("ref_code", refCode);
        params.put("client_fullname", clientFullname);
        params.put("client_email", clientEmail);
        params.put("client_mobile", clientMobile);
        
        String sUrl = NganLuongConfig.NGANLUONG_URL_CARD_POST;                
             
        String response = RequestServer.sendPostRequest(sUrl, params);
        
        String[] data = response.split("|");
        
                
    }
        
}
