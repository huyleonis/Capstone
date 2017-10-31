package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.LaneDTO;

public interface LaneService {
    LaneDTO getLaneByBeacon(String uuid);
}
