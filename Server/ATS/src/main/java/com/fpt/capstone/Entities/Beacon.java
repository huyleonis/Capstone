/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name = "beacon")
public class Beacon implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "major")
	private int major;

	@Column(name = "minor")
	private int minor;

	@ManyToOne
	@JoinColumn(name = "stationid")
	private Station station;

	@OneToOne
	@JoinColumn(name = "laneid")
	private Lane lane;

	@Column(name = "type")
	private int type;

	@Column(name = "isactive")
	private Boolean isActive;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Lane getLane() {
		return lane;
	}

	public void setLane(Lane lane) {
		this.lane = lane;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
