package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Transaction;

import java.util.Date;

public class TransactionDTO {

    private String TransactionId;
    private int usernameId;
    private int stationId;
    private Date dateTime;
    private String status;
    private int priceId;
    private int laneId;
    private int type;
    private String photo;
    private String vehicleType;
    private Double price;
    private String licensePlate;
    private int accountId;

    public TransactionDTO() {
    }

    public TransactionDTO(String TransactionId, int usernameId, int stationId, Date dateTime, String status, int priceId, int laneId, int type, String photo, String vehicleType, Double price, String licensePlate, int accountId) {
        this.TransactionId = TransactionId;
        this.usernameId = usernameId;
        this.stationId = stationId;
        this.dateTime = dateTime;
        this.status = status;
        this.priceId = priceId;
        this.laneId = laneId;
        this.type = type;
        this.photo = photo;
        this.vehicleType = vehicleType;
        this.price = price;
        this.licensePlate = licensePlate;
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public int getUsernameId() {
        return usernameId;
    }

    public void setUsernameId(int usernameId) {
        this.usernameId = usernameId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static TransactionDTO convertFromEntity(Transaction transaction) {

        TransactionDTO dto = new TransactionDTO();

        dto.setTransactionId(transaction.getId());
        dto.setUsernameId(transaction.getAccount().getId());
        dto.setStationId(transaction.getStation().getId());
        dto.setDateTime(transaction.getDateTime());
        dto.setStatus(transaction.getStatus());
        dto.setPriceId(transaction.getPrice().getId());
//        dto.setLaneId(transaction.getLane().getId());
        dto.setLaneId(-1);
        dto.setType(-1);
        if (transaction.getPhoto() != null) {
            dto.setPhoto(transaction.getPhoto());
        } else {
            dto.setPhoto(null);
        }
        dto.setLicensePlate(transaction.getAccount().getVehicle().getLicensePlate());
        dto.setVehicleType(transaction.getAccount().getVehicle().getVehicletype().getName());
        dto.setPrice(transaction.getPrice().getPrice());
        dto.setAccountId(transaction.getAccount().getId());
        return dto;
    }

//    public static Transaction convertToEntity(TransactionDTO transactionDTO) {
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
