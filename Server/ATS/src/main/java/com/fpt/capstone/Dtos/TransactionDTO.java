package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Transaction;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TransactionDTO {
    private String id;
    private String username;
    private int usernameId;
    private int stationId;
    private String stationName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateTime;

    private String vehicleId;
    private String status;
    private int priceid;
    private double price_id;
    private String licensePlate;

    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    private int laneId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice_id() {
        return price_id;
    }

    public void setPrice_id(double price_id) {
        this.price_id = price_id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getUsernameId() {
        return usernameId;
    }

    public void setUsernameId(int usernameId) {
        this.usernameId = usernameId;
    }

    public int getPriceid() {
        return priceid;
    }

    public void setPriceid(int priceid) {
        this.priceid = priceid;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public static TransactionDTO convertFromEntity(Transaction transaction){
        TransactionDTO dto = new TransactionDTO();

        String id = transaction.getId();
        String username = transaction.getAccount().getUsername();
        int usernameId = transaction.getAccount().getId();
        int stationId = transaction.getStation().getId();
        String stationName = transaction.getStation().getName();
//        Date dateTime = (Date) transaction.getDateTime();
//        String day = transaction.getDateTime().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = transaction.getDateTime().toString();
//        try{
////            dateTime = (Date) format.parse(day);
////            dto.setDateTime(dateTime);
//            if(dateTime != null) {
//                dto.setDateTime(format.parse(String.valueOf(transaction.getDateTime().toString())));
//            }
//            System.out.println("dateTime: " + dateTime);
//
//        }catch (ParseException e){
//            e.printStackTrace();
//        }


        String vehicleId = transaction.getPrice().getVehicletype().getName();
        String status = transaction.getStatus();
        int priceId = transaction.getPrice().getId();
        double price = transaction.getPrice().getPrice();
        if (transaction.getLane() != null) {
            int laneId = transaction.getLane().getId();
            dto.setLaneId(laneId);
        }


        dto.setId(id);
        dto.setUsername(username);
        dto.setUsernameId(usernameId);
        dto.setStationId(stationId);
        dto.setStationName(stationName);
        try {
            dto.setDateTime(format.parse(String.valueOf(dateTime)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dto.setVehicleId(vehicleId);
        dto.setStatus(status);
        dto.setPrice_id(price);
        dto.setPriceid(priceId);



        return dto;
    }

//    public static Transaction convertToEntity(TransactionDTO transactionDTO){
//        String id = transactionDTO.getId();
//        int username_id = transactionDTO.getUsername_id();
//        int station_id = transactionDTO.getStation_id();
//        Date date_time = transactionDTO.getDate_time();
//        String status = transactionDTO.getStatus();
//        int price_id = transactionDTO.getPriceid();
//
//        Transaction tr = new Transaction();
//        tr.setId(id);
//        tr.setDateTime(date_time);
//        tr.setStatus(status);
//
//        return tr;
//
//    }
}
