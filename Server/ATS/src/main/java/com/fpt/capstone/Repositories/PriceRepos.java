package com.fpt.capstone.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpt.capstone.Entities.Price;

@Repository
public interface PriceRepos extends JpaRepository<Price, Integer> {

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

    @Query(value = "SELECT * FROM price WHERE id = ?1", nativeQuery = true)
    Price findById(Integer id);



}
