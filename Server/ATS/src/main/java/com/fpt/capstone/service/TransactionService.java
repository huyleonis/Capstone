package com.fpt.capstone.service;

<<<<<<< HEAD:Server/ATS/src/main/java/com/fpt/capstone/Services/TransactionService.java
import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
=======
import com.fpt.capstone.dto.TransactionDTO;
>>>>>>> origin/WebAdmin:Server/ATS/src/main/java/com/fpt/capstone/service/TransactionService.java

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public interface TransactionService {


    TransactionDTO insertManualTransaction(String licensePlate, int laneId);

    TransactionDTO insertAutoTransaction(String username, int stationId);

    TransactionDTO updateTransactionStatus(String id, String status);

    TransactionDTO getById(String id);
    
    TransactionDetailDTO getDetailById(String id);

    List<TransactionDTO> getTransactionsForStaff(String status);

    TransactionDTO updateTransactionLane(String id, int laneId);

    List<TransactionDTO> getHistoryTransaction(String username, Date fromDate, Date toDate);

    List<TransactionDetailDTO> getAllDetail();

    List<TransactionDetailDTO> getDetailsByAccountId(String username);

}