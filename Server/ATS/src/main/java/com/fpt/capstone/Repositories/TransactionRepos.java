package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepos extends JpaRepository<Transaction, String> {

    /**
     * Tạo transaction thanh toán thủ công
     * @param id
     * @param stationId
     * @param idTransaction
     * @param now
     * @param status
     * @param price
     * @param type
     * @param vehicleId
     * @return 
     */
    @Modifying
    @Transactional
    @Query(value = "insert into transaction (id, stationId, createdTime, status, price, type, vehicleId)" +
                                    " values(:id, :stationId, :time, :status, :price, :type, :vehicleId)", nativeQuery = true)
    int insertManualTransaction(@Param("id") String id, 
            @Param("stationId") int stationId, 
            @Param("time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date now, 
            @Param("status") String status,
            @Param("price") double price,
            @Param("type") int type,
            @Param("vehicleId") int vehicleId);

    /**
     * Lấy transasaction theo id
     *
     * @param id
     * @return
     */
    @Query(value = "select * from transaction where id = ?1", nativeQuery = true)
    Transaction findById(String id);

    /**
     * Tạo transaction thanh toán tự động, khi driver đi vào beacon 1, gửi lên
     * yêu cầu thanh toán khi chưa chụp dc hình
     *
     * @param id
     * @param stationId
     * @param idTransaction
     * @param now
     * @param status
     * @param price
     * @param type
     * @param vehicleId
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "insert into transaction (id, stationId, createdTime, status, price, type, vehicleId)" +
            " values(:id, :stationId, :time, :status, :price, :type, :vehicleId)", nativeQuery = true)
    int insertAutoTransaction(@Param("id") String id,
                              @Param("stationId") int stationId,
                              @Param("time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date now,
                              @Param("status") String status,
                              @Param("price") double price,
                              @Param("type") int type,
                              @Param("vehicleId") int vehicleId);

    /**
     * Method to create transaction when camera capture photo of plate
     *
     * @param id
     * @param plate
     * @param stationId
     * @param createdTime
     * @param status
     * @param price
     * @param filePath
     * @param vehicleId
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "insert into transaction (id, stationId, createdTime, status, price, photo, vehicleId)" +
            "                values(:id, :stationId, :time,  :status, :price, :photo, :vehicleId)", nativeQuery = true)
    int createCaptureTransaction(@Param("id") String id,
                                 @Param("stationId") int stationId,
                                 @Param("time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createdTime,
                                 @Param("status") String status,
                                 @Param("price") double price,
                                 @Param("photo") String filePath,
                                 @Param("vehicleId") int vehicleId);

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
     * Cập nhật type của transaction
     *
     * @param id
     * @param type
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update transaction set type = ?2 where id = ?1", nativeQuery = true)
    int updateTransactionType(String id, int type);

    /**
     * Lấy transaction cho staff theo status cho trước trong vòng 10 phút trước đó 
     *
     * @param laneId
     * @param status
     * @return
     */
    @Query(value = "select * from transaction where status like CONCAT('%',?1,'%')"
            + " AND createdTime between date_sub(now(), interval 10 minute)"
            + " AND now() order by createdTime asc", nativeQuery = true)
    List<Transaction> getTransactionForStaff(String status);

    /**
     * Cập nhật làn đường mà xe chạy vào khi xe qua beacon 2
     *
     * @param idTransaction
     * @param laneId
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update transaction set laneId = ?2 where id = ?1", nativeQuery = true)
    int updateTransaction(String idTransaction, int laneId);


    /**
     * lịch sử giao dịch cho tài xế, khi chọn ngày bắt đầu và kết thúc giao dịch
     *
     * @param vehicleId : id của vehicle
     * @param fromDate  : bắt đầu từ ngày giao dịch
     * @param toDate    : kết thúc ngày giao dịch
     * @return
     */
    @Query(value = "select * from transaction where vehicleId = :vehicleId " +
            "and createdTime > :fromDate and createdTime < :toDate ", nativeQuery = true)
    List<Transaction> getHistoryTransaction(@Param("vehicleId") int vehicleId,
                                            @Param("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
                                            @Param("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate);

    /**
     * Tìm transaction detail theo vehicle id trong vòng 24 giờ
     *
     * @param vehicleId khóa xác định xe của tài xế
     * @return
     */
    @Query(value = "SELECT * FROM transaction "
            + "WHERE vehicleId = :vehicleId "
            + "AND createdTime >= NOW() - INTERVAL 24 HOUR", nativeQuery = true)
    List<Transaction> findByVehicleIdIn24Hours(@Param("vehicleId") Integer vehicleId);

    @Query(value = "SELECT * FROM transaction "
            + "WHERE vehicleId = :vehicleId "
            + "AND stationId = :stationId "
            + "AND status = 'Initial' "
            + "AND createdTime > NOW() - INTERVAL 30 MINUTE", nativeQuery = true)
    Transaction getCapturedTransaction(@Param("vehicleId") Integer vehicleId, @Param("stationId") Integer stationId);

    @Query(value = "SELECT * FROM transaction WHERE status = :status", nativeQuery = true)
    List<Transaction> findByStatus(@Param("status") String status);
    
    @Query(value = "SELECT * FROM transaction WHERE vehicleId = :vehicleId AND "
    		+ "(createdTime between :createdTime - INTERVAL 30 minute AND :createdTime + INTERVAL 30 minute)", nativeQuery = true)
    List<Transaction> findByVehicleIdAndTime(@Param("vehicleId") int vehicleId, 
    										@Param("createdTime") String createdTime);
}