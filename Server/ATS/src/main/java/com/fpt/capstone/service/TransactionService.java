package com.fpt.capstone.service;

import com.fpt.capstone.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
//    TransactionDTO findByLicensePlate(String licensePlate, int id);

    TransactionDTO insertManualTransaction(String licensePlate, int laneId);

    TransactionDTO insertAutoTransaction(String username, int stationId);

    TransactionDTO updateTransactionStatus(String id, String status);

    TransactionDTO getById(String id);

    List<TransactionDTO> getTransactionsForStaff(int laneId, String status);

    TransactionDTO updateTransactionLane(String id, int laneId);

}
