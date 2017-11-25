package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.LaneDTO;
import hackathon.fpt.ktk.entity.Lane;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LaneServiceImpl extends AbstractServiceImpl implements LaneService {

    @Override
    public List<LaneDTO> getAlllane() {
        List<Lane> lanes = laneRepos.findAll();
        List<LaneDTO> dtos = new ArrayList<>();

        for (Lane lane : lanes) {
            LaneDTO dto = LaneDTO.convertFromEntity(lane);
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public LaneDTO insert(Lane lane) {

        LaneDTO dto = null;

        try {
            Lane processedLane = laneRepos.save(lane);
            if (processedLane != null) {
                dto = LaneDTO.convertFromEntity(processedLane);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dto;
    }

    @Override
    public LaneDTO update(Lane lane) {

        LaneDTO dto = null;

        try {
            Lane existingLane = laneRepos.findOne(lane.getId());

            if (existingLane != null) {
                Lane processedLane = laneRepos.save(lane);
                dto = LaneDTO.convertFromEntity(processedLane);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dto;
    }
}
