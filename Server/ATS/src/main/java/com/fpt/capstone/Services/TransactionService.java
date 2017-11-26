package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Entities.Transaction;
import com.fpt.capstone.Utils.TransactionStatus;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public interface TransactionService {

    TransactionDTO insertManualTransaction(String licensePlate, int laneId);

    TransactionDTO insertAutoTransaction(String username, int stationId);

    TransactionDTO createCapturedTransaction(String plate, String photo, Date createdTime, int stationId)
            throws Exception;

    TransactionDTO updateTransactionStatus(String id, TransactionStatus status);

    TransactionDTO getById(String id);

    TransactionDetailDTO getDetailById(String id);

    List<TransactionDTO> getTransactionsForStaff(String status);

    TransactionDTO updateTransactionType(String id, int type);

    TransactionDTO updateTransactionLane(String id, int laneId);

    List<TransactionDetailDTO> getHistoryTransaction(String username, Date fromDate, Date toDate);

    List<TransactionDetailDTO> getDetailByVehicleIdIn24Hours(int vehicleId);

    TransactionDetailDTO getCapturedTransaction(int vehicleId, int stationId, boolean updateStatus);
    
    List<TransactionDetailDTO> getAllReportDetail();

    List<TransactionDetailDTO> getAllDetail();

    TransactionDTO updateReport(Transaction transaction);

    boolean delete(String id);

    List<TransactionDTO> getTransByLicPlateAndTime(String licensePlate, String createdTime);

    boolean resolveReport(String transId, String transIdError);

    boolean confirmReport(String transId, String licensePlate);

    boolean updatePhoto(String transId, String photo);
}
