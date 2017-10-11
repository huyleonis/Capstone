package com.fpt.capstone.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lane")
public class Lane {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    //bi-directional many-to-one association to Station
    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
