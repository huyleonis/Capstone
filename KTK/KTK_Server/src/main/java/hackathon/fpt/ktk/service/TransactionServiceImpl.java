package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.TransactionDTO;
import hackathon.fpt.ktk.dto.TransactionDetailDTO;
import hackathon.fpt.ktk.entity.*;
import hackathon.fpt.ktk.util.TransactionStatus;
import hackathon.fpt.ktk.util.TransactionType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl extends AbstractServiceImpl implements TransactionService {

    @Override
    public TransactionDTO insertManualTransaction(String licensePlate, int laneId) {
        String id = new Date().getTime() + "";
        Date createdTime = new Date();

        Lane lane = laneRepos.findOne(laneId);
        int stationId = lane.getStation().getId();

        Vehicle vehicle = vehicleRepos.findByLicensePlate(licensePlate);
        int vehicleTypeId = vehicle.getVehicleType().getId();

        Price price = priceRepos.findByStationIdAndTypeId(stationId, vehicleTypeId);
        String status = TransactionStatus.TRANS_NOTPAY.toString();

        Transaction transaction = new Transaction(id, vehicle, lane.getStation(), createdTime,
                status, price.getPrice(), null, TransactionType.MANUAL.getType(), null);
        Transaction processedTransaction = transactionRepos.save(transaction);

        if (processedTransaction != null) {
            Transaction returnTransaction = transactionRepos.findOne(id);
            TransactionDTO dto = TransactionDTO.convertFromEntity(returnTransaction);
            return dto;
        }
        return null;
    }

    @Override
    public TransactionDTO updateTransactionStatus(String id, TransactionStatus status) {

        Transaction existingTransaction = transactionRepos.findOne(id);
        if (existingTransaction != null) {

            existingTransaction.setStatus(status.getName());
            Transaction processedTransaction = transactionRepos.save(existingTransaction);

            if (processedTransaction != null) {
                Transaction returnTransaction = transactionRepos.findOne(id);
                TransactionDTO dto = TransactionDTO.convertFromEntity(returnTransaction);
                return dto;
            }
        }

        return null;
    }

    @Override
    public TransactionDetailDTO getCapturedTransaction(int vehicleId, int stationId, boolean updateStatus) {
        TransactionDetailDTO result = null;

        Transaction transaction = transactionRepos
                .findByVehicleIdAndStationId(vehicleId, stationId);

        if (transaction != null) {

            Date now = new Date();
            Date tranDate = transaction.getDateTime();
            long diff = Math.abs(now.getTime() - tranDate.getTime());
            long diffMins = diff / (60 * 1000);

            //Nếu transaction được tạo trong thời gian dưới 30 phút
            // thì đây là transaction mới tạo, dùng để xử lý
            if (diffMins < 30) {
                if (updateStatus) {
                    transaction.setStatus(TransactionStatus.TRANS_NOTPAY.getName());
                    transactionRepos.save(transaction);
                }
                result = TransactionDetailDTO.covertFromEntity(transaction);
            }
        }
        return result;
    }

    @Override
    public TransactionDTO getTransactionById(String transactionId) {

        Transaction transaction = transactionRepos.findOne(transactionId);
        if (transaction != null) {
            TransactionDTO dto = TransactionDTO.convertFromEntity(transaction);
            return dto;
        }

        return null;
    }

    @Override
    public TransactionDTO updateTransactionLane(String id, int laneId) {

        Transaction existingTransaction = transactionRepos.findOne(id);
        Lane lane = laneRepos.findOne(laneId);
        if (existingTransaction != null && lane != null) {

            existingTransaction.setLane(lane);
            Transaction processedTransaction = transactionRepos.save(existingTransaction);

            if (processedTransaction != null) {
                Transaction returnTransaction = transactionRepos.findOne(id);
                TransactionDTO dto = TransactionDTO.convertFromEntity(returnTransaction);
                return dto;
            }
        }
        return null;
    }

    @Override
    public TransactionDTO createCapturedTransaction(String plate, String photo, Date createdTime, int stationId) throws Exception {
        String id = createdTime.getTime() + "";

        Vehicle vehicle = vehicleRepos.findByLicensePlate(plate);

        if (vehicle != null) { //Nếu số xe có trong db
            Price price = priceRepos.findByStationIdAndTypeId(stationId, vehicle.getVehicleType().getId());
            int result = transactionRepos.createCaptureTransaction(id, stationId,
                    createdTime, TransactionStatus.TRANS_INITIAL.getName(),
                    price.getPrice(), photo, vehicle.getId());

            if (result > 0) {
                Transaction transaction = transactionRepos.findOne(id);
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
            Transaction transaction2 = transactionRepos.findOne(id);
            if (transaction2 != null) {
                TransactionDTO dto = TransactionDTO.convertFromEntity(transaction2);
                return dto;
            }
        }
        return null;
    }

    @Override
    public TransactionDetailDTO getDetailById(String id) {
        return TransactionDetailDTO.covertFromEntity(transactionRepos.findOne(id));
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
    public List<TransactionDetailDTO> getAllDetail() {

        List<TransactionDetailDTO> dtos = new ArrayList<>();

        List<Transaction> transactions = transactionRepos.findAll();

        for (Transaction transaction : transactions) {
            dtos.add(TransactionDetailDTO.covertFromEntity(transaction));
        }

        return dtos;
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
}
