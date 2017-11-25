package hackathon.fpt.ktk.repository;


import hackathon.fpt.ktk.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepos extends JpaRepository<Price, Integer> {

    @Query(value = "SELECT * FROM price WHERE stationId = :stationId " +
            "AND typeid = :typeid", nativeQuery = true)
    Price findByStationIdAndTypeId(@Param(value = "stationId") int stationId,
                                   @Param(value = "typeid") int typeid);

    /**
     * Tìm giá tiền dựa trên biển số xe và trạm cho thu phí thủ công gửi về staff
     * @param licensePlate
     * @param idLane
     * @return price, typeName, accountId
     */
    @Query(value = "select * from price  where stationId = "
            + "(select s.id from station s, lane l where l.stationId = s.id and l.id = :idLane) "
            + "and typeId = "
            + "(select vt.id from vehicletype vt, vehicle v where vt.id = v.typeId and v.license_plate = :license)", nativeQuery = true)
    Price findByLicensePlate(@Param("license") String licensePlate, @Param("idLane") int idLane);

    /**
     * Tìm giá tiền dựa trên station id và license plate
     *
     * @param stationId
     * @param licensePlate
     * @return
     */
    @Query(value = "select * from price price where price.stationId = :stationId and price.typeId = " +
            "(select vehicle.typeId from vehicle vehicle where vehicle.licensePlate = :plate)", nativeQuery = true)
    Price findPriceByStationIdAndLicensePlate(@Param("stationId") int stationId,
                                              @Param("plate") String licensePlate);

}