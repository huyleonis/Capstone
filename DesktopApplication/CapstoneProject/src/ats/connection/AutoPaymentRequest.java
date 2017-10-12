/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.connection;

import ats.dtos.VehiclePayment;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
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

    //Get all Automatic Payment to Queue
    public Queue<VehiclePayment> getAutoTrans(int idLane){

        JSONParser parser = new JSONParser();
        Queue<VehiclePayment> list = new PriorityQueue<>();
        try {
            URL oracle = new URL("http://localhost:8080/transaction/getResult/" + idLane); // URL to Parse
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONArray array = (JSONArray) parser.parse(inputLine);

                // Loop through each item
                for (Object transaction : array) {

                    JSONObject trans = (JSONObject) transaction;
                    if (!trans.isEmpty()) {
                        String id = (String) trans.get("id");
                        String typeName = (String) trans.get("vehicle_id");
                        Double price = (Double) trans.get("price_id");
                        String status = (String) trans.get("status");

                        VehiclePayment vp = new VehiclePayment(id, typeName, status, price);
                        list.offer(vp);
                    }
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(AutoPaymentRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

//    @Override
//    public void run() {
//        try {
//            getAutoTrans(1);
//        } catch (Exception ex) {
//            Logger.getLogger(AutoPaymentRequest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
