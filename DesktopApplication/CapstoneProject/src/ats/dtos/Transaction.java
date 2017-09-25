/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.dtos;

import java.sql.Date;

/**
 *
 * @author Chi Hieu
 */
public class Transaction {
    private int idTransaction;
    private String username, licensePlate;
    private double fee;
    private Date dateOfTransaction;

    public Transaction() {
    }

    public Transaction(int idTransaction, String username, String licensePlate, double fee, Date dateOfTransaction) {
        this.idTransaction = idTransaction;
        this.username = username;
        this.licensePlate = licensePlate;
        this.fee = fee;
        this.dateOfTransaction = dateOfTransaction;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public Date getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(Date dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }
    
    
}
