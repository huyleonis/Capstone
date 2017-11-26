package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.StationDTO;
import hackathon.fpt.ktk.entity.Station;

import java.util.List;

public interface StationService {

    List<StationDTO> getAllStation();

    StationDTO insert(Station station);

    StationDTO update(Station station);
}
