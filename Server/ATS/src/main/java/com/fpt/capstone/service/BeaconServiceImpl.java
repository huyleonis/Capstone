package com.fpt.capstone.service;

import com.fpt.capstone.dto.BeaconDTO;
import com.fpt.capstone.entity.Beacon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BeaconServiceImpl extends AbstractServiceImpl implements BeaconService {
    @Override
    public BeaconDTO insert(Beacon beacon) {
        BeaconDTO beaconDTO = null;

        Beacon processedBeacon = beaconRepos.save(beacon);
        if (processedBeacon != null) {
            beaconDTO = BeaconDTO.convertFromEntity(processedBeacon);
        }

        return beaconDTO;
    }

    @Override
    public BeaconDTO update(Beacon beacon) {
        BeaconDTO beaconDTO = null;

        Beacon existingBeacon = beaconRepos.findOne(beacon.getId());

        if (existingBeacon != null) {
            Beacon processedBeacon = beaconRepos.save(beacon);
            beaconDTO = BeaconDTO.convertFromEntity(processedBeacon);
        }

        return beaconDTO;
    }

    @Override
    public BeaconDTO getBeaconById(int id) {
        Beacon beacon = beaconRepos.findOne(id);

        if (beacon != null) {
            BeaconDTO dto = BeaconDTO.convertFromEntity(beacon);
            return dto;
        }

        return null;
    }

    @Override
    public List<BeaconDTO> getAllBeacon() {
        List<Beacon> beacons = beaconRepos.findAll();
        List<BeaconDTO> dtos = new ArrayList<>();

        for (Beacon beacon : beacons) {
            dtos.add(BeaconDTO.convertFromEntity(beacon));
        }

        return dtos;
    }
}
