/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.VehicleType;

/**
 *
 * @author hp
 */
public class VehicleTypesDTO {
    private int id;
    private String name;

    public VehicleTypesDTO() {
    }

    public VehicleTypesDTO(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static VehicleTypesDTO convertFromEntity(VehicleType vehicletype) {

        VehicleTypesDTO dto = new VehicleTypesDTO();

        dto.setId(vehicletype.getId());
        dto.setName(vehicletype.getName());

        return dto;
    }
}
