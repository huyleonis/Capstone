package com.fpt.capstone.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Services.AccountService;
import com.fpt.capstone.Services.TransactionService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView viewAccount() {
		ModelAndView m = new ModelAndView("transaction");
		return m;
	}

	/**
	 * Tạo transaction thu phí thủ công khi staff enter biển số xe
	 * 
	 * @param licensePlate
	 *            biển số xe do staff nhập vào
	 * @param laneId
	 *            mã làn của staff thực hiện thu phí
	 * @return trả về thông tin transaction vừa được tạo
	 */
	@RequestMapping(value = "makeManualPayment/{licensePlate}/{laneId}")
	@ResponseStatus(HttpStatus.OK)
	public TransactionDTO insertTransactionPaymentManual(@PathVariable String licensePlate, @PathVariable int laneId) {
		return transactionService.insertManualTransaction(licensePlate, laneId);
	}

	/**
	 * Cập nhật trạng thái thu phí thủ công khi staff đã nhận tiền
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "receivedMoney/{id}")
	@ResponseStatus(HttpStatus.OK)
	public TransactionDTO updateTransaction(@PathVariable String id) {
		return transactionService.updateTransactionStatus(id, "Đang xử lý");
	}

	/**
	 * Cập nhật trạng thái thu phí thủ công/tự động khi staff đã mở cổng cho xe qua
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "finish/{id}")
	@ResponseStatus(HttpStatus.OK)
	public TransactionDTO finishTransaction(@PathVariable String id) {
		return transactionService.updateTransactionStatus(id, "Hoàn thành giao dịch");
	}

	/**
	 * Tạo transaction thu phí tự động khi tài xế xác nhận đồng ý thanh toán
	 * 
	 * @param username
	 *            tài khoản của tài xế
	 * @param stationId
	 *            mã trạm do android app gửi lên
	 * @return trả về thông tin transaction vừa dc tạo
	 */
	@RequestMapping(value = "makePayment/{username}/{stationId}")
	@ResponseStatus(HttpStatus.OK)
	public TransactionDTO insertTransactionPayment(@PathVariable String username, @PathVariable int stationId) {
		System.out.println("Request Make payment from Driver in station " + stationId);

		// Khởi tạo transaction, status: Chưa thanh toán
		TransactionDTO transDTO = transactionService.insertAutoTransaction(username, stationId);

		System.out.println("   + create transaction [" + transDTO.getId() + "] success with status ["
				+ transDTO.getStatus() + "]");

		// Gọi module paypal
		String result = accountService.makePayment(username, stationId);
		if (result.equals("")) {
			transDTO = transactionService.updateTransactionStatus(transDTO.getId(), "Thành công");
		} else {
			transDTO = transactionService.updateTransactionStatus(transDTO.getId(), "Thất bại");
			transDTO.setFailReason(result);
		}

		// status:

		System.out.println("   + update transaction success with status [" + transDTO.getStatus() + "]");
		return transDTO;
	}

	/**
	 * Khi xe nhận tín hiệu beacon 2, gửi yêu cầu check result. Server cập nhật
	 * trạng
	 * 
	 * @param laneId
	 * @param idTransaction
	 * @return
	 */
	@RequestMapping(value = "checkResult/{laneId}/{idTransaction}")
	@ResponseStatus(HttpStatus.OK)
	public TransactionDTO checkResult(@PathVariable String laneId, @PathVariable String idTransaction) {

		System.out.println("Request Check result from Driver in lane with ID " + laneId);

		int iLaneId = Integer.parseInt(laneId);

		TransactionDTO transDTO = transactionService.getById(idTransaction);
		if (transDTO.getStatus().endsWith("Chờ xử lý")) {
			return transDTO;
		}

		String status = transDTO.getStatus() + " - Chờ xử lý";

		// Cập nhật làn xe vô
		transDTO = transactionService.updateTransactionLane(idTransaction, iLaneId);

		System.out.println(
				"   + Update trans (1) lane [" + transDTO.getLaneId() + "] and status [" + transDTO.getStatus() + "]");

		// Cập nhật trạng thái thành chờ xử lý
		transDTO = transactionService.updateTransactionStatus(idTransaction, status);

		System.out.println(
				"   + Update trans (2) lane [" + transDTO.getLaneId() + "] and status [" + transDTO.getStatus() + "]");
		return transDTO;
	}
    /**
     * Lấy transaction detail theo vehicle id trong vòng 24h
     * @param vehicleId
     * @return
     */
    @RequestMapping(value = "/getDetailByVehicleIdIn24Hours/{vehicleId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDetailByVehicleIdIn24Hours(@PathVariable String vehicleId) {
        System.out.println("GET DETAILS BY VEHICLE ID IN 24 HOURS");
        List<TransactionDetailDTO> dtos =
                transactionServiceImpl.getDetailByVehicleIdIn24Hours(Integer.parseInt(vehicleId));

        Map<String, Object> map = new HashMap<>();
        map.put("TransactionDetails", dtos);

        return map;
    }
	/**
	 * Staff gửi yêu cầu lấy danh sách các xe đang vào lane mình trực Server gửi
	 * danh sách các xe này và cập nhật trạng thái thành Đang xử lý
	 * 
	 * @param laneId
	 * @return
	 */
	@RequestMapping(value = "getResult")
	@ResponseStatus(HttpStatus.OK)
	public List<TransactionDTO> getListResultTransactionByLane() {
		List<TransactionDTO> result = transactionService.getTransactionsForStaff("Chưa thanh toán");

		for (TransactionDTO tran : result) {
			if (!tran.getStatus().endsWith("Đang xử lý")) {
				String status = tran.getStatus().replace("Chờ xử lý", "Đang xử lý");
				transactionService.updateTransactionStatus(tran.getId(), status);
			}
		}
		return result;
	}

	/**
	 * Lấy transaction detail theo id
	 * 
	 * @param transactionId
	 * @return
	 */
	@RequestMapping(value = "getDetail/{transactionId}")
	@ResponseStatus(HttpStatus.OK)
	public TransactionDetailDTO getById(@PathVariable String transactionId) {
		TransactionDetailDTO dto = transactionService.getDetailById(transactionId);

		return dto;
	}

	/**
	 * Lấy transaction detail theo username
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/getDetailByAccount/{username}", method = RequestMethod.GET)
	public Map<String, Object> getDetailByAccount(@PathVariable String username) {

		List<TransactionDetailDTO> dtos = transactionService.getDetailsByAccountId(username);

		Map<String, Object> map = new HashMap<>();
		map.put("TransactionDetails", dtos);

		return map;
	}

	/**
	 * Lấy list transaction detail
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getDetail", method = RequestMethod.GET)
	public String getAllTransDetail() throws JsonProcessingException {

		List<TransactionDetailDTO> dtos = transactionService.getAllDetail();

		return new Gson().toJson(dtos);
	}

	/**
	 * Khi xe qua trạm nhưng thanh toán thất bại, tài xế yêu cầu thanh toán lại thì
	 * thực hiện
	 * 
	 * @param transactionId
	 * @return
	 */
	@RequestMapping(value = "updateProcessingTransaction/{transactionId}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, String> requestPaymentLater(@PathVariable String transactionId) {
		Map<String, String> map = new HashMap<>();

		String status = "";

		TransactionDetailDTO transDTO = transactionService.getDetailById(transactionId);

		System.out.println(
				"   + get transaction [" + transDTO.getId() + "] success with status [" + transDTO.getStatus() + "]");

		// Gọi module paypal
		String result = accountService.makePayment(transDTO.getUsername(), transDTO.getStationId());
		if (result.equals("")) {
			transactionService.updateTransactionStatus(transDTO.getId(), "Kết thúc");
			status = "Kết Thúc";
		} else {
			transactionService.updateTransactionStatus(transDTO.getId(), "Thất bại");
			status = "Kết Thúc";
		}

		// status:

		System.out.println("   + update transaction success with status: " + status);

		map.put("status", status);

		return map;
	}
}