package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.StationDTO;

import java.util.List;

public interface StationService {
//    List<StationDTO> getall();
    StationDTO findByUuid(String uuid);
}
