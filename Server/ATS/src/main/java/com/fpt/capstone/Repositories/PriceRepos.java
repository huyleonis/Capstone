package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.repository.query.Param;

@Repository
public interface PriceRepos extends JpaRepository<Price, String>{


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
     * @param stationId
     * @param licensePlate
     * @return
     */
    @Query(value = "select * from price price where price.station_id = ?1 and price.type_id = " +
            "(select vehicle_type.id from vehicletype vehicle_type where vehicle_type.id = " +
            "(select vehicle.type_id from vehicle vehicle where vehicle.license_plate = ?2))", nativeQuery = true)
    Price findPriceByStationIdAndLicensePlate(int stationId, String licensePlate);
}
