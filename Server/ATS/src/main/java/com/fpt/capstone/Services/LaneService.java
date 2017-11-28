package com.fpt.capstone.Services;

import java.util.List;

import com.fpt.capstone.Dtos.LaneDTO;
import com.fpt.capstone.Entities.Lane;

public interface LaneService {
    LaneDTO getLaneByBeacon(String uuid);
    
    List<LaneDTO> getAlllane();
    
    LaneDTO insert(Lane lane);
    
    LaneDTO update(Lane lane);

    boolean active(Lane lane);

    boolean deactive(Lane lane);
}
