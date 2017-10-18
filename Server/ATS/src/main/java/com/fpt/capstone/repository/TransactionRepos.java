package com.fpt.capstone.repository;

import com.fpt.capstone.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepos extends JpaRepository<Transaction,Integer>{

//    /**
//     * Tìm giá tiền dựa tren biển số xe và trạm cho thu phí thủ công gửi về staff
//     * @param licensePlate
//     * @param idStation
//     * @return
//     */
//    @Query(value = "select *" +
//            " from transaction" +
//            " where price_id =" +
//                                            " ( select price.id" +
//                                            " from vehicle vehicle, vehicletype vehicletype, price price" +
//                                            " where vehicle.license_plate = ?1 and vehicle.type_id = vehicletype.id and price.type_id = vehicletype.id" +
//                                            " and price.station_id =" +
//                                                                    " ( select station.id" +
//                                                                    " from beacon beacon, station station, account account" +
//                                                                    " where account.role = 2 and account.id = ?2 and account.id = beacon.account_id and beacon.station_id = station.id))" +
//            " and username_id =" +
//                                            " ( select a.id" +
//                                            " from account a, vehicle v" +
//                                            " where a.role = 3 and a.vehicle_id = v.id and v.license_plate = ?1)", nativeQuery = true)
//    Transaction findByLicensePlate(String licensePlate, int idStation);

    /**
     * Tao transaction thu phí thủ công khi staff gửi biển số xe và mã trạm
     * @param licensePlate
     * @param laneId
     * @param transactionId
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "insert into transaction (id, username_id, station_id, date_time, status, price_id, lane_id, type)" +
            " values (?3, " +
            "(select a.id from vehicle v, account a where v.id = a.vehicle_id and v.license_plate = ?1), " +
            "(select l.station_id from lane l where l.id = ?2), " +
            "?4, " +
            "'Chưa thanh toán', " +
            "?5, " +
            "?2," +
            "false)", nativeQuery = true)
    int insertManualTransaction(String licensePlate, int laneId, String transactionId, Date dateNow, int priceId);


    /**
     * Lấy transasaction theo id
     * @param id
     * @return
     */
    @Query(value = "select * from transaction t where t.id = ?1" , nativeQuery = true)
    Transaction findById(String id);

    /**
     * Tạo transaction thanh toán tự động, khi driver đi vào beacon 1, gửi lên username và idStaion
     * @param username
     * @param stationId
     * @param idTransaction
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "insert into transaction (id, username_id, station_id, date_time, status, price_id, type)" +
                    " values(" +
            "?3, " +
            "(select id from account where username = ?1), " +
            "?2, " +
            "?4, " +
            "'Chưa thanh toán', " +
            "?5, " +
            "true)", nativeQuery = true)
    int insertAutoTransaction(String username, int stationId, String idTransaction, Date now, int priceId);


    /**
     * Cập nhật trạng thái của transaction
     * @param idTransaction
     * @param status
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update transaction set status = ?2 where id = ?1", nativeQuery = true)
    int updateTransaction(String idTransaction, String status);

    /**
     * Lấy transaction cho staff theo status cho trước
     * @param laneId
     * @param status
     * @return
     */
    @Query(value = "select * from transaction where lane_id = ?1 and status like CONCAT('%',?2,'%') order by date_time asc", nativeQuery = true)
    List<Transaction> getTransactionForStaff(int laneId, String status);


    /**
     * Cập nhật làn đường mà xe chạy vào khi xe qua beacon 2
     * @param idTransaction
     * @param lane_id
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update transaction set lane_id = ?2 where id = ?1", nativeQuery = true)
    int updateTransaction(String idTransaction, int lane_id);
}
