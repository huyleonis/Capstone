/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.request;

import ats.dtos.VehiclePayment;
import ats.swing.gui.MainForm;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Chi Hieu
 */
public class AutoPaymentRequest extends TimerTask {

    //public static final String LOCALHOST = "http://localhost:8080";
    
    /**
     * Lấy các transaction theo từng làn xe khi thanh toán tự động với status là "Chờ xử lý" và lưu vào queue
     *
     * @param idLane làn xe ứng với mỗi list
     * @param localhost tên
     * @return trả về thông tin list transaction "Chờ xử lý"
     */
    public List<VehiclePayment> getListVehicleUnpaid(String localhost) {
        JSONParser parser = new JSONParser();
        List<VehiclePayment> list = new ArrayList<>();
        try {
            URL oracle = new URL(localhost
                    + "/transaction/getResult"); // URL to Parse
            URLConnection yc = oracle.openConnection();  // Open Connection
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONArray array = (JSONArray) parser.parse(inputLine);
                // Loop through each item
                for (Object transaction : array) {
                    JSONObject trans = (JSONObject) transaction;
                    if (!trans.isEmpty()) {
                        String licensePlate = (String) trans.get("licensePlate");
                        String id = (String) trans.get("transactionId");
                        String typeName = (String) trans.get("vehicleType");
                        Double price = (Double) trans.get("price");
                        String status = (String) trans.get("status");

                        VehiclePayment vp = new VehiclePayment(id, licensePlate, typeName, status, price);
                        list.add(vp);
                    }
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(AutoPaymentRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
