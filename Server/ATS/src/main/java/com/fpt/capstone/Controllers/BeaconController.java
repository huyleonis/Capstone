/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Controllers;

import java.util.List;

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
import com.fpt.capstone.Dtos.BeaconDTO;
import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Entities.Account;
import com.fpt.capstone.Entities.Beacon;
import com.fpt.capstone.Repositories.AccountRepos;
import com.fpt.capstone.Repositories.BeaconRepos;
import com.fpt.capstone.Services.AccountService;
import com.fpt.capstone.Services.BeaconService;
import com.fpt.capstone.Services.PriceService;
import com.fpt.capstone.Services.TransactionService;
import com.fpt.capstone.Utils.BeaconType;
import com.fpt.capstone.Utils.TransactionStatus;
import com.google.gson.Gson;

@RestController
@RequestMapping("/beacon")
public class BeaconController {

    @Autowired
    private BeaconService beaconService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BeaconRepos beaconRepos;

    @Autowired
    private PriceService priceService;
    
    @Autowired
    private AccountRepos accountRepos;

    @RequestMapping(value = "getBeacon/{uuid}/{major}/{minor}")
    @ResponseStatus(HttpStatus.OK)
    public BeaconDTO getBeacon(@PathVariable String uuid,
            @PathVariable String major, @PathVariable String minor) {

        System.out.println("Get Beacon information: " + uuid + " - " + major + " - "
                + minor);
        int iMajor = Integer.parseInt(major);
        int iMinor = Integer.parseInt(minor);

        Beacon beaconEntity = beaconRepos.getBeacon(uuid, iMajor, iMinor);
        if (beaconEntity != null) {
            BeaconDTO dto = BeaconDTO.convertFromEntity(beaconEntity);
            System.out.println("Get beacon info - type " + dto.getType());
            return dto;
        } else {
            BeaconDTO dto = new BeaconDTO();
            dto.setId(0);
            dto.setUuid(uuid);
            dto.setMajor(iMajor);
            dto.setMinor(iMinor);
            dto.setType(BeaconType.BEACON_OTHER);
            return dto;
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewAccount() {
        ModelAndView m = new ModelAndView("beacon");
        return m;
    }

    @RequestMapping(value = "/get/{uuid}", method = RequestMethod.GET)
    public BeaconDTO getBeacon(@PathVariable String uuid) {

        return null;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAllBeacon() throws JsonProcessingException {

        List<BeaconDTO> dtos = beaconService.getAllBeacon();

        return new Gson().toJson(dtos);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody Beacon beacon) {

        boolean isSuccessful = false;

        BeaconDTO dto = beaconService.insert(beacon);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Beacon beacon) {
        boolean isSuccessful = false;

        BeaconDTO dto = beaconService.update(beacon);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    /**
     * Khi xe nhận tín hiệu beacon 1, kiểm tra xem camera có chụp dc hình tương
     * ứng thì trả về transaction ứng với hình, không thì trả về giá và hỏi
     * người dùng có tạo transaction hay không.
     * @param stationId
     * @param licensePlate
     * @return 
     */
    @RequestMapping(value = "/payment/{stationId}/{username}", method = RequestMethod.GET)
    public Object triggerBeaconPayment(@PathVariable int stationId, @PathVariable String username) {

        System.out.println("Triggered Beacon Payment");
        TransactionDetailDTO transaction;
        transaction = transactionService.getCapturedTransaction(stationId, stationId);  
        
        Account account = accountRepos.findByUsername(username);

        if (transaction == null) {
            System.out.println("Not found captured payment created by camera");
            return priceService.findPriceByStationIdAndLicensePlate(stationId, account.getVehicle().getLicensePlate());
        }

        System.out.println("Found captured payment created by camera");
        return transaction;
    }

    /**
     * Khi xe nhận tín hiệu beacon 2, gửi yêu cầu check result. Server cập nhật
     * trạng
     *
     * @param laneId
     * @param idTransaction
     * @return
     */
    @RequestMapping(value = "/result/{laneId}/{transactionId}", method = RequestMethod.GET)
    public String triggerBeaconResult(@PathVariable int laneId, @PathVariable String transactionId) {
        System.out.println("Request Check result from Driver in lane with ID " + laneId);

        TransactionDTO transDTO = transactionService.getById(transactionId);
        
        if (transDTO == null) {
            return "false";
        }
        
        TransactionStatus status = transDTO.getTransactionStatus();
        if (status == TransactionStatus.TRANS_SUCCESS
                || status == TransactionStatus.TRANS_FAILED) {

            TransactionStatus newStatus;
            if (status == TransactionStatus.TRANS_SUCCESS) {
                newStatus = TransactionStatus.TRANS_FINISH;
            } else {
                newStatus = TransactionStatus.TRANS_FAILED_PASSED;
            }

            // Cập nhật làn xe vô
            transDTO = transactionService.updateTransactionLane(transactionId, laneId);

            System.out.println(
                    "   + Update trans (1) lane [" + transDTO.getLaneId() + "] and status [" + transDTO.getStatus() + "]");

            // Cập nhật trạng thái thành chờ xử lý
            transDTO = transactionService.updateTransactionStatus(transactionId, newStatus);

            System.out.println(
                    "   + Update trans (2) lane [" + transDTO.getLaneId() + "] and status [" + transDTO.getStatus() + "]");
            return "true";
        }

        System.out.println("Transaction is wrong status: " + transDTO.getStatus());
        return "false";
    }
}
