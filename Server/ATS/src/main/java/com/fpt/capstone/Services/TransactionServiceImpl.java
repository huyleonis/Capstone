package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Entities.Price;
import com.fpt.capstone.Entities.Transaction;
import com.fpt.capstone.Repositories.AccountRepos;
import com.fpt.capstone.Repositories.PriceRepos;
import com.fpt.capstone.Repositories.StationRepos;
import com.fpt.capstone.Repositories.TransactionRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepos transactionRepos;

    @Autowired
    private PriceRepos priceRepos;

    @Autowired
    private StationRepos stationRepos;

    @Autowired
    private AccountRepos accountRepos;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public TransactionDTO insertManualTransaction(String licensePlate, int laneId) {

        String id = new Date().getTime() + "";
        Date now = new Date();
        int stationId = stationRepos.getStationIdOfLane(laneId);
        Price price = priceRepos.findPriceByStationIdAndLicensePlate(stationId, licensePlate);

        int transaction = transactionRepos.insertManualTransaction(licensePlate, laneId, id, now, price.getId());
        if (transaction > 0) {
            Transaction transaction1 = transactionRepos.findById(id);
            if (transaction1 != null) {
                TransactionDTO dto = TransactionDTO.convertFromEntity(transaction1);
                return dto;
            }
        }
        return null;
    }

    @Override
    public TransactionDTO insertAutoTransaction(String username, int stationId) {

        String id = new Date().getTime() + "";
        Date now = new Date();
        String licensePlate = accountRepos.getLicensePlateOfAccount(username);
        Price price = priceRepos.findPriceByStationIdAndLicensePlate(stationId, licensePlate);

        int transaction = transactionRepos.insertAutoTransaction(username, stationId, id, now, price.getId());
        if (transaction > 0) {
            Transaction transaction2 = transactionRepos.findById(id);
            if (transaction2 != null) {
                TransactionDTO dto = TransactionDTO.convertFromEntity(transaction2);
                return dto;
            }
        }
        return null;
    }

    @Override
    public TransactionDTO updateTransactionStatus(String id, String status) {
        int transaction = transactionRepos.updateTransaction(id, status);

        if (transaction > 0) {
            Transaction transaction1 = transactionRepos.findById(id);
            if (transaction1 != null) {
                TransactionDTO dto = TransactionDTO.convertFromEntity(transaction1);
                //dto.setStatus(status);
                return dto;
            }
        }
        return null;
    }

    @Override
    public TransactionDTO getById(String id) {
        return TransactionDTO.convertFromEntity(transactionRepos.findById(id));
    }
    
    @Override
    public TransactionDetailDTO getDetailById(String id) {
        return TransactionDetailDTO.covertFromEntity(transactionRepos.findById(id));
    }

    @Override
    public TransactionDTO updateTransactionLane(String id, int laneId) {
        int transaction = transactionRepos.updateTransaction(id, laneId);
        if (transaction > 0) {
            Transaction transaction1 = transactionRepos.findById(id);
            if (transaction1 != null) {
                TransactionDTO dto = TransactionDTO.convertFromEntity(transaction1);
                //dto.setLaneId(laneId);
                return dto;
            }
        }
        return null;
    }

    @Override
    public List<TransactionDTO> getHistoryTransaction(String username, Date fromDate, Date toDate) {
//        fromDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        toDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Transaction> list = transactionRepos.getHistoryTransaction(username, fromDate, toDate);
        List<TransactionDTO> result = new ArrayList<>();
        for(Transaction tran : list){
            TransactionDTO dto = TransactionDTO.convertFromEntity(tran);
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<TransactionDTO> getTransactionsForStaff(int laneId, String status) {
        List<Transaction> list = transactionRepos.getTransactionForStaff(laneId, status);
        List<TransactionDTO> result = new ArrayList<>();
        for (Transaction tran : list) {
            TransactionDTO dto = TransactionDTO.convertFromEntity(tran);
            result.add(dto);
        }
        return result;
    }

//    @Override
//    public TransactionDTO finishTransaction(String id) {
//        int transaction = transactionRepos.finishTransaction(id);
//        if(transaction >0){
//            Transaction transaction1 = transactionRepos.findById(id);
//            if (transaction1 != null){
//                TransactionDTO dto = TransactionDTO.convertFromEntity(transaction1);
//                return dto;
//            }
//        }
//        return null;
//    }


    @Override
    public List<TransactionDetailDTO> getAllDetail() {

        List<TransactionDetailDTO> dtos = new ArrayList<>();

        List<Transaction> transactions = transactionRepos.findAll();

        for (Transaction transaction: transactions) {
            dtos.add(TransactionDetailDTO.covertFromEntity(transaction));
        }

        return dtos;
    }
}
