package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public interface TransactionService {
//    TransactionDTO findByLicensePlate(String licensePlate, int id);

    TransactionDTO insertManualTransaction(String licensePlate, int laneId);

    TransactionDTO insertAutoTransaction(String username, int stationId);

    TransactionDTO updateTransactionStatus(String id, String status);

    TransactionDTO getById(String id);
    
    TransactionDetailDTO getDetailById(String id);

    List<TransactionDTO> getTransactionsForStaff(int laneId, String status);

    TransactionDTO updateTransactionLane(String id, int laneId);

    List<TransactionDTO> getHistoryTransaction(String username, Date fromDate, Date toDate);

}
