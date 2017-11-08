package com.fpt.capstone.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpt.capstone.Entities.Price;

@Repository
public interface PriceRepos extends JpaRepository<Price, Integer> {

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

    @Query(value = "SELECT * FROM price WHERE id = ?1", nativeQuery = true)
    Price findById(Integer id);

    /**
     * Tìm giá tiền dựa trên biển số xe và trạm cho thu phí thủ công gửi về staff
     * @param licensePlate
     * @param idLane
     * @return price, typeName, account_id
     */
    @Query(value = "SELECT * FROM price"
            + "WHERE station_id = "
            + "(SELECT st.id FROM station st, lane la WHERE la.station_id = st.id AND la.id = :idLane) "
            + "AND type_id = "
            + "(SELECT vt.id FROM vehicletype vt, vehicle v WHERE vt.id = v.type_id AND v.license_plate = :license)", nativeQuery = true)
    Price findByLicensePlate(@Param("license") String licensePlate,@Param("idLane") int idLane);

}
