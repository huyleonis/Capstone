package com.fpt.capstone.Services;

import java.util.List;

import com.fpt.capstone.Dtos.LaneDTO;
import com.fpt.capstone.Entities.Lane;

public interface LaneService {
	
    LaneDTO getLaneByBeacon(String uuid);
    
    List<LaneDTO> getAlllane();
    
    List<LaneDTO> getLanesByStation(int stationId);
    
    LaneDTO insert(Lane lane);
    
    LaneDTO update(Lane lane);
}
