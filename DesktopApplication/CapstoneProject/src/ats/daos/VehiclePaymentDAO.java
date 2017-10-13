/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.daos;

import ats.connection.MyConnection;
import ats.dtos.VehiclePayment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Chi Hieu
 */
public class VehiclePaymentDAO {
    private PreparedStatement preStm;
    private Connection conn;
    private ResultSet rs;

    public VehiclePaymentDAO() {
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

    public VehiclePayment searchPaymentByLicensePlate(String licensePlate) throws Exception{
        VehiclePayment vp = new VehiclePayment();
        try {
            String sql = "Select t.name, p.price "
                    + "FROM vehicletype t, vehicle v, price p "
                    + "WHERE t.id = v.type_id AND t.id = p.type_id AND v.license_plate = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, licensePlate);
            rs = preStm.executeQuery();
            String nameType = "";
            double price = 0;
            if(rs.next()){
                nameType = rs.getString("name");
                price = rs.getDouble("price");
                vp.setTypeName(nameType);
                vp.setFee(price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeConnection();
        }
        
        return vp;
    }
}