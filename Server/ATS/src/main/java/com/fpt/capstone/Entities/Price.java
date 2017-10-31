package com.fpt.capstone.Entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "price")
public class Price {

    @Id
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="from_date")
    private Date fromDate;

    private double price;

    //bi-directional many-to-one association to Station
    @ManyToOne
    private Station station;

    //bi-directional many-to-one association to Vehicletype
    @ManyToOne
    @JoinColumn(name="type_id")
    private Vehicletype vehicletype;

    //bi-directional many-to-one association to Transaction
    @OneToMany(mappedBy="price")
    private List<Transaction> transactions;

    public Price() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Station getStation() {
        return this.station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Vehicletype getVehicletype() {
        return this.vehicletype;
    }

    public void setVehicletype(Vehicletype vehicletype) {
        this.vehicletype = vehicletype;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Transaction addTransaction(Transaction transaction) {
        getTransactions().add(transaction);
        transaction.setPrice(this);

        return transaction;
    }

    public Transaction removeTransaction(Transaction transaction) {
        getTransactions().remove(transaction);
        transaction.setPrice(null);

        return transaction;
    }
}
