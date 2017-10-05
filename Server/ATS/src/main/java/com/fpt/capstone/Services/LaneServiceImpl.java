package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.LaneDTO;
import com.fpt.capstone.Entities.Lane;
import com.fpt.capstone.Repositories.LaneRepos;
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
