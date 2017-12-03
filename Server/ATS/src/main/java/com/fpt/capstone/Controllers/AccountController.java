package com.fpt.capstone.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.Entities.Account;
import com.fpt.capstone.Services.AccountService;
import com.fpt.capstone.Utils.OTPUtils;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/account")
public class AccountController {

    static final int ACTIVE = 2;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ServletContext context;

    /**
     * Hi?n th? trang account.jsp
     *
     * @return beacon view
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewAccount() {
        ModelAndView m = new ModelAndView("user");
        m.addObject("currSelected", ACTIVE);
        m.addObject("currTitle", "Account Management");
        return m;
    }

    /**
     * L?y danh sách account
     *
     * @return danh sách Account du?i d?ng JSONARRAY
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @RequestMapping(value = "/getListAccount", method = RequestMethod.GET)
    public String getListAccount() throws JsonProcessingException {
        // ObjectMapper mapper = new ObjectMapper();
        List<AccountDTO> list = accountService.getListAccount();
        return new Gson().toJson(list);
    }

    /**
     * Get account info by username
     *
     * @param username
     * @return accountDto
     */
    @RequestMapping(value = "/get/{username}", method = RequestMethod.GET)
    public AccountDTO getAccountByUsername(@PathVariable String username) {
        return accountService.getAccountByUsername(username);
    }

    /**
     * T?o account m?i
     *
     * @param account Account entity
     * @return K?t qu? th?c hi?n
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody Account account) {

        boolean isSuccessful = false;

        AccountDTO dto = accountService.insert(account);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    /**
     *
     * @param account Account entity
     * @return k?t qu? th?c hi?n
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Account account) {

        boolean isSuccessful = false;

        AccountDTO dto = accountService.update(account);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    /**
     * Check dang nh?p t? Mobile Application
     *
     * @param username
     * @param password
     * @return K?t qu? dang nh?p t? hàm checkLogin
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object checkLogin(@RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password) {

        Map<String, String> map = new HashMap<>();
        String result = accountService.checkLogin(username, password);
        map.put("result", result);

        String basePath = context.getRealPath(".");

        /*
        N?u ki?m tra username, password thành công (Success)
        H? th?ng ti?n hành g?i mã OTP v? di?n tho?i driver
        Mã OTP b? xóa sau 5 phút ho?c khi check OTP thành công
         */
        if (result.equals("Success")) {
            AccountDTO account = accountService.getAccountByUsername(username);
            map.put("fullname", account.getFullname());
            try {
                OTPUtils.sendOTP(account.getPhone(), username, basePath);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        OTPUtils.deleteFileOTP(username, basePath);
                    }
                }, 1000 * 60 * 5);
            } catch (IOException ex) {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return map;
    }

    /**
     * Check mã OTP
     *
     * @param username
     * @param licensePlate bi?n s? xe
     * @param otp mã OTP
     * @return K?t qu? ki?m tra
     */
    @RequestMapping(value = "/otp", method = RequestMethod.POST)
    public Object checkOTPForLogin(@RequestParam("username") String username,
            @RequestParam(name = "licensePlate") String licensePlate,
            @RequestParam(name = "OTP") String otp) {

        boolean checkResult = accountService.checkLicensePlate(username, licensePlate);
        if (!checkResult) {
            return "License Plate is invalid";
        }

        String basePath = context.getRealPath(".");

        String otpCreated = OTPUtils.getOtpNumber(username, basePath);
        OTPUtils.deleteFileOTP(username, basePath);
        if (otpCreated == null || !otpCreated.equals(otp)) {
            return "OTP is invalid";
        }
        String randomToken = OTPUtils.randomToken();
        boolean updateToken = accountService.updateToken(username, randomToken);
        if (!updateToken) {
            return "Update token is invalid";
        }
        return "Success= " + randomToken;
    }

    /**
     * Check Token Login
     *
     * @param username
     * @param token Mã token - Dùng d? check login
     * @return k?t qu?
     */
    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
    public String checkToken(@RequestParam String username, @RequestParam String token) {
        AccountDTO dto = accountService.getAccountByUsername(username);
        if (dto == null) {
            return "false";
        } else {
            String accToken = dto.getToken();

            if (accToken != null && accToken.equals(token)) {
                return "true";
            }
            return "false";
        }
    }

    /**
     * Check Login cho desktop
     *
     * @param username
     * @param password
     * @return accountDTO
     */
    @RequestMapping(value = "/checkLogin/{username}/{password}", method = RequestMethod.GET)
    @ResponseBody
    public AccountDTO checkLoginDesktop(@PathVariable String username, @PathVariable String password) {
        return accountService.checkLoginFromDesktopApp(username, password);
    }

    /**
     * active tai khoan
     * @param account
     * @return
     */
    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public String active(@RequestBody Account account) {

        boolean isSuccessful = accountService.active(account);

        return (isSuccessful)? "success" : "fail";
    }

    /**
     * deactive tai khoan
     * @param account
     * @return
     */
    @RequestMapping(value = "/deactive", method = RequestMethod.POST)
    public String deactive(@RequestBody Account account) {

        boolean isSuccessful = accountService.deactive(account);

        return (isSuccessful)? "success" : "fail";
    }
    @RequestMapping(value = "/otp", method = RequestMethod.POST)
    public Object checkOTPForLogin(@RequestParam("username") String username,
                                   @RequestParam(name = "licensePlate") String licensePlate,
                                   @RequestParam(name = "OTP") String otp) {

        boolean checkResult = accountService.checkLicensePlate(username, licensePlate);

        if (!checkResult) {
            return "License Plate is invalid";
        }

        String basePath = context.getRealPath(".");

        String otpCreated = OTPUtils.getOtpNumber(username, basePath);
        OTPUtils.deleteFileOTP(username, basePath);
        if (otpCreated == null || !otpCreated.equals(otp)) {
            return "OTP is invalid";
        }
        String randomToken = OTPUtils.randomToken();
        boolean updateToken = accountService.updateToken(username, randomToken);
        if (!updateToken) {
            return "Update token is invalid";
        }
        return "Success= " + randomToken;
    }

    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
    public boolean checkToken(@RequestParam String username, @RequestParam String token) {
        AccountDTO dto = accountService.getAccountByUsername(username);
        String accToken = dto.getToken();

        if (accToken.equals(token)) {
            return true;
        }
        return false;
    }


    @RequestMapping(value = "/insertAccount", method = RequestMethod.POST)
    public Object checkInfForRegister(@RequestParam(name = "username") String username,
                                      @RequestParam(name = "password") String password,
                                      @RequestParam(name = "email") String email,
                                      @RequestParam(name = "phone") String phone,
                                      @RequestParam(name = "numberId") String numberId,
                                      @RequestParam(name = "licensePlate") String licensePlate) {
        boolean checkUsername = accountService.checkUsername(username);
        if (checkUsername) {
            return "Username is existed";
        }

        boolean checkLicensePlate = false;
        Vehicle vehicle = vehicleRepos.findByLicensePlate(licensePlate);
        if (vehicle != null) {
            checkLicensePlate = true;
        }
        if (!checkLicensePlate) {
            return "License plate is existed";
        }

        accountService.insert(username, password, email, phone, numberId, licensePlate);
        return "Success";
    }
}