package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.TransactionDTO;
import hackathon.fpt.ktk.dto.TransactionDetailDTO;
import hackathon.fpt.ktk.util.TransactionStatus;

public interface TransactionService {

    TransactionDTO insertManualTransaction(String licensePlate, int laneId);

    TransactionDTO updateTransactionStatus(String id, TransactionStatus status);

    TransactionDetailDTO getCapturedTransaction(int vehicleId, int stationId, boolean updateStatus);

    TransactionDTO getTransactionById(String transactionId);

    TransactionDTO updateTransactionLane(String id, int laneId);
}
