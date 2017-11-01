/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.request;

import ats.dtos.LoginDTO;
import ats.dtos.VehicleDTO;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Chi Hieu
 */
public class LoginRequest {

    public static final String LOCALHOST = "http://localhost:8080";

    /**
     * Gửi request lên server để lấy thông tin đăng nhập từ desktop application
     *
     * @param username Tên tài khoản
     * @param password Mật khẩu đăng nhập
     * @return LoginDTO(username, role, fullname)
     */
    public LoginDTO checkLogin(String username, String password) throws Exception {
        String urlName = LOCALHOST + "/login/checkLogin/" + username + "/" + password;
        JSONParser parser = new JSONParser();
        LoginDTO loginDTO = new LoginDTO();
        try {
            URL oracle = new URL(urlName); // URL to Parse
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject account = (JSONObject) parser.parse(inputLine);
                if (account != null) {
                    Long role = (Long) account.get("role");
                    String fullname = (String) account.get("fullname");
                    loginDTO = new LoginDTO(username, role, fullname);

                } else {
                    loginDTO = null;
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return loginDTO;
    }
}