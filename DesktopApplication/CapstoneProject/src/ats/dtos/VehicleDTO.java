/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.dtos;

/**
 *
 * @author Chi Hieu
 */
public class VehicleDTO {
    private String licensePlate;
    private String typeVehicle;
    private Double price;
    private int accountId;

    public VehicleDTO() {
    }

    public VehicleDTO(String licensePlate, String typeVehicle, Double price, int accountId) {
        this.licensePlate = licensePlate;
        this.typeVehicle = typeVehicle;
        this.price = price;
        this.accountId = accountId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(String typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    
    
}
