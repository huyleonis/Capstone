/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chi Hieu
 */
@Entity
@Table(name = "transaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t")
    , @NamedQuery(name = "Transaction.findById", query = "SELECT t FROM Transaction t WHERE t.id = :id")
    , @NamedQuery(name = "Transaction.findByDateTime", query = "SELECT t FROM Transaction t WHERE t.dateTime = :dateTime")
    , @NamedQuery(name = "Transaction.findByStatus", query = "SELECT t FROM Transaction t WHERE t.status = :status")
    , @NamedQuery(name = "Transaction.findByType", query = "SELECT t FROM Transaction t WHERE t.type = :type")
    , @NamedQuery(name = "Transaction.findByPhoto", query = "SELECT t FROM Transaction t WHERE t.photo = :photo")})
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "status")
    private String status;
    @Column(name = "type")
    private Boolean type;
    @Size(max = 45)
    @Column(name = "photo")
    private String photo;
    @JoinColumn(name = "lane_id", referencedColumnName = "id")
    @ManyToOne
    private Lane laneId;
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Price priceId;
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Station stationId;
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Vehicle vehicleId;

    public Transaction() {
    }

    public Transaction(String id) {
        this.id = id;
    }

    public Transaction(String id, Date dateTime, String status) {
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Lane getLaneId() {
        return laneId;
    }

    public void setLaneId(Lane laneId) {
        this.laneId = laneId;
    }

    public Price getPriceId() {
        return priceId;
    }

    public void setPriceId(Price priceId) {
        this.priceId = priceId;
    }

    public Station getStationId() {
        return stationId;
    }

    public void setStationId(Station stationId) {
        this.stationId = stationId;
    }

    public Vehicle getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Vehicle vehicleId) {
        this.vehicleId = vehicleId;
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
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fpt.capstone.Entities.Transaction[ id=" + id + " ]";
    }
    
}
