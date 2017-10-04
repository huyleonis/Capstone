package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Entities.Transaction;
import com.fpt.capstone.Repositories.TransactionRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepos transactionRepos;


    @Override
    public TransactionDTO findByLicensePlate(String license_plate, int id) {
        Transaction transaction = transactionRepos.findByLicensePlate(license_plate , id);
        if (transaction != null){
            TransactionDTO transactionDTO = TransactionDTO.convertFromEntity(transaction);
            return transactionDTO;
        } else {
            return null;
        }
    }

    @Override
    public Transaction insertTransaction(TransactionDTO transactionDTO) {

        return null;
    }




}
