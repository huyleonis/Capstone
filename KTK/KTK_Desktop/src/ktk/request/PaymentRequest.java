/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ktk.request;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import ktk.dtos.VehicleDTO;
import ktk.dtos.VehiclePayment;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Chi Hieu
 */
public class PaymentRequest {
    
    /**
     * Kiểm tra kết nối với server
     *
     * @param localhost địa chỉ ip server

     * @return check true|false
     */
    public boolean checkConnection(String localhost){
        boolean check = false;
        String urlName = localhost + "/ats/checkConnection";
        try {
            URL oracle = new URL(urlName); // URL to Parse
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            if((in.readLine()) != null) {
              check = true;  
            }
            in.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        
        return check;
    }
    
    /**
     * Tìm thông tin xe khi nhập biển số xe thủ công
     *
     * @param licensePlate biển số xe
     * @param idStation làn xe ứng với mỗi list
     * @param localhost
     * @return trả về thông tin loại xe, giá xe tương ứng với biển số xe
     */
    public VehicleDTO getInfoVehicle(String licensePlate, int idStation, String localhost) throws Exception {
        String urlName = localhost + "/price/findPriceStaff/" + idStation + "/" + licensePlate;
        JSONParser parser = new JSONParser();
        VehicleDTO vehicleDTO = new VehicleDTO();
        try {
            URL oracle = new URL(urlName); // URL to Parse
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject payment = (JSONObject) parser.parse(inputLine);
                if (payment != null) {
                    String typeName = (String) payment.get("typeVehicle");
                    Double price = (Double) payment.get("price");
                    vehicleDTO = new VehicleDTO(licensePlate, typeName, price);
                } else {
                    vehicleDTO = null;
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return vehicleDTO;
    }
    
    
    /**
     * Tạo transaction khi staff gửi request
     *
     * @param licensePlate biển số xe
     * @param idLane làn xe ứng với mỗi list
     * @param localhost
     * @return
     */
    public VehiclePayment insertManualPayment(String licensePlate, int idLane, String localhost) throws Exception {
        String urlName = localhost + "/transaction/makeManualPayment/" + licensePlate + "/" + idLane;
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
                String id = (String) payment.get("vehicleId");
                String typeName = (String) payment.get("typeVehicle");
                Double price = (Double) payment.get("price");
                String status = (String) payment.get("status");
                String photo = (String) payment.get("photo");
                vehiclePayment = new VehiclePayment(id, licensePlate, typeName, status, photo, price);
            }
            in.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return vehiclePayment;
    }
    
    /**
     * Cập nhật trạng thái của transaction thành Finish
     *
     * @param id id của transaction
     * @param localhost
     * @return
     */
    public VehiclePayment finishManualPayment(String id, String localhost) throws Exception {
        String urlName = localhost + "/transaction/finish/" + id;
        JSONParser parser = new JSONParser();
        VehiclePayment vehiclePayment = new VehiclePayment();
        try {
            URL oracle = new URL(urlName); // URL to Parse
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject payment = (JSONObject) parser.parse(inputLine);

                String licensePlate = (String) payment.get("licensePlate");
                String typeName = (String) payment.get("typeVehicle");
                Double price = (Double) payment.get("price");
                String status = (String) payment.get("status");
                String photo = (String) payment.get("photo");
                vehiclePayment = new VehiclePayment(id, licensePlate, typeName, status, photo, price);
            }
            in.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return vehiclePayment;
    }
    
    /**
     * Kiểm tra xe được camera nhận diện hay chưa?
     *
     * @param licensePlate biển số xe
     * @param stationId id của station
     * @param localhost
     * @return
     */
    public VehiclePayment getCapturedTransaction(String licensePlate, int stationId, String localhost) throws Exception {
        String urlName = localhost + "/transaction/getCapturedTransaction/" + licensePlate + "/" + stationId;
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
                //String licensePlate = (String) payment.get("licensePlate");
                String typeName = (String) payment.get("typeVehicle");
                Double price = (Double) payment.get("price");
                String status = (String) payment.get("status");
                String photo = (String) payment.get("photo");
                vehiclePayment = new VehiclePayment(id, licensePlate, typeName, status, photo, price);
            }
            in.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return vehiclePayment;
    }
}
