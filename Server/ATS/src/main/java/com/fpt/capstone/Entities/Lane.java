package com.fpt.capstone.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lane")
public class Lane {

    @Id
    private int id;

    private String name;

    //bi-directional many-to-one association to Beacon
    @OneToMany(mappedBy="lane")
    private List<Beacon> beacons;

    //bi-directional many-to-one association to Station
    @ManyToOne
    private Station station;

    //bi-directional many-to-one association to Transaction
    @OneToMany(mappedBy="lane")
    private List<Transaction> transactions;

    public Lane() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Beacon> getBeacons() {
        return this.beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }

    public Beacon addBeacon(Beacon beacon) {
        getBeacons().add(beacon);
        beacon.setLane(this);

        return beacon;
    }

    public Beacon removeBeacon(Beacon beacon) {
        getBeacons().remove(beacon);
        beacon.setLane(null);

        return beacon;
    }

    public Station getStation() {
        return this.station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Transaction addTransaction(Transaction transaction) {
        getTransactions().add(transaction);
        transaction.setLane(this);

        return transaction;
    }

    public Transaction removeTransaction(Transaction transaction) {
        getTransactions().remove(transaction);
        transaction.setLane(null);

        return transaction;
    }
}
