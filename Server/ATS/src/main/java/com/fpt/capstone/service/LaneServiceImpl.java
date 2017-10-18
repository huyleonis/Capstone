package com.fpt.capstone.service;

import com.fpt.capstone.dto.LaneDTO;
import com.fpt.capstone.entity.Lane;
import com.fpt.capstone.repository.LaneRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaneServiceImpl implements LaneService {

    @Autowired
    LaneRepos laneRepos;

    @Override
    public LaneDTO getLaneByBeacon(String uuid) {
        Lane lane =  laneRepos.getLaneByBeacon(uuid);
        if (lane != null) {
            return LaneDTO.convertFromEntity(lane);
        }

        return null;
    }
}
