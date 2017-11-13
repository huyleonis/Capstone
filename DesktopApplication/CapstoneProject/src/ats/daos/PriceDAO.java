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

    public double findPriceByLicensePlate(String licensePlate, int idStation) throws Exception {
        double price = 0;
        try {
            String sql = "SELECT pr.price "
                    + "FROM vehicle ve, price pr, vehicletype vt, station st "
                    + "WHERE ve.typeId = vt.id AND pr.typeId = vt.id"
                    + " AND pr.stationId = st.id  AND st.id = ?"
                    + " AND ve.licensePlate = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, idStation);
            preStm.setString(2, licensePlate);
            rs = preStm.executeQuery();
            if (rs.next()) {
                price = rs.getDouble("price");
            }
        } finally {
            closeConnection();
        }
        return price;
    }
}
