package hackathon.fpt.ktk.entity;

import javax.persistence.*;

@Entity(name = "beacon")
public class Beacon {

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

    public Beacon() {
    }

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

    public void setActive(Boolean active) {
        isActive = active;
    }
}
