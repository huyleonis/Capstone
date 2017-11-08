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

/**
 *
 * @author Chi Hieu
 */
public class VehicleDAO {

    private ResultSet rs;
    private PreparedStatement preStm;
    private Connection conn;

    public VehicleDAO() {
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

    public int findVehicleByLicensePlate(String licensePlate) throws Exception {
        int idVehicle = 0;
        try {
            String sql = "SELECT id FROM vehicle WHERE license_plate = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, licensePlate);
            rs = preStm.executeQuery();
            if (rs.next()) {
                idVehicle = rs.getInt("id");
            }
        } finally {
            closeConnection();
        }
        return idVehicle;
    }

    public String findLicensePlateByVehicle(int id) throws Exception {
        String licensePlate = "";
        try {
            String sql = "SELECT license_plate FROM vehicle WHERE id = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                licensePlate = rs.getString("license_plate");
            }
        } finally {
            closeConnection();
        }
        return licensePlate;
    }
}
