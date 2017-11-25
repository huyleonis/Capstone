package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.TransactionDTO;
import hackathon.fpt.ktk.dto.TransactionDetailDTO;
import hackathon.fpt.ktk.entity.Lane;
import hackathon.fpt.ktk.entity.Price;
import hackathon.fpt.ktk.entity.Transaction;
import hackathon.fpt.ktk.entity.Vehicle;
import hackathon.fpt.ktk.util.TransactionStatus;
import hackathon.fpt.ktk.util.TransactionType;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
