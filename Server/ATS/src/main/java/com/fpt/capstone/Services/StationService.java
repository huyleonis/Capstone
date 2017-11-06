package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.StationDTO;
import com.fpt.capstone.Entities.Station;

import java.util.List;

public interface StationService {
	
    StationDTO findByUuid(String uuid);
    
    List<StationDTO> getAllStation();
    
    StationDTO insert(Station station);
    
    StationDTO update(Station station);
}
