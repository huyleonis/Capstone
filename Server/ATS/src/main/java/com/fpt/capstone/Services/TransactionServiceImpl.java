package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.TransactionDTO;
import com.fpt.capstone.Dtos.TransactionDetailDTO;
import com.fpt.capstone.Entities.Account;
import com.fpt.capstone.Entities.Price;
import com.fpt.capstone.Entities.Transaction;
import com.fpt.capstone.Entities.Vehicle;
import com.fpt.capstone.Repositories.AccountRepos;
import com.fpt.capstone.Repositories.PriceRepos;
import com.fpt.capstone.Repositories.StationRepos;
import com.fpt.capstone.Repositories.TransactionRepos;
import com.fpt.capstone.Repositories.VehicleRepos;
import com.fpt.capstone.Utils.TransactionStatus;
import com.fpt.capstone.Utils.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @Autowired
    private VehicleRepos vehicleRepos;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TransactionDTO insertManualTransaction(String licensePlate, int laneId) {

        String id = new Date().getTime() + "";
        Date now = new Date();
        int stationId = stationRepos.getStationIdOfLane(laneId);
        Price price = priceRepos.findPriceByStationIdAndLicensePlate(stationId, licensePlate);
        String status = TransactionStatus.TRANS_NOTPAY.toString();
        int vehicleId = vehicleRepos.findByLicensePlate(licensePlate).getId();

        int transaction = transactionRepos.insertManualTransaction(id, stationId,
                now, status, price.getPrice(), TransactionType.MANUAL.getType(), vehicleId);
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
        Account account = accountRepos.findByUsername(username);
        int vehicleId = account.getVehicle().getId();
        Price price = priceRepos.findPriceByStationIdAndLicensePlate(stationId, account.getVehicle().getLicensePlate());

        int transaction = transactionRepos.insertAutoTransaction(id, stationId, now,
                TransactionStatus.TRANS_PENDING.getName(), price.getPrice(),
                TransactionType.AUTOMATION.getType(), vehicleId);

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
    public TransactionDTO updateTransactionStatus(String id, TransactionStatus status) {
        int transaction = transactionRepos.updateTransaction(id, status.getName());

        if (transaction > 0) {
            Transaction transaction1 = transactionRepos.findById(id);
            if (transaction1 != null) {
                TransactionDTO dto = TransactionDTO.convertFromEntity(transaction1);
                // dto.setStatus(status);
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
                // dto.setLaneId(laneId);
                return dto;
            }
        }
        return null;
    }

    @Override
    public List<TransactionDTO> getTransactionsForStaff(String status) {
        List<Transaction> list = transactionRepos.getTransactionForStaff(status);
        List<TransactionDTO> result = new ArrayList<>();
        for (Transaction tran : list) {
            TransactionDTO dto = TransactionDTO.convertFromEntity(tran);
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<TransactionDetailDTO> getHistoryTransaction(String username, Date fromDate, Date toDate) {
        // fromDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // toDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Account account = accountRepos.findByUsername(username);
        List<Transaction> list = transactionRepos.getHistoryTransaction(account.getVehicle().getId(), fromDate, toDate);
        List<TransactionDetailDTO> result = new ArrayList<>();
        for (Transaction tran : list) {
            TransactionDetailDTO dto = TransactionDetailDTO.covertFromEntity(tran);
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<TransactionDetailDTO> getAllDetail() {

        List<TransactionDetailDTO> dtos = new ArrayList<>();

        List<Transaction> transactions = transactionRepos.findAll();

        for (Transaction transaction : transactions) {
            dtos.add(TransactionDetailDTO.covertFromEntity(transaction));
        }

        return dtos;
    }

    @Override
    public List<TransactionDetailDTO> getDetailByVehicleIdIn24Hours(int vehicleId) {

        List<Transaction> transactions = transactionRepos.findByVehicleIdIn24Hours(vehicleId);

        List<TransactionDetailDTO> dtos = new ArrayList<>();

        for (Transaction transaction : transactions) {
            dtos.add(TransactionDetailDTO.covertFromEntity(transaction));
        }

        return dtos;
    }

    @Override
    public TransactionDetailDTO getCapturedTransactionForMobile(int vehicleId, int stationId) {
        TransactionDetailDTO result = null;

        Transaction tran = transactionRepos.getCapturedTransactionForMobile(vehicleId, stationId);

        if (tran != null) {

            Date now = new Date();
            Date tranDate = tran.getDateTime();
            long diff = Math.abs(now.getTime() - tranDate.getTime());
            long diffMins = diff / (60 * 1000);

            //Nếu transaction được tạo trong thời gian dưới 30 phút
            // thì đây là transaction mới tạo, dùng để xử lý
            if (diffMins < 30) {
                transactionRepos.updateTransaction(tran.getId(), TransactionStatus.TRANS_NOTPAY.getName());
                result = TransactionDetailDTO.covertFromEntity(tran);
            }
        }
        return result;
    }

    @Override
    public TransactionDetailDTO getCapturedTransactionForDesktop(int vehicleId, int stationId) {
        TransactionDetailDTO result = null;

        Transaction tran = transactionRepos.getCapturedTransactionForMobile(vehicleId, stationId);

        if (tran != null) {

            Date now = new Date();
            Date tranDate = tran.getDateTime();
            long diff = Math.abs(now.getTime() - tranDate.getTime());
            long diffMins = diff / (60 * 1000);

            //Nếu transaction được tạo trong thời gian dưới 30 phút
            // thì đây là transaction mới tạo, dùng để xử lý
            if (diffMins < 30) {  
                result = TransactionDetailDTO.covertFromEntity(tran);
            }
        }
        return result;
    }

    @Override
    public TransactionDTO createCapturedTransaction(String plate, String photo, Date createdTime, int stationId) throws Exception {
        String id = createdTime.getTime() + "";

        Vehicle vehicle = vehicleRepos.findByLicensePlate(plate);

        if (vehicle != null) { //Nếu số xe có trong db
            Price price = priceRepos.findPriceByStationIdAndLicensePlate(stationId, plate);
            int result = transactionRepos.createCaptureTransaction(id, stationId,
                    createdTime, TransactionStatus.TRANS_INITIAL.getName(),
                    price.getPrice(), photo, vehicle.getId());

            if (result > 0) {
                Transaction transaction = transactionRepos.findById(id);
                if (transaction != null) {
                    TransactionDTO dto = TransactionDTO.convertFromEntity(transaction);
                    return dto;
                }
            }
        } else { //nếu số xe ko có trong db
            throw new Exception("This license plate does not exist");
        }
        return null;
    }

    @Override
    public TransactionDTO updateTransactionType(String id, int type) {
        int transaction = transactionRepos.updateTransactionType(id, type);
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
    public List<TransactionDetailDTO> getAllReportDetail() {

        List<TransactionDetailDTO> dtos = new ArrayList<>();

        List<Transaction> transactions = transactionRepos.findByStatus(TransactionStatus.TRANS_ERROR.getName());

        for (Transaction transaction : transactions) {
            dtos.add(TransactionDetailDTO.covertFromEntity(transaction));
        }

        return dtos;
    }

    @Override
    public TransactionDTO updateReport(Transaction transaction) {
        TransactionDTO dto = null;

        try {
            Vehicle vehicle = vehicleRepos.findByLicensePlate(transaction.getVehicle().getLicensePlate());
            if (vehicle != null) {

                Transaction existingTransaction = transactionRepos.findOne(transaction.getId());

                existingTransaction.setVehicle(vehicle);
                existingTransaction.setStatus(TransactionStatus.TRANS_NOTPAY.getName());
                ;

                if (existingTransaction != null) {
                    Transaction processedTransaction = transactionRepos.save(existingTransaction);
                    dto = TransactionDTO.convertFromEntity(processedTransaction);
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (Exception e) {
        }

        return dto;
    }

    @Override
    public boolean deleteReport(String id) {

        try {
            Transaction transaction = transactionRepos.findOne(id);
            if (transaction != null) {
                transactionRepos.delete(id);
                return true;
            }
        } catch (Exception e) {

        }

        return false;
    }

    @Override
    public List<TransactionDTO> getTransByLicPlateAndTime(String licensePlate, String createdTime) {
        List<TransactionDTO> dtos = new ArrayList<>();

        Vehicle vehicle = vehicleRepos.findByLicensePlate(licensePlate);
        if (vehicle != null) {
            List<Transaction> transactions = transactionRepos.findByVehicleIdAndTime(vehicle.getId(), createdTime);

            if (transactions != null) {

                for (Transaction transaction : transactions) {
                    TransactionDTO dto = TransactionDTO.convertFromEntity(transaction);
                    dtos.add(dto);
                }
            }
        }

        return dtos;
    }

    @Override
    public boolean resolveReport(String transId, String transIdError) {
        Transaction transError = transactionRepos.findOne(transIdError);

        Transaction resolveTrans = transactionRepos.findOne(transId);
        resolveTrans.setPhoto(transError.getPhoto());

        Transaction processedTrans = transactionRepos.save(resolveTrans);
        if (processedTrans != null) {
            transactionRepos.delete(transIdError);
            return true;
        }

        return false;
    }

    @Override
    public boolean confirmReport(String transId, String licensePlate) {
        Vehicle vehicle = vehicleRepos.findByLicensePlate(licensePlate);
        if (vehicle != null) {
            Transaction transaction = transactionRepos.findOne(transId);

            transaction.setVehicle(vehicle);
            transaction.setStatus(TransactionStatus.TRANS_NOTPAY.getName());

            Transaction processedTrans = transactionRepos.save(transaction);
            if (processedTrans != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean updatePhoto(String transId, String photo) {
        Transaction resolveTrans = transactionRepos.findOne(transId);
        resolveTrans.setPhoto(photo);

        Transaction processedTrans = transactionRepos.save(resolveTrans);
        if (processedTrans != null) {
            return true;
        }

        return false;
    }
}
