package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Lane;

public class LaneDTO {

    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static LaneDTO convertFromEntity(Lane lane){
        LaneDTO dto = new LaneDTO();

        int id = lane.getId();

        dto.setId(id);
        return dto;
    }
}
