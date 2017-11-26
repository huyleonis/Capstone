package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.TransactionDTO;
import hackathon.fpt.ktk.dto.TransactionDetailDTO;
import hackathon.fpt.ktk.util.TransactionStatus;

import java.util.Date;
import java.util.List;

public interface TransactionService {

    TransactionDTO insertManualTransaction(String licensePlate, int laneId);

    TransactionDTO updateTransactionStatus(String id, TransactionStatus status);

    TransactionDetailDTO getCapturedTransaction(int vehicleId, int stationId, boolean updateStatus);

    TransactionDTO getTransactionById(String transactionId);

    TransactionDTO updateTransactionLane(String id, int laneId);

    TransactionDTO createCapturedTransaction(String plate, String photo, Date createdTime, int stationId) throws Exception;

    TransactionDTO insertAutoTransaction(String username, int stationId);

    TransactionDetailDTO getDetailById(String id);

    List<TransactionDetailDTO> getDetailByVehicleIdIn24Hours(int vehicleId);

    List<TransactionDetailDTO> getAllDetail();

    List<TransactionDTO> getTransactionsForStaff(String status);

    List<TransactionDetailDTO> getHistoryTransaction(String username, Date fromDate, Date toDate);
    
      List<TransactionDetailDTO> getAllReportDetail();
}
