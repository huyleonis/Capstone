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
import java.net.URL;
import java.net.URLConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.TimerTask;


/**
 *
 * @author Chi Hieu
 */
public class ManualPaymentRequest{

    //private final String USER_AGENT = "Mozilla/5.0";
    // HTTP GET request

    //Make a manual payment
    public VehiclePayment insertManualPayment(String licensePlate, int idLane) throws Exception {
        String urlName = "http://localhost:8080/transaction/makeManualPayment/" + licensePlate + "/" + idLane;
        JSONParser parser = new JSONParser();
        VehiclePayment vehiclePayment = new VehiclePayment();
        try {
            URL oracle = new URL(urlName); // URL to Parse
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject payment = (JSONObject) parser.parse(inputLine);
                // Get id of transaction
                String id = (String) payment.get("id");
                vehiclePayment.setId(id);
                // Get type of vehicle
                String typeName = (String) payment.get("vehicle_id");
                vehiclePayment.setTypeName(typeName);
                // Get price of vehicle
                Double price = (Double) payment.get("price_id");    
                vehiclePayment.setFee(price);
                // Get status of transaction
                String status = (String) payment.get("status");
                vehiclePayment.setStatus(status);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehiclePayment;
    }

    //Update Manual Payment: status from Chưa Thanh Toán to Đã thanh toán.
    public VehiclePayment updateManualPayment(String id) throws Exception {
        String urlName = "http://localhost:8080/transaction/receivedMoney/" + id;
        JSONParser parser = new JSONParser();
        VehiclePayment vehiclePayment = new VehiclePayment();
        try {
            URL oracle = new URL(urlName); // URL to Parse
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject payment = (JSONObject) parser.parse(inputLine);

                String typeName = (String) payment.get("vehicle_id");
                vehiclePayment.setTypeName(typeName);

                Double price = (Double) payment.get("price_id");
                vehiclePayment.setFee(price);

                String status = (String) payment.get("status");
                vehiclePayment.setStatus(status);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehiclePayment;
    }

    //Finish Manual Payment: update status from "Đã hoàn thành" to "Đã hoàn thành"
    public VehiclePayment finishManualPayment(String id) throws Exception {
        String urlName = "http://localhost:8080/transaction/finish/" + id;
        JSONParser parser = new JSONParser();
        VehiclePayment vehiclePayment = new VehiclePayment();
        try {
            URL oracle = new URL(urlName); // URL to Parse
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject payment = (JSONObject) parser.parse(inputLine);

//               String id = (String) payment.get("id");
//                vehiclePayment.setId(id);
                String typeName = (String) payment.get("vehicle_id");
                vehiclePayment.setTypeName(typeName);

                Double price = (Double) payment.get("price_id");
                vehiclePayment.setFee(price);

                String status = (String) payment.get("status");
                vehiclePayment.setStatus(status);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehiclePayment;
    }

}
