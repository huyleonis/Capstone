/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.daos;

import ats.connection.MyConnection;
import ats.dtos.VehicleDTO;
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
            String sql = "SELECT id FROM vehicle WHERE licensePlate = ?";
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
            String sql = "SELECT licensePlate FROM vehicle WHERE id = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                licensePlate = rs.getString("licensePlate");
            }
        } finally {
            closeConnection();
        }
        return licensePlate;
    }

    public VehicleDTO findPriceByLicensePlate(String licensePlate, int stationId) throws Exception {
        VehicleDTO dto = new VehicleDTO();
        try {
            String sql = "SELECT vt.name, pr.price "
                    + "FROM vehicle ve, vehicletype vt, price pr, station st "
                    + "WHERE ve.typeId = vt.id AND ve.licensePlate = ? AND pr.typeId = vt.id "
                    + "AND st.id = ? AND st.id = pr.stationId";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, licensePlate);
            preStm.setInt(2, stationId);
            rs = preStm.executeQuery();
            if (rs.next()) {      
                dto.setLicensePlate(licensePlate);
                dto.setTypeVehicle(rs.getString("name"));
                dto.setPrice(rs.getDouble("price"));
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
}
