package com.fpt.capstone.service;

import com.fpt.capstone.dto.BeaconDTO;
import com.fpt.capstone.entity.Beacon;

import java.util.List;

public interface BeaconService {

    BeaconDTO insert(Beacon beacon);

    BeaconDTO update(Beacon beacon);

    BeaconDTO getBeaconById(int id);

    List<BeaconDTO> getAllBeacon();
}
