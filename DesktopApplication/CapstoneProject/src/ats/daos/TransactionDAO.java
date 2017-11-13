/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.daos;

import ats.connection.MyConnection;
import ats.dtos.TransactionDTO;
import ats.dtos.VehicleDTO;
import ats.utils.TransactionStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Chi Hieu
 */
public class TransactionDAO {

    private ResultSet rs;
    private PreparedStatement preStm;
    private Connection conn;

    public TransactionDAO() {
    }

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public boolean insertTransaction(String licensePlate, int idLane, String photo) throws Exception {
        boolean check = false;
        PriceDAO priceDAO = new PriceDAO();
        StationDAO stationDAO = new StationDAO();
        VehicleDAO vehicleDAO = new VehicleDAO();
        int idVehicle = vehicleDAO.findVehicleByLicensePlate(licensePlate);
        int idStation = stationDAO.findStationByLane(idLane);
        double price = priceDAO.findPriceByLicensePlate(licensePlate, idStation);
        String idTransaction = new Date().getTime() + "";
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);
        boolean type = false;
        String status = TransactionStatus.TRANS_FINISH.toString();
        try {
            String sql = "INSERT INTO transaction (id, stationId, createdTime, status, price, laneId, type, photo, vehicleId) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, idTransaction);
            preStm.setInt(9, idVehicle);
            preStm.setInt(2, idStation);
            preStm.setString(3, currentTime);
            preStm.setString(4, status);
            preStm.setDouble(5, price);
            preStm.setInt(6, idLane);
            preStm.setBoolean(7, type);
            preStm.setString(8, photo);
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public List<TransactionDTO> getTransactionByDateFromClient(String date) throws Exception {
        List<TransactionDTO> list = null;
        VehicleDAO dao = new VehicleDAO();
        try {
            String sql = "SELECT * FROM transaction WHERE date_format(date_time, '%Y-%m-%d') = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, date);
            rs = preStm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                int vehicleId = rs.getInt("vehicle_id");
                String licensePlate = dao.findLicensePlateByVehicle(vehicleId);
                int stationId = rs.getInt("station_id");
                java.sql.Date dateTime = (java.sql.Date) rs.getDate("date_time");
                String status = rs.getString("status");
                int priceId = rs.getInt("price_id");
                int laneId = rs.getInt("lane_id");
                String type = rs.getString("type");
                String photo = rs.getString("photo");
                TransactionDTO dto = new TransactionDTO(id, vehicleId, licensePlate, stationId, dateTime, status, priceId, laneId, type, photo);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }

        return list;
    }

}
