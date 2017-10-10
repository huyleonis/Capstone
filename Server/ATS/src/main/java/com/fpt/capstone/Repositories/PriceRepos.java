package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepos extends JpaRepository<Price, String>{


    /**
     * Tìm giá tiền dựa trên username và uuid beacon cho thu phi tự động
     * @param username
     * @param uuid
     * @return
     */
    @Query(value = "select * from price price" +
            " where price.station_id = (select b.station_id from beacon b where b.uuid = ?1)" +
            " and price.type_id = (select vehicletype.id from account account, vehicle vehicle, vehicletype vehicletype where account.role = 3 and account.username= ?2 and account.vehicle_id = vehicle.id and vehicle.type_id = vehicletype.id)", nativeQuery = true)
    Price findPriceByUuidAndUsername(String username, String uuid);


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
