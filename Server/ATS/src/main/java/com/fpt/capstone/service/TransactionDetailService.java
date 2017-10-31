package com.fpt.capstone.service;

import com.fpt.capstone.dto.TransactionDetailDTO;

public interface TransactionDetailService {

    TransactionDetailDTO findTransactionById(String transactionId);
}
