package com.fpt.capstone.Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Chi Hieu
 */
@Entity
@Table(name = "price")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Price.findAll", query = "SELECT p FROM Price p")
    , @NamedQuery(name = "Price.findById", query = "SELECT p FROM Price p WHERE p.id = :id")
    , @NamedQuery(name = "Price.findByPrice", query = "SELECT p FROM Price p WHERE p.price = :price")
    , @NamedQuery(name = "Price.findByFromDate", query = "SELECT p FROM Price p WHERE p.fromDate = :fromDate")
    , @NamedQuery(name = "Price.findByIsActive", query = "SELECT p FROM Price p WHERE p.isActive = :isActive")})
public class Price implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "price")
    private double price;
    @Column(name = "from_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;
    @Basic(optional = false)
    @Column(name = "isActive")
    private boolean isActive;
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Station stationId;
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Vehicletype typeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priceId")
    private Collection<Transaction> transactionCollection;

    public Price() {
    }

    public Price(Integer id) {
        this.id = id;
    }

    public Price(Integer id, double price, boolean isActive) {
        this.id = id;
        this.price = price;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Station getStationId() {
        return stationId;
    }

    public void setStationId(Station stationId) {
        this.stationId = stationId;
    }

    public Vehicletype getTypeId() {
        return typeId;
    }

    public void setTypeId(Vehicletype typeId) {
        this.typeId = typeId;
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
        if (!(object instanceof Price)) {
            return false;
        }
        Price other = (Price) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fpt.capstone.Entities.Price[ id=" + id + " ]";
    }
    
}
