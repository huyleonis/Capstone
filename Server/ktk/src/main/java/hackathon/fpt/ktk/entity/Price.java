package hackathon.fpt.ktk.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "fromdate")
    private Date fromDate;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "stationid")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "typeid")
    private VehicleType vehicleType;

    @Column(name = "isactive")
    private Boolean isActive;

    @OneToMany(mappedBy = "price")
    private List<Transaction> transactions;

    public Price() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
