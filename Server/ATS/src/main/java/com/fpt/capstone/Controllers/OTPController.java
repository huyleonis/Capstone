package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.Services.AccountService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.*;
import java.util.Random;

@RestController
@RequestMapping("/otp")
public class OTPController {

    public static final String ACCOUNT_SID = "AC152cc9c5f8c26c9c4da5ba48d7632316";
    public static final String AUTH_TOKEN = "5186d4f64444daa62e7975db57813369";

    @Autowired
    private AccountService accountService;

    /**
     * Gửi mã OTP qua sms
     * @param username: tên người dùng
     * @throws IOException
     */
    @RequestMapping(value = "sendOTP/{username}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void sendOTP(@PathVariable String username) throws IOException {
        String numberOtp = createFileOTP(username);
        AccountDTO dto = accountService.getAccountByUsername(username);
        String phone = dto.getPhone();
        String phoneStr = phone.replaceFirst("0", "+84");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(phoneStr), new PhoneNumber("+18643830360"),
                "Mã OTP của bạn là:" + numberOtp).create();

    }

    /**
     * Tạo file và đọc mã OTP trong file username.txt
     * @param username
     * @return mã OTP
     * @throws IOException
     */
    private String createFileOTP(String username) throws IOException {

            String numberOTP = numberOTP();
            String path = "D:\\otp\\" + username + ".txt";
            File file = new File(path);

            //if file doesn't exits, then create it
            if(!file.exists()){
            //file.delete();
                file.createNewFile();
            }

        FileWriter fw = null;
        fw = new FileWriter(file.getAbsoluteFile());

        BufferedWriter bw = new BufferedWriter(fw);

        //Write in file
        bw.write(numberOTP);

        //close connection
        bw.close();
        fw.close();

        return numberOTP;
    }

    /**
     * Tạo mã OTP với 6 số random
     * @return mã OTP
     */
    private String numberOTP() {
        String tokenRandom = "";
        Random random = new Random();

        tokenRandom += (random.nextInt(9) + 1);
        for (int i = 1; i < 6; i++) {
            tokenRandom += random.nextInt(9);
        }

        return tokenRandom;
    }

    /**
     * Kiểm tra biến số xe người dùng
     * @param username
     * @return biển số xe
     */
    @RequestMapping(value = "getLicensePlate/{username}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getLicensePlateOfAccount(@PathVariable String username){
        AccountDTO dto = accountService.getAccountByUsername(username);

        return dto.getLicensePlate();
    }

    /**
     * Mở file chứa mã OTP và trả về mã OTP
     * @param username
     * @return mã OTP
     */
    @RequestMapping(value = "checkOtpNumber/{username}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String checkOtpNumber(@PathVariable String username)  {
        String path = "D:\\otp\\" + username +".txt";
        String otpNumber = "";
        BufferedReader br = null;
        FileReader fr = null;
        try{
            fr = new FileReader(path);
            br = new BufferedReader(fr);

            while ((otpNumber= br.readLine()) != null){
                System.out.println("otpnumber: "+otpNumber);
                return otpNumber;
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(br != null){
                    br.close();
                }
                if(fr != null){
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
     * @param username
     * @throws IOException
     */
    @RequestMapping(value = "deleteFile/{username}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void deleteFileAfterInputOTP(@PathVariable String username) throws IOException {
        String file_path = "D:\\otp\\" + username + ".txt";
        File file = new File(file_path);
        boolean status = file.delete();
        if (status){
            System.out.println("File deleted successfully!!");
        }else {
            System.out.println("File name "  + username + ".txt does not exists");
        }

    }
}
