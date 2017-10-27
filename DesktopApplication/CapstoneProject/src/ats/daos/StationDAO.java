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
public class StationDAO {

    private ResultSet rs;
    private PreparedStatement preStm;
    private Connection conn;

    public StationDAO() {
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

    public int findStationByLane(int idLane) throws Exception {
        int idStation = 0;
        try {
            String sql = "SELECT st.id FROM station st, lane la WHERE la.station_id = st.id AND la.id = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, idLane);
            rs = preStm.executeQuery();
            if (rs.next()) {
                idStation = rs.getInt("id");
            }
        } finally {
            closeConnection();
        }
        return idStation;
    }
}
