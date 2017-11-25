package hackathon.fpt.ktk.repository;

import hackathon.fpt.ktk.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepos extends JpaRepository<Transaction, String> {

    @Query(value = "SELECT * FROM transaction "
            + "WHERE vehicleId = :vehicleId "
            + "AND stationId = :stationId "
            + "AND status = 'Initial' "
            + "AND createdTime > NOW() - INTERVAL 30 MINUTE", nativeQuery = true)
    Transaction findByVehicleIdAndStationId(@Param(value = "vehicleId") int vehicleId,
                                            @Param(value = "stationId") int stationId);
}
