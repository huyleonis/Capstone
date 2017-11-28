package com.fpt.capstone.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.BeaconDTO;
import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Entities.Account;
import com.fpt.capstone.Entities.Beacon;
import com.fpt.capstone.Repositories.AccountRepos;
import com.fpt.capstone.Repositories.BeaconRepos;
import com.fpt.capstone.Services.BeaconService;
import com.fpt.capstone.Services.PriceService;
import com.fpt.capstone.Services.TransactionService;
import com.fpt.capstone.Utils.BeaconType;
import com.fpt.capstone.Utils.TransactionStatus;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    static final int ACTIVE = 3;

    /**
     * Hiển thị trang beacon.jsp
     *
     * @return beacon view
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewBeacon() {
        ModelAndView m = new ModelAndView("beacon");
        m.addObject("currSelected", ACTIVE);
        m.addObject("currTitle", "Beacon Management");
        return m;
    }

    /**
     * Lấy danh sách beacon
     *
     * @return danh sách Beacon dưới dạng JSONARRAY
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAllBeacon() throws JsonProcessingException {
        List<BeaconDTO> dtos = beaconService.getAllBeacon();
        return new Gson().toJson(dtos);
    }

    /**
     * Kiểm tra TYPE của beacon
     *
     * @param uuid
     * @param major
     * @param minor
     * @param username
     * @return type của beacon
     */
    @RequestMapping(value = "getBeacon/{uuid}/{major}/{minor}/{username}")
    public Object getBeacon(@PathVariable String uuid,
            @PathVariable String major, @PathVariable String minor, @PathVariable String username) {

        Map<String, Object> result = new HashMap<>();
        System.out.println("Get Beacon information: " + uuid + " - " + major + " - "
                + minor);
        int iMajor = Integer.parseInt(major);
        int iMinor = Integer.parseInt(minor);

        Beacon beaconEntity = beaconRepos.getBeacon(uuid, iMajor, iMinor);
        if (beaconEntity != null) {
            BeaconDTO dto = BeaconDTO.convertFromEntity(beaconEntity);
            result.put("type ", dto.getType().getName());
            System.out.println("Get beacon info - type " + dto.getType());
            if (dto.getType() == BeaconType.BEACON_PAYMENT) {
                result.put("info ", this.triggerBeaconPayment(dto.getStationId(), username));
            } else if (dto.getType() == BeaconType.BEACON_RESULT) {
                result.put("laneId ", dto.getLaneId());
            }
        } else {
            result.put("type ", BeaconType.BEACON_OTHER.getName());
            System.out.println("Get beacon info - type OTHER");
        }
        return result;
    }

    /**
     * Tạo beacon mới
     *
     * @param beacon Beacon entitiy
     * @return kết quả thực hiện
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody Beacon beacon) {

        boolean isSuccessful = false;

        BeaconDTO dto = beaconService.insert(beacon);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    /**
     * Enable/Disable beacon
     *
     * @param beacon Beacon entity
     * @return kết quả thực hiện
     */
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
     *
     * @param stationId id của trạm
     * @param username
     * @return
     */
    @RequestMapping(value = "/payment/{stationId}/{username}", method = RequestMethod.GET)
    public Object triggerBeaconPayment(@PathVariable int stationId, @PathVariable String username) {

        System.out.println("Triggered Beacon Payment");
        TransactionDetailDTO transaction;
        Account account = accountRepos.findByUsername(username);
        int vehicleId = account.getVehicle().getId();
            transaction = transactionService.getCapturedTransactionForMobile(vehicleId, stationId);

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
     * @param transactionId Id của giao dich
     * @return true if FINISH Transaction. If not, it is false
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

            // Cập nhật trạng thái thành hoàn thành
            transDTO = transactionService.updateTransactionStatus(transactionId, newStatus);

            System.out.println(
                    "   + Update trans (2) lane [" + transDTO.getLaneId() + "] and status [" + transDTO.getStatus() + "]");
            return "true";
        }

        System.out.println("Transaction is wrong status: " + transDTO.getStatus());
        return "false";
    }

    /**
     * active beacon
     * @param beacon
     * @return
     */
    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public String active(@RequestBody Beacon beacon) {

        boolean isSuccessful = beaconService.active(beacon);

        return (isSuccessful)? "success" : "fail";
    }

    /**
     * deactive beacon
     * @param beacon
     * @return
     */
    @RequestMapping(value = "/deactive", method = RequestMethod.POST)
    public String deactive(@RequestBody Beacon beacon) {

        boolean isSuccessful = beaconService.deactive(beacon);

        return (isSuccessful)? "success" : "fail";
    }
}
