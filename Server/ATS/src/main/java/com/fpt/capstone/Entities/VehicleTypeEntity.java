package com.fpt.capstone.Entities;

import javax.persistence.*;

@Entity(name = "vehicletype")
public class VehicleTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdType")
    private Integer idType;

    @Column(name = "NameType")
    private String nameType;

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }
}
