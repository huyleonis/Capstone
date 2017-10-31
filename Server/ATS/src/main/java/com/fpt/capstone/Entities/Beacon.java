package com.fpt.capstone.Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chi Hieu
 */
@Entity
@Table(name = "beacon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Beacon.findAll", query = "SELECT b FROM Beacon b")
    , @NamedQuery(name = "Beacon.findById", query = "SELECT b FROM Beacon b WHERE b.id = :id")
    , @NamedQuery(name = "Beacon.findByUuid", query = "SELECT b FROM Beacon b WHERE b.uuid = :uuid")
    , @NamedQuery(name = "Beacon.findByMajor", query = "SELECT b FROM Beacon b WHERE b.major = :major")
    , @NamedQuery(name = "Beacon.findByMinor", query = "SELECT b FROM Beacon b WHERE b.minor = :minor")
    , @NamedQuery(name = "Beacon.findByType", query = "SELECT b FROM Beacon b WHERE b.type = :type")})
public class Beacon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "uuid")
    private String uuid;
    @Basic(optional = false)
    @Column(name = "major")
    private int major;
    @Basic(optional = false)
    @Column(name = "minor")
    private int minor;
    @Basic(optional = false)
    @Column(name = "type")
    private boolean type;
    @JoinColumn(name = "lane_id", referencedColumnName = "id")
    @ManyToOne
    private Lane laneId;
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Station stationId;

    public Beacon() {
    }

    public Beacon(Integer id) {
        this.id = id;
    }

    public Beacon(Integer id, String uuid, int major, int minor, boolean type) {
        this.id = id;
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public Lane getLaneId() {
        return laneId;
    }

    public void setLaneId(Lane laneId) {
        this.laneId = laneId;
    }

    public Station getStationId() {
        return stationId;
    }

    public void setStationId(Station stationId) {
        this.stationId = stationId;
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
        if (!(object instanceof Beacon)) {
            return false;
        }
        Beacon other = (Beacon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fpt.capstone.Entities[ id=" + id + " ]";
    }
    
}
