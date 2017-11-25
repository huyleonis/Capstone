package hackathon.fpt.ktk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import hackathon.fpt.ktk.dto.AccountDTO;
import hackathon.fpt.ktk.dto.BeaconDTO;
import hackathon.fpt.ktk.dto.TransactionDTO;
import hackathon.fpt.ktk.dto.TransactionDetailDTO;
import hackathon.fpt.ktk.entity.Beacon;
import hackathon.fpt.ktk.util.BeaconType;
import hackathon.fpt.ktk.util.TransactionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/beacon")
public class BeaconController extends AbstractController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewBeacon() {
        ModelAndView m = new ModelAndView("beacon");
        return m;
    }

    @RequestMapping(value = "getBeacon/{uuid}/{major}/{minor}/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getBeacon(@PathVariable String uuid,
                                         @PathVariable String major,
                                         @PathVariable String minor,
                                         @PathVariable String username) {

        Map<String, Object> result = new HashMap<>();

        System.out.println("Get Beacon information: " + uuid + " - " + major + " - "
                + minor);
        int iMajor = Integer.parseInt(major);
        int iMinor = Integer.parseInt(minor);

        BeaconDTO dto = beaconService.getBeaconByUuidAndMajorAndMinor(uuid, iMajor, iMinor);

        if (dto != null) {
            result.put("type", dto.getType().getName());
            System.out.println("Get beacon info - type " + dto.getType());

            if (dto.getType() == BeaconType.BEACON_PAYMENT) {
                result.put("info", this.triggerBeaconPayment(dto.getStationId(), username));
            } else if (dto.getType() == BeaconType.BEACON_RESULT) {
                result.put("laneId", dto.getLaneId());
            }

        } else {
            result.put("type", BeaconType.BEACON_OTHER.getName());
            System.out.println("Get beacon info - type OTHER");
        }

        return result;
    }

    /**
     * Khi xe nhận tín hiệu beacon 1, kiểm tra xem camera có chụp dc hình tương
     * ứng thì trả về transaction ứng với hình, không thì trả về giá và hỏi
     * người dùng có tạo transaction hay không.
     *
     * @param stationId
     * @param username
     * @return
     */
    @RequestMapping(value = "/payment/{stationId}/{username}", method = RequestMethod.GET)
    public Object triggerBeaconPayment(@PathVariable int stationId,
                                       @PathVariable String username) {

        System.out.println("Triggered Beacon Payment");

        AccountDTO accountDTO = accountService.getAccountByUsername(username);
        int vehicleId = accountDTO.getVehicleId();

        TransactionDetailDTO transaction = transactionService
                .getCapturedTransaction(vehicleId, stationId, true);

        if (transaction == null) {
            System.out.println("Not found captured payment created by camera");
            return priceService.getPriceByStationIdAndLicensePlate(stationId, accountDTO.getLicensePlate());
        }

        System.out.println("Found captured payment created by camera");
        return transaction;
    }

    /**
     * Khi xe nhận tín hiệu beacon 2, gửi yêu cầu check result. Server cập nhật
     * trạng
     *
     * @param laneId
     * @param transactionId
     * @return
     */
    @RequestMapping(value = "/result/{laneId}/{transactionId}", method = RequestMethod.GET)
    public String triggerBeaconResult(@PathVariable int laneId,
                                      @PathVariable String transactionId) {
        System.out.println("Request Check result from Driver in lane with ID " + laneId);

        TransactionDTO transDTO = transactionService.getTransactionById(transactionId);

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
     * lay danh sach beacon
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAllBeacon() throws JsonProcessingException {

        List<BeaconDTO> dtos = beaconService.getAllBeacon();

        if (dtos != null) {
            return new Gson().toJson(dtos);
        }

        return "Khong co du lieu";
    }

    /**
     * tao beacon moi
     * @param beacon
     * @return
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
     * cap nhat beacon
     * @param beacon
     * @return
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
}
