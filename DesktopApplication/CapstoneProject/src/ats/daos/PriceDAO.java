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
public class PriceDAO {

    private ResultSet rs;
    private PreparedStatement preStm;
    private Connection conn;

    public PriceDAO() {
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

    public int findPriceByLicensePlate(String licensePlate, int idStation) throws Exception {
        int idPrice = 0;
        try {
            String sql = "SELECT pr.id "
                    + "FROM vehicle ve, price pr, vehicletype vt, station st "
                    + "WHERE ve.type_id = vt.id AND pr.type_id = vt.id"
                    + " AND pr.station_id = st.id  AND st.id = ?"
                    + " AND ve.license_plate = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, idStation);
            preStm.setString(2, licensePlate);
            rs = preStm.executeQuery();
            if (rs.next()) {
                idPrice = rs.getInt("id");
            }
        } finally {
            closeConnection();
        }
        return idPrice;
    }
}
