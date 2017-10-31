package com.fpt.capstone.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "beacon")
public class Beacon {

    @Id
    private int id;

    private int major;

    private int minor;

    private int type;

    private String uuid;

    //bi-directional many-to-one association to Lane
    @ManyToOne
    private Lane lane;

    //bi-directional many-to-one association to Station
    @ManyToOne
    private Station station;

    public Beacon() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMajor() {
        return this.major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return this.minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Lane getLane() {
        return this.lane;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

    public Station getStation() {
        return this.station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
