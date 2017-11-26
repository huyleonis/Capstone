package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.StationDTO;
import hackathon.fpt.ktk.entity.Station;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl extends AbstractServiceImpl implements StationService {

    @Override
    public List<StationDTO> getAllStation() {

        List<Station> stations = stationRepos.findAll();
        List<StationDTO> dtos = new ArrayList<>();

        for (Station station : stations) {
            StationDTO dto = StationDTO.convertFromEntity(station);
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public StationDTO insert(Station station) {
        StationDTO dto = null;

        try {
            Station processedStation = stationRepos.save(station);
            if (processedStation != null) {
                dto = StationDTO.convertFromEntity(processedStation);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dto;
    }

    @Override
    public StationDTO update(Station station) {
        StationDTO dto = null;

        try {
            Station existingStation = stationRepos.findOne(station.getId());

            if (existingStation != null) {
                Station processedStation = stationRepos.save(station);
                dto = StationDTO.convertFromEntity(processedStation);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dto;
    }
}
