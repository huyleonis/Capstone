package com.fpt.capstone.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "vehicletype")
public class VehicleType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "vehicleType")
    private List<Price> prices;

    @OneToMany(mappedBy = "vehicleType")
    private List<Vehicle> vehicles;

    public VehicleType() {
    }

    public VehicleType(String name, List<Price> prices, List<Vehicle> vehicles) {
        this.name = name;
        this.prices = prices;
        this.vehicles = vehicles;
    }

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

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
