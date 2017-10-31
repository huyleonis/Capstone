package com.fpt.capstone.service;

import com.fpt.capstone.dto.StationDTO;

public interface StationService {
//    List<StationDTO> getall();
    StationDTO findByUuid(String uuid);
}
