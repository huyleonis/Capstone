package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepos extends JpaRepository<Transaction,Integer>{

    @Query(value = "select *" +
            " from transaction" +
            " where price_id =" +
                                            " ( select price.id" +
                                            " from vehicle vehicle, vehicletype vehicletype, price price" +
                                            " where vehicle.license_plate = ?1 and vehicle.type_id = vehicletype.id and price.type_id = vehicletype.id" +
                                            " and price.station_id =" +
                                                                    " ( select station.id" +
                                                                    " from beacon beacon, station station, account account" +
                                                                    " where account.role = 2 and account.id = ?2 and account.id = beacon.account_id and beacon.station_id = station.id))" +
            " and username_id =" +
                                            " ( select a.id" +
                                            " from account a, vehicle v" +
                                            " where a.role = 3 and a.vehicle_id = v.id and v.license_plate = ?1)", nativeQuery = true)
    Transaction findByLicensePlate(String a, int id);


}
