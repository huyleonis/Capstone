/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "price")
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "station_id")
	private Station station;

	@Column(name = "price")
	private double price;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private Vehicletype vehicletype;

	@Column(name = "from_date")
	private Date fromDate;

	@Column(name = "is_active")
	private int active;

	@OneToMany(mappedBy = "price")
	private List<Transaction> transactions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Vehicletype getVehicletype() {
		return vehicletype;
	}

	public void setVehicletype(Vehicletype vehicletype) {
		this.vehicletype = vehicletype;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
