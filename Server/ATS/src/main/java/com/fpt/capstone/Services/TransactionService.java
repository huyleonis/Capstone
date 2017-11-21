package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Utils.TransactionStatus;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public interface TransactionService {


    TransactionDTO insertManualTransaction(String licensePlate, int laneId);

    TransactionDTO insertAutoTransaction(String username, int stationId);
    
    TransactionDTO createCapturedTransaction(String plate, String photo, Date createdTime, int stationId) throws Exception;

    TransactionDTO updateTransactionStatus(String id, TransactionStatus status);

    TransactionDTO getById(String id);
    
    TransactionDetailDTO getDetailById(String id);

    List<TransactionDTO> getTransactionsForStaff(String status);

    TransactionDTO updateTransactionLane(String id, int laneId);
    
    TransactionDTO updateTransactionType(String id, int type);

    List<TransactionDetailDTO> getHistoryTransaction(String username, Date fromDate, Date toDate);

    List<TransactionDetailDTO> getAllDetail();

    List<TransactionDetailDTO> getDetailByVehicleIdIn24Hours(int vehicleId);
    
    TransactionDetailDTO getCapturedTransaction(int vehicleId, int stationId, boolean updateStatus);

}