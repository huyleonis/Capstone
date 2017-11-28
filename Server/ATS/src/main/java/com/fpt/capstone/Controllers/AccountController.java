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
     * Hiển thị trang account.jsp
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
     * Lấy danh sách account
     *
     * @return danh sách Account dưới dạng JSONARRAY
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
     * Tạo account mới
     *
     * @param account Account entity
     * @return Kết quả thực hiện
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody Account account) {

        boolean isSuccessful = false;

        // system will generate password automatically
        //account.setPassword("123");
        AccountDTO dto = accountService.insert(account);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    /**
     * Enable/Disable account
     *
     * @param account Account entity
     * @return kết quả thực hiện
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
     * Check đăng nhập từ Mobile Application
     *
     * @param username
     * @param password
     * @return Kết quả đăng nhập từ hàm checkLogin
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object checkLogin(@RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password) {

        Map<String, String> map = new HashMap<>();
        String result = accountService.checkLogin(username, password);
        map.put("result", result);

        String basePath = context.getRealPath(".");

        /*
        Nếu kiểm tra username, password thành công (Success)
        Hệ thống tiến hành gửi mã OTP về điện thoại driver
        Mã OTP bị xóa sau 5 phút hoặc khi check OTP thành công
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
     * @param licensePlate biển số xe
     * @param otp mã OTP
     * @return Kết quả kiểm tra
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
     * @param token Mã token - Dùng để check login
     * @return kết quả
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
    public String activeAccount(@RequestBody Account account) {

        boolean isSuccessful = accountService.active(account);

        return (isSuccessful)? "success" : "fail";
    }

    /**
     * deactive tai khoan
     * @param account
     * @return
     */
    @RequestMapping(value = "/deactive", method = RequestMethod.POST)
    public String deactiveAccount(@RequestBody Account account) {

        boolean isSuccessful = accountService.deactive(account);

        return (isSuccessful)? "success" : "fail";
    }
}
