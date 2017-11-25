/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackathon.fpt.ktk.paymentmodule;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Chi Hieu
 */
public class NganLuongConfig {
    public static final String NGANLUONG_URL_CARD_SOAP = "https://nganluong.vn/mobile_card_api.php?wsdl";
    public static final String NGANLUONG_URL_CARD_POST = "https://www.nganluong.vn/mobile_card.api.post.v2.php";
    public static final String FUNCTION  = "CardCharge";
    public static final String VERSION  = "2.0";    
    public static final String MERCHANT_ID  = "52424";
    public static final String MERCHANT_PASSWORD  = "3928346a3f78e225f4d2aefdec93bf68";
    public static final String MERCHANT_PASSWORD_ENCODED  = "c6ffdd1a53a567a017dcafed4ec29b5f";
    public static final String EMAIL_RECEIVE_MONEY  = "huy2896@gmail.com";
    
    public static final String MAIN_URL = "https://www.nganluong.vn/mobile_checkout_api_post.php";
    public static final String RETURN_URL = "https://www.nganluong.vn/nganluong/homeDeveloper/DeveloperBank.html";
    public static final String CANCEL_URL = "https://www.nganluong.vn/nganluong/homeDeveloper/DeveloperBank.html";
    public static final String NOTIFY_URL = "https://www.nganluong.vn/nganluong/homeDeveloper/DeveloperBank.html";
    
    public static String md5(String strMd5) {
        try {
            String password = strMd5;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            //convert the byte to hex format method 2
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            return ex.toString();
        }
    }
}
