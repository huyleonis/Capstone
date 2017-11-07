/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.daos;

import ats.connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

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
        int idPrice = priceDAO.findPriceByLicensePlate(licensePlate, idStation);
        String idTransaction = new Date().getTime() + "";
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);
        boolean type = false;
        String status = "Hoàn thành giao dịch";
        try {
            String sql = "INSERT INTO transaction (id, vehicle_id, station_id, date_time, status, price_id, lane_id, type, photo) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, idTransaction);
            preStm.setInt(2, idVehicle);
            preStm.setInt(3, idStation);
            preStm.setString(4, currentTime);
            preStm.setString(5, status);
            preStm.setInt(6, idPrice);
            preStm.setInt(7, idLane);
            preStm.setBoolean(8, type);
            preStm.setString(9, photo);
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

}
