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
public class AccountDAO {

    private ResultSet rs;
    private PreparedStatement preStm;
    private Connection conn;

    public AccountDAO() {
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
    
        public int findAccountByLicensePlate(String licensePlate) throws Exception {
        int idAccount = 0;
        try {
            String sql = "SELECT ac.id FROM vehicle ve, account ac WHERE ve.id = ac.vehicle_id AND ve.license_plate = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, licensePlate);
            rs = preStm.executeQuery();
            if (rs.next()) {
                idAccount = rs.getInt("id");
            }
        } finally {
            closeConnection();
        }
        return idAccount;
    }
}
