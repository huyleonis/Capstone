/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Controllers;

import java.util.List;

import com.fpt.capstone.Entities.Vehicle;
import com.fpt.capstone.Repositories.VehicleRepos;
import com.fpt.capstone.Services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.Entities.Account;
import com.fpt.capstone.Services.AccountService;
import com.fpt.capstone.Utils.OTPUtils;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ServletContext context;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleRepos vehicleRepos;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewAccount() {
        ModelAndView m = new ModelAndView("user");
        return m;
    }

    @RequestMapping(value = "/getListAccount", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getListAccount() throws JsonProcessingException {
        // ObjectMapper mapper = new ObjectMapper();
        List<AccountDTO> list = accountService.getListAccount();
        return new Gson().toJson(list);
    }

    /**
     * Get account info by username
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/get/{username}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO getAccountByUsername(@PathVariable String username) {
        AccountDTO acc = accountService.getAccountByUsername(username);

        return acc;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Account account) {

        boolean isSuccessful = false;

        AccountDTO dto = accountService.update(account);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object checkLogin(@RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password) {

        Map<String, String> map = new HashMap<>();
        String result = accountService.checkLogin(username, password);
        map.put("result", result);

        String basePath = context.getRealPath(".");

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
                ex.printStackTrace();
            }
        }

        return map;
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
