package com.fpt.capstone.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "vehicletype")
public class Vehicletype {

    @Id
    private int id;

    private String name;

    //bi-directional many-to-one association to Price
    @OneToMany(mappedBy="vehicletype")
    private List<Price> prices;

    //bi-directional many-to-one association to Vehicle
    @OneToMany(mappedBy="vehicletype")
    private List<Vehicle> vehicles;

    public Vehicletype() {
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

    public List<Price> getPrices() {
        return this.prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public Price addPrice(Price price) {
        getPrices().add(price);
        price.setVehicletype(this);

        return price;
    }

    public Price removePrice(Price price) {
        getPrices().remove(price);
        price.setVehicletype(null);

        return price;
    }

    public List<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        getVehicles().add(vehicle);
        vehicle.setVehicletype(this);

        return vehicle;
    }

    public Vehicle removeVehicle(Vehicle vehicle) {
        getVehicles().remove(vehicle);
        vehicle.setVehicletype(null);

        return vehicle;
    }
}
