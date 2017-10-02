package com.fpt.capstone.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "station")
public class StationEntity {

    @Id
    @Column
    private Integer IdStation;

    @Column
    private String Name;

    @Column
    private String Location;

    public Integer getIdStation() {
        return IdStation;
    }

    public void setIdStation(Integer idStation) {
        IdStation = idStation;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
