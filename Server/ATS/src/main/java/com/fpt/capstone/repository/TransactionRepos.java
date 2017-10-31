package com.fpt.capstone.repository;

import com.fpt.capstone.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.query.Param;

@Repository
public interface TransactionRepos extends JpaRepository<Transaction, Integer> {

    /**
     * Tao transaction thu phí thủ công khi staff gửi biển số xe và mã trạm
     *
     * @param licensePlate
     * @param laneId
     * @param transactionId
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "insert into transaction (id, username_id, station_id, date_time, status, price_id, lane_id, type)"
            + " values (?3, "
            + "(select a.id from vehicle v, account a where v.id = a.vehicle_id and v.license_plate = ?1), "
            + "(select l.station_id from lane l where l.id = ?2), "
            + "?4, "
            + "'Chưa thanh toán', "
            + "?5, "
            + "?2,"
            + "false)", nativeQuery = true)
    int insertManualTransaction(String licensePlate, int laneId, String transactionId, Date dateNow, int priceId);

    /**
     * Lấy transasaction theo id
     *
     * @param id
     * @return
     */
    @Query(value = "select * from transaction where id = ?1" , nativeQuery = true)
    Transaction findById(String id);

    /**
     * Tạo transaction thanh toán tự động, khi driver đi vào beacon 1, gửi lên
     * username và idStaion
     *
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
    int insertAutoTransaction(String username, int stationId, String idTransaction, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date now, int priceId);


    /**
     * Cập nhật trạng thái của transaction
     *
     * @param idTransaction
     * @param status
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update transaction set status = ?2 where id = ?1", nativeQuery = true)
    int updateTransaction(String idTransaction, String status);

    /**
     * Lấy transaction cho staff theo status cho trước trong vòng 5 phút trước đó 
     *
     * @param laneId
     * @param status
     * @return
     */
    @Query(value = "select * from transaction where status like CONCAT('%',?1,'%') AND date_time between date_sub(now(), interval 5 minute) AND now() order by date_time asc", nativeQuery = true)
    List<Transaction> getTransactionForStaff(String status);

    /**
     * Cập nhật làn đường mà xe chạy vào khi xe qua beacon 2
     *
     * @param idTransaction
     * @param lane_id
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update transaction set lane_id = ?2 where id = ?1", nativeQuery = true)
    int updateTransaction(String idTransaction, int lane_id);


    /**
     * lịch sử giao dịch cho tài xế, khi chọn ngày bắt đầu và kết thúc giao dịch
     * @param username : tên tài xế
     * @param fromDate : bắt đầu từ ngày giao dịch
     * @param toDate : kết thúc ngày giao dịch
     * @return
     */
    @Query(value = "select * from transaction where username_id = " +
            "(select id from account where username = :user) and " +
            "date_time > :fromDate and date_time < :toDate", nativeQuery = true)
    List<Transaction> getHistoryTransaction(@Param("user") String username, @Param("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate, @Param("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate);

    @Query(value = "SELECT * FROM transaction WHERE username_id = ?1", nativeQuery = true)
    List<Transaction> findByUsernameId(Integer usernameId);
}
