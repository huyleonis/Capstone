package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.BeaconDTO;
import hackathon.fpt.ktk.entity.Beacon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BeaconServiceImpl extends AbstractServiceImpl implements BeaconService {

    @Override
    public BeaconDTO getBeaconByUuidAndMajorAndMinor(String uuid, int major, int minor) {

        Beacon beacon = beaconRepos.findByUuidAndMajorAndMinor(uuid, major, minor);

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

        if (!beacons.isEmpty()) {
            for (Beacon beacon : beacons) {
                BeaconDTO dto = BeaconDTO.convertFromEntity(beacon);
                dtos.add(dto);
            }

            return dtos;
        }

        return null;
    }

    @Override
    public BeaconDTO insert(Beacon beacon) {
        BeaconDTO dto = null;

        try {
            Beacon processedBeacon = beaconRepos.save(beacon);
            if (processedBeacon != null) {
                dto = BeaconDTO.convertFromEntity(processedBeacon);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dto;
    }

    @Override
    public BeaconDTO update(Beacon beacon) {
        BeaconDTO dto = null;

        try {
            Beacon exisitingBeacon = beaconRepos.findOne(beacon.getId());

            if (exisitingBeacon != null) {
                Beacon processedBeacon = beaconRepos.save(beacon);
                dto = BeaconDTO.convertFromEntity(processedBeacon);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage());
        }

        return dto;
    }
}
