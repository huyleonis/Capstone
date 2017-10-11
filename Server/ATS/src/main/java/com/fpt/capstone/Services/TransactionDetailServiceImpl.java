package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.PriceDTO;
import com.fpt.capstone.Dtos.StationDTO;
import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Repositories.PriceRepos;
import com.fpt.capstone.Repositories.StationRepos;
import com.fpt.capstone.Repositories.TransactionRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

    @Autowired
    private TransactionRepos transactionRepos;

    @Autowired
    private StationRepos stationRepos;

    @Autowired
    private PriceRepos priceRepos;

    @Override
    public TransactionDetailDTO findTransactionById(String transactionId) {

        TransactionDTO transactionDTO = TransactionDTO.convertFromEntity(transactionRepos.findById(transactionId));

        StationDTO stationDTO = StationDTO.convertFromEntity(stationRepos.findById(transactionDTO.getStationId()));

        PriceDTO priceDTO = PriceDTO.convertFromEntity(priceRepos.findById(transactionDTO.getPriceId()));

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
