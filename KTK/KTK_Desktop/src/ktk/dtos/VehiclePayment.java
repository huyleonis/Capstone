/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ktk.dtos;

/**
 *
 * @author Chi Hieu
 */
public class VehiclePayment {
    private String id;
    private String licensePlate;
    private String typeName;
    private String status;
    private String photo;
    private double fee;

    public VehiclePayment() {
    }

    public VehiclePayment(String id, String licensePlate, String typeName, String status, String photo, double fee) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.typeName = typeName;
        this.status = status;
        this.photo = photo;
        this.fee = fee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
    
    
}
