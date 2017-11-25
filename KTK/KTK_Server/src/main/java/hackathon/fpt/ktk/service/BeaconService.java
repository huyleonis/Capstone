package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.BeaconDTO;
import hackathon.fpt.ktk.entity.Beacon;

import java.util.List;

public interface BeaconService {

    BeaconDTO getBeaconByUuidAndMajorAndMinor(String uuid, int major, int minor);

    List<BeaconDTO> getAllBeacon();

    BeaconDTO insert(Beacon beacon);

    BeaconDTO update(Beacon beacon);
}
