package com.fpt.capstone.service;

import com.fpt.capstone.dto.LaneDTO;

public interface LaneService {
    LaneDTO getLaneByBeacon(String uuid);
}
