package com.fpt.capstone.Services;

import com.fpt.capstone.Dtos.StationDTO;
import com.fpt.capstone.Entities.Station;
import com.fpt.capstone.Repositories.StationRepos;
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
