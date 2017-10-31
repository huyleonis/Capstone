package com.fpt.capstone.repository;

import com.fpt.capstone.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD:Server/ATS/src/main/java/com/fpt/capstone/Repositories/PriceRepos.java
import java.util.List;
import org.springframework.data.repository.query.Param;

@Repository
public interface PriceRepos extends JpaRepository<Price, String> {
=======
@Repository
public interface PriceRepos extends JpaRepository<Price, Integer>{

>>>>>>> origin/WebAdmin:Server/ATS/src/main/java/com/fpt/capstone/repository/PriceRepos.java

    /**
     * Tìm giá tiền dựa trên username và id của station cho thu phi tự động
     * @param username
     * @param station Id
     * @return
     */
    @Query(value = "select * from price price" +
            " where price.station_id = :stationId" +
            " and price.type_id = "
            + "(select type_id from vehicle where id = "
            + "(select vehicle_id from account where username = :username))", nativeQuery = true)
    Price findPriceByStationAndUsername(@Param("username") String username, @Param("stationId") int stationId);


    /**
     * Tìm giá tiền dựa trên station id và license plate cho thu phí thủ công
     *
     * @param stationId
     * @param licensePlate
     * @return
     */
    @Query(value = "select * from price price where price.station_id = ?1 and price.type_id = "
            + "(select vehicle_type.id from vehicletype vehicle_type where vehicle_type.id = "
            + "(select vehicle.type_id from vehicle vehicle where vehicle.license_plate = ?2))", nativeQuery = true)
    Price findPriceByStationIdAndLicensePlate(int stationId, String licensePlate);
<<<<<<< HEAD:Server/ATS/src/main/java/com/fpt/capstone/Repositories/PriceRepos.java

    @Query(value = "SELECT * FROM price WHERE id = ?1", nativeQuery = true)
    Price findById(Integer id);

    /**
     * Tìm giá tiền dựa trên biển số xe và trạm cho thu phí thủ công gửi về staff
     * @param licensePlate
     * @param idLane
     * @return price, typeName, account_id
     */
    @Query(value = "select * from price where station_id = "
            + "(select s.id from station s, lane l where l.station_id = s.id and l.id = :idLane) "
            + "and type_id = "
            + "(select vt.id from vehicletype vt, vehicle v where vt.id = v.type_id and v.license_plate = :license)", nativeQuery = true)
    Price findByLicensePlate(@Param("license") String licensePlate, @Param("idLane") int idLane);

=======
>>>>>>> origin/WebAdmin:Server/ATS/src/main/java/com/fpt/capstone/repository/PriceRepos.java
}
