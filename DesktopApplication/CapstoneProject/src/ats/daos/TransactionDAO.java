/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.daos;

import ats.connection.MyConnection;
import ats.dtos.Transaction;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chi Hieu
 */
public class TransactionDAO {

    private PreparedStatement preStm;
    private Connection conn;
    private ResultSet rs;

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

    public List<Transaction> listOfTransaction() throws Exception {
        List<Transaction> result = null;
        try {
            String sql = "SELECT tr.IdTransaction, tr.Username, tr.DateTime, tr.Fee, ac.LicensePlate "
                    + "FROM transaction tr, account ac "
                    + "WHERE tr.Username = ac.Username";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            int id = 0;
            String username = "";
            String licensePlate = "";
            Date dateOfTransaction = null;
            double fee = 0;
            while (rs.next()) {
                id = rs.getInt("IdTransaction");
                username = rs.getString("Username");
                licensePlate = rs.getString("LicensePlate");
                dateOfTransaction = rs.getDate("DateTime");
                fee = rs.getDouble("Fee");
                Transaction tr = new Transaction(id, username, licensePlate, fee, dateOfTransaction);
                result.add(tr);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
