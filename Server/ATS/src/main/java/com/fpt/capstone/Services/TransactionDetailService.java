package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.TransactionDetailDTO;

public interface TransactionDetailService {

    TransactionDetailDTO findTransactionById(String transactionId);
}
