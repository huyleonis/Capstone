package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.LaneDTO;
import hackathon.fpt.ktk.entity.Lane;

import java.util.List;

public interface LaneService {

    List<LaneDTO> getAlllane();

    LaneDTO insert(Lane lane);

    LaneDTO update(Lane lane);
}
