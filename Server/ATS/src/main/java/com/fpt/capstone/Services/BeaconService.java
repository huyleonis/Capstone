package com.fpt.capstone.Services;

import java.util.List;

import com.fpt.capstone.Dtos.BeaconDTO;
import com.fpt.capstone.Entities.Beacon;

public interface BeaconService {        

    List<BeaconDTO> getAllBeacon();

    BeaconDTO insert(Beacon beacon);

    BeaconDTO update(Beacon beacon);

    boolean active(Beacon beacon);

    boolean deactive(Beacon beacon);
}
