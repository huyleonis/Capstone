/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.request;

import ats.dtos.TransactionDetailDTO;
import ats.dtos.VehicleDTO;
import ats.dtos.VehiclePayment;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Chi Hieu
 */
public class ManualPaymentRequest {

//    public static final String LOCALHOST = "http://localhost:8080";

    /**
     * Tìm thông tin xe khi nhập biển số xe thủ công
     *
     * @param licensePlate biển số xe
     * @param idLane làn xe ứng với mỗi list
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
     * @return trả về thông tin loại xe, giá xe tương ứng với biển số xe
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
                String id = (String) payment.get("id");
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
    /**
     * Cập nhật trạng thái của transaction thành "Đã thu"
     *
     * @param id id của transaction
     * @return trả về Object có chứa thông tin transaction với status là "Đã
     * thu"
     */
//    public VehiclePayment updateManualPayment(String id) throws Exception {
//        String urlName = LOCALHOST + "/transaction/receivedMoney/" + id;
//        JSONParser parser = new JSONParser();
//        VehiclePayment vehiclePayment = new VehiclePayment();
//        try {
//            URL oracle = new URL(urlName); // URL to Parse
//            URLConnection yc = oracle.openConnection();
//            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
//
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                JSONObject payment = (JSONObject) parser.parse(inputLine);
//
//                String typeName = (String) payment.get("vehicle_id");
//                vehiclePayment.setTypeName(typeName);
//
//                Double price = (Double) payment.get("price_id");
//                vehiclePayment.setFee(price);
//
//                String status = (String) payment.get("status");
//                vehiclePayment.setStatus(status);
//            }
//            in.close();
//        } catch (FileNotFoundException e) {
//        } catch (IOException e) {
//        }
//        return vehiclePayment;
//    }
    /**
     * Cập nhật trạng thái của transaction thành Kết thúc
     *
     * @param id id của transaction
     * @return trả về Object có chứa thông tin transaction với status là "Kết
     * thúc"
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
     * Danh sách các transaction chưa thanh toán theo biển số xe
     *
     * @param licensePlate
     * @param localhost

     * @return trả về Object có chứa thông tin transaction với status là "Kết
     * thúc"
     * @throws java.lang.Exception
     */
    public List<TransactionDetailDTO> listTransactionNotPay(String licensePlate, String localhost) throws Exception {
        String urlName = localhost + "/transaction/findTransactionNotPay/" + licensePlate;
        JSONParser parser = new JSONParser();
        List<TransactionDetailDTO> list = new ArrayList<>();
        try {
            URL oracle = new URL(urlName); // URL to Parse
            URLConnection yc = oracle.openConnection();  // Open Connection
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONArray array = (JSONArray) parser.parse(inputLine);
                // Loop through each item
                for (Object transaction : array) {
                    JSONObject trans = (JSONObject) transaction;
                    if (!trans.isEmpty()) {
                        String id = (String) trans.get("id");
                        Double price = (Double) trans.get("price");
                        String status = (String) trans.get("status");
                        String photo = (String) trans.get("photo");
                        String dateTime = (String) trans.get("dateTime");
                        String type = (String) trans.get("type");
                        String typeVehicle = (String) trans.get("typeVehicle");
                        TransactionDetailDTO dto = new TransactionDetailDTO(licensePlate, id, status, dateTime, type, photo, typeVehicle, price);
                        list.add(dto);
                    }
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
        } catch (IOException | org.json.simple.parser.ParseException e) {
        }
        return list;
    }
    
}
