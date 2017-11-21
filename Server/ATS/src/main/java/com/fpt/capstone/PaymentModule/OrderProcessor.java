/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.PaymentModule;

import com.fpt.capstone.Utils.RequestServer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hp
 */
public class OrderProcessor {
    public static String sendOrder(String orderCode, String amount, String clientFullname, 
            String clientMobile, String clientEmail, String clientAddress) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("func", "sendOrder");
        params.put("version", "1.0");
        params.put("merchant_id", NganLuongConfig.MERCHANT_ID);
        params.put("merchant_account", NganLuongConfig.EMAIL_RECEIVE_MONEY);
        params.put("order_code", orderCode);
        params.put("total_amount", amount);
        params.put("currency", "vnd");
        params.put("language", "vi");
        params.put("return_url", NganLuongConfig.RETURN_URL);
        params.put("cancel_url", NganLuongConfig.CANCEL_URL);
        params.put("notify_url", NganLuongConfig.NOTIFY_URL);
        params.put("buyer_fullname", clientFullname);
        params.put("buyer_email", clientEmail);
        params.put("buyer_mobile", clientMobile);
        params.put("buyer_address", clientAddress);
        params.put("checksum", getCheckSum(params));
        
        String sUrl = NganLuongConfig.MAIN_URL;

        String response = RequestServer.sendPostRequest(sUrl, params);
        System.out.println("send order response: " + response);
        return response;
    }

    private static String getCheckSum(Map<String, String> params) {
        String stringSendOrder = params.get("func") + "|" +
                params.get("version") + "|" +
                params.get("merchant_id") + "|" +
                params.get("merchant_account") + "|" +
                params.get("order_code") + "|" +
                params.get("total_amount") + "|" +
                params.get("currency") + "|" +
                params.get("language") + "|" +
                params.get("return_url") + "|" +
                params.get("cancel_url") + "|" +
                params.get("notify_url") + "|" +
                params.get("buyer_fullname") + "|" +
                params.get("buyer_email") + "|" +
                params.get("buyer_mobile") + "|" +
                params.get("buyer_address") + "|" +
                NganLuongConfig.MERCHANT_PASSWORD;
        System.out.println("Before MD5: " + stringSendOrder);
        String checkSum = NganLuongConfig.md5(stringSendOrder);
        System.out.println("After MD5: " + checkSum);
        return checkSum;
    }

    public static String checkOrder(String tokenCode) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("func", "checkOrder");
        params.put("version", "1.0");
        params.put("merchant_id", NganLuongConfig.MERCHANT_ID);
        params.put("token_code", tokenCode);
        params.put("checksum", getCheckSum2(params));              
        
        String sUrl = NganLuongConfig.MAIN_URL;

        String response = RequestServer.sendPostRequest(sUrl, params);
        System.out.println("check order response: " + response);
        return response;
    }

    private static String getCheckSum2(Map<String, String> params) {
        String stringSendOrder = params.get("func") + "|" +
                params.get("version") + "|" +
                params.get("merchant_id") + "|" +
                params.get("token_code") + "|" +
                NganLuongConfig.MERCHANT_PASSWORD;
        return NganLuongConfig.md5(stringSendOrder);
    }
}
