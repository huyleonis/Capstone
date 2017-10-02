package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Entities.Transaction;

public interface TransactionService {
    TransactionDTO findByLicensePlate(String license_plate, int id);

    Transaction insertTransaction(TransactionDTO transactionDTO);
}
