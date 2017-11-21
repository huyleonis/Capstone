/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Utils;

import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.Services.AccountService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author hp
 */
public class OTPUtils {

    public static final String ACCOUNT_SID = "AC152cc9c5f8c26c9c4da5ba48d7632316";
    public static final String AUTH_TOKEN = "5186d4f64444daa62e7975db57813369";

    /**
     * Gửi mã OTP qua sms
     *
     * @param phone: số điện thoại nhận mã otp
     * @param username: tên người dùng
     * @param basePath
     * @throws IOException
     */
    public static void sendOTP(String phone, String username, String basePath) throws IOException {
        String numberOtp = createFileOTP(username, basePath);
        String phoneStr = phone.replaceFirst("0", "+84");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(phoneStr), new PhoneNumber("+18643830360"),
                "Mã OTP của bạn là:" + numberOtp).create();

    }

    /**
     * Tạo file và đọc mã OTP trong file username.txt
     *
     * @param username
     * @return mã OTP
     * @throws IOException
     */
    private static String createFileOTP(String username, String basePath) throws IOException {

        String numberOTP = numberOTP();
        String path = basePath + "/WEB-INF/otp/" + username + ".txt";
        File file = new File(path);

        if (!file.exists()) {
            file.createNewFile();
        }

        try (FileWriter fw = new FileWriter(file.getAbsoluteFile()); 
                BufferedWriter bw = new BufferedWriter(fw)) {            
            //Write in file
            bw.write(numberOTP);            
        }

        return numberOTP;
    }

    /**
     * Tạo mã OTP với 6 số random
     *
     * @return mã OTP
     */
    private static String numberOTP() {
        String tokenRandom = "";
        Random random = new Random();

        tokenRandom += (random.nextInt(9) + 1);
        for (int i = 1; i < 6; i++) {
            tokenRandom += random.nextInt(9);
        }

        return tokenRandom;
    }

    /**
     * Lấy mã OTP chứa trong file
     * @param username
     * @param basePath 
     * @return 
     */
    public static String getOtpNumber(String username, String basePath ) {
        String path = basePath + "/WEB-INF/otp/" + username + ".txt";
        String otpNumber = "";
        BufferedReader br = null;
        FileReader fr = null;
        try {
            File f = new File(path);
            
            if (!f.exists()) {
                return null;
            }
            fr = new FileReader(path);
            br = new BufferedReader(fr);

            while ((otpNumber = br.readLine()) != null) {
                return otpNumber;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return otpNumber;
    }

    /**
     * Xóa file chứa mã OTP với tên username
     *
     * @param username
     * @param basePath 
     * @throws IOException
     */
    public static void deleteFileOTP(String username, String basePath) {
        String filePath = basePath + "/WEB-INF/otp/" + username + ".txt";
        File file = new File(filePath);
        boolean status = file.delete();
        if (status) {
            System.out.println("File deleted successfully!!");
        } else {
            System.out.println("File name " + username + ".txt does not exists");
        }
    }

    /**
     * Tạo mã token 16 kí tự
     * @return
     */
    public static String randomToken(){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        while(sb.length() < 19) {

            int index = (int) (random.nextFloat() * alphabet.length());
            if (sb.length() % 5 == 4&& sb.length() != 0) {
                sb.append("-");
            } else {
                sb.append(alphabet.charAt(index));
            }
        }
        System.out.println("token:" + sb.toString());

        return sb.toString();
    }
}
