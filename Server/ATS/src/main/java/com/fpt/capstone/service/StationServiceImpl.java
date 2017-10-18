package com.fpt.capstone.service;

import com.fpt.capstone.dto.StationDTO;
import com.fpt.capstone.entity.Station;
import com.fpt.capstone.repository.StationRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationRepos stationRepos;

    @Override
    public StationDTO findByUuid(String uuid) {
        Station station = stationRepos.findByUuid(uuid);
        if(station != null){
            StationDTO stationDTO = StationDTO.convertFromEntity(station);
            return stationDTO;
        }else {
            return null;
        }

    }
}
