/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Chi Hieu
 */
@Entity
@Table(name = "station")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Station.findAll", query = "SELECT s FROM Station s")
    , @NamedQuery(name = "Station.findById", query = "SELECT s FROM Station s WHERE s.id = :id")
    , @NamedQuery(name = "Station.findByName", query = "SELECT s FROM Station s WHERE s.name = :name")
    , @NamedQuery(name = "Station.findByLocation", query = "SELECT s FROM Station s WHERE s.location = :location")
    , @NamedQuery(name = "Station.findByZone", query = "SELECT s FROM Station s WHERE s.zone = :zone")
    , @NamedQuery(name = "Station.findByIsActive", query = "SELECT s FROM Station s WHERE s.isActive = :isActive")})
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "zone")
    private String zone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isActive")
    private boolean isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stationId")
    private Collection<Beacon> beaconCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stationId")
    private Collection<Price> priceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stationId")
    private Collection<Lane> laneCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stationId")
    private Collection<Transaction> transactionCollection;

    public Station() {
    }

    public Station(Integer id) {
        this.id = id;
    }

    public Station(Integer id, String name, String location, String zone, boolean isActive) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.zone = zone;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public Collection<Beacon> getBeaconCollection() {
        return beaconCollection;
    }

    public void setBeaconCollection(Collection<Beacon> beaconCollection) {
        this.beaconCollection = beaconCollection;
    }

    @XmlTransient
    public Collection<Price> getPriceCollection() {
        return priceCollection;
    }

    public void setPriceCollection(Collection<Price> priceCollection) {
        this.priceCollection = priceCollection;
    }

    @XmlTransient
    public Collection<Lane> getLaneCollection() {
        return laneCollection;
    }

    public void setLaneCollection(Collection<Lane> laneCollection) {
        this.laneCollection = laneCollection;
    }

    @XmlTransient
    public Collection<Transaction> getTransactionCollection() {
        return transactionCollection;
    }

    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
        this.transactionCollection = transactionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Station)) {
            return false;
        }
        Station other = (Station) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fpt.capstone.Entities.Station[ id=" + id + " ]";
    }
    
}
