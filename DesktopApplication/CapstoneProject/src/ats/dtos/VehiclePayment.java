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
public class VehiclePayment {
    private String licensePlate, typeName;
    private double fee;

    public VehiclePayment() {
    }

    public VehiclePayment(String typeName, double fee) {
        this.typeName = typeName;
        this.fee = fee;
    }

    public VehiclePayment(String licensePlate, String typeName, double fee) {
        this.licensePlate = licensePlate;
        this.typeName = typeName;
        this.fee = fee;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
    
    
}
