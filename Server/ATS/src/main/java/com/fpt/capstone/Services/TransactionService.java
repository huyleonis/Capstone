package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.TransactionDTO;

import java.util.List;

public interface TransactionService {


    TransactionDTO insertManualTransaction(String licensePlate, int laneId);

    TransactionDTO insertAutoTransaction(String username, int stationId);

    TransactionDTO updateTransactionStatus(String id, String status);

    TransactionDTO getById(String id);

    List<TransactionDTO> getTransactionsForStaff(String status);

    TransactionDTO updateTransactionLane(String id, int laneId);

}
