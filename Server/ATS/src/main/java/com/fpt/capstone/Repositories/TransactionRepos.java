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

    @Query(value = "insert into transaction ( username_id, station_id, date_time, status, price_id)" +
            " values ((select a.id from vehicle v, account a where v.id = a.vehicle_id and v.license_plate = ?1), ?2, '2017-01-01 00:00:03', 'Chưa thanh toán', (select price.id from vehicle ve, vehicletype vt, price price where ve.license_plate = ?1 and ve.type_id = vt.id and vt.id = price.type_id and price.station_id = ?2))", nativeQuery = true)
    Transaction insertTransaction(String license_plate, int station_id);
}
