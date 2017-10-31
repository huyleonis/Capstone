package com.fpt.capstone.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "station")
public class Station implements Serializable
{

    @Id
    private int id;

    private String location;

    private String name;

    private String zone;

    //bi-directional many-to-one association to Beacon
    @OneToMany(mappedBy="station")
    private List<Beacon> beacons;

    //bi-directional many-to-one association to Lane
    @OneToMany(mappedBy="station")
    private List<Lane> lanes;

    //bi-directional many-to-one association to Price
    @OneToMany(mappedBy="station")
    private List<Price> prices;

    //bi-directional many-to-one association to Transaction
    @OneToMany(mappedBy="station")
    private List<Transaction> transactions;

    public Station() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone() {
        return this.zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public List<Beacon> getBeacons() {
        return this.beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }

    public Beacon addBeacon(Beacon beacon) {
        getBeacons().add(beacon);
        beacon.setStation(this);

        return beacon;
    }

    public Beacon removeBeacon(Beacon beacon) {
        getBeacons().remove(beacon);
        beacon.setStation(null);

        return beacon;
    }

    public List<Lane> getLanes() {
        return this.lanes;
    }

    public void setLanes(List<Lane> lanes) {
        this.lanes = lanes;
    }

    public Lane addLane(Lane lane) {
        getLanes().add(lane);
        lane.setStation(this);

        return lane;
    }

    public Lane removeLane(Lane lane) {
        getLanes().remove(lane);
        lane.setStation(null);

        return lane;
    }

    public List<Price> getPrices() {
        return this.prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public Price addPrice(Price price) {
        getPrices().add(price);
        price.setStation(this);

        return price;
    }

    public Price removePrice(Price price) {
        getPrices().remove(price);
        price.setStation(null);

        return price;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Transaction addTransaction(Transaction transaction) {
        getTransactions().add(transaction);
        transaction.setStation(this);

        return transaction;
    }

    public Transaction removeTransaction(Transaction transaction) {
        getTransactions().remove(transaction);
        transaction.setStation(null);

        return transaction;
    }
}
