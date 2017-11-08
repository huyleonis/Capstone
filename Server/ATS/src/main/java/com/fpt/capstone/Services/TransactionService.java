package com.fpt.capstone.Services;

import java.util.Date;
import java.util.List;

import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;

public interface TransactionService {


    TransactionDTO insertManualTransaction(String licensePlate, int laneId);

    TransactionDTO insertAutoTransaction(String username, int stationId);

    TransactionDTO updateTransactionStatus(String id, String status);

    TransactionDTO getById(String id);
    
    TransactionDetailDTO getDetailById(String id);

    List<TransactionDTO> getTransactionsForStaff(String status, int min);

    TransactionDTO updateTransactionLane(String id, int laneId);

    List<TransactionDTO> getHistoryTransaction(String username, Date fromDate, Date toDate);

    List<TransactionDetailDTO> getAllDetail();

    List<TransactionDetailDTO> getDetailsByAccountId(String username);

    List<TransactionDTO> getTransactionByDate(String date, String status);
    
    void deleteTransaction(String id);
}