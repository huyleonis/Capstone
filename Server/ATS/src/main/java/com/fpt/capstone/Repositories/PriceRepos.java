package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepos extends JpaRepository<Price, String>{


    @Query(value = "select * from price price" +
            " where price.station_id = (select s.id from beacon b, station s where b.uuid = ?1 and b.station_id = s.id)" +
            " and price.type_id = (select vehicletype.id from account account, vehicle vehicle, vehicletype vehicletype where account.role = 3 and account.username= ?2 and account.vehicle_id = vehicle.id and vehicle.type_id = vehicletype.id)", nativeQuery = true)
    Price findPriceByUuidAndUsername(String a, String b);
}
