package hackathon.fpt.ktk.controller;

import hackathon.fpt.ktk.dto.TransactionDTO;
import hackathon.fpt.ktk.dto.TransactionDetailDTO;
import hackathon.fpt.ktk.dto.VehicleDTO;
import hackathon.fpt.ktk.util.TransactionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController extends AbstractController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewTransaction() {
        ModelAndView m = new ModelAndView("transaction");
        return m;
    }

    /**
     * Tạo transaction thu phí thủ công khi staff enter biển số xe
     *
     * @param licensePlate biển số xe do staff nhập vào
     * @param laneId mã làn của staff thực hiện thu phí
     * @return trả về thông tin transaction vừa được tạo
     */
    @RequestMapping(value = "makeManualPayment/{licensePlate}/{laneId}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO insertTransactionPaymentManual(@PathVariable String licensePlate,
                                                         @PathVariable int laneId) {
        return transactionService.insertManualTransaction(licensePlate, laneId);
    }

    /**
     * Cập nhật trạng thái thu phí thủ công/tự động khi staff đã mở cổng cho xe
     * qua
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "finish/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO finishTransaction(@PathVariable String id) {
        System.out.println("Finish transaction");
        TransactionDTO dto = transactionService
                .updateTransactionStatus(id, TransactionStatus.TRANS_FINISH);

        return dto;
    }

    /**
     * Lấy transaction detail theo id cho desktop
     *
     * @param licensePlate
     * @param stationId
     * @return
     */
    @RequestMapping(value = "getCapturedTransaction/{licensePlate}/{stationId}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDetailDTO getCapturedTransactionForDesktop(@PathVariable String licensePlate,
                                                                 @PathVariable int stationId) {

        VehicleDTO vehicleDTO = vehicleService.getVehicleByLicensePlate(licensePlate);

        TransactionDetailDTO dto = transactionService
                .getCapturedTransaction(vehicleDTO.getId(), stationId, false);

        return dto;
    }
}
