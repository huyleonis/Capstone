package com.fpt.capstone.service;

import com.fpt.capstone.dto.PriceDTO;
import com.fpt.capstone.dto.StationDTO;
import com.fpt.capstone.dto.TransactionDTO;
import com.fpt.capstone.dto.TransactionDetailDTO;
import com.fpt.capstone.repository.PriceRepos;
import com.fpt.capstone.repository.StationRepos;
import com.fpt.capstone.repository.TransactionRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailServiceImpl extends AbstractServiceImpl implements TransactionDetailService {

    @Override
    public TransactionDetailDTO findTransactionById(String transactionId) {

        TransactionDTO transactionDTO = TransactionDTO.convertFromEntity(transactionRepos.findById(transactionId));

        StationDTO stationDTO = StationDTO.convertFromEntity(stationRepos.findById(transactionDTO.getStationId()));

        PriceDTO priceDTO = PriceDTO.convertFromEntity(priceRepos.findOne(transactionDTO.getPriceId()));

        TransactionDetailDTO transactionDetailDTO = new TransactionDetailDTO();
        transactionDetailDTO.setStationName(stationDTO.getName());
        transactionDetailDTO.setPrice(priceDTO.getPrice());
        transactionDetailDTO.setTransactionId(transactionDTO.getTransactionId());
        transactionDetailDTO.setDateTime(transactionDTO.getDateTime());
        transactionDetailDTO.setStationId(transactionDTO.getStationId());
        transactionDetailDTO.setStatus(transactionDTO.getStatus());
        transactionDetailDTO.setZone(stationDTO.getZone());

        return transactionDetailDTO;
    }
}
