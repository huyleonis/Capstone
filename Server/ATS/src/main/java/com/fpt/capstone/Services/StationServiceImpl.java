package com.fpt.capstone.Services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.capstone.Dtos.StationDTO;
import com.fpt.capstone.Entities.Station;
import com.fpt.capstone.Repositories.StationRepos;

@Service
public class StationServiceImpl implements StationService {

	private final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private StationRepos stationRepos;

	@Override
	public StationDTO findByUuid(String uuid) {
		Station station = stationRepos.findByUuid(uuid);
		if (station != null) {
			StationDTO stationDTO = StationDTO.convertFromEntity(station);
			return stationDTO;
		} else {
			return null;
		}

	}

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
			log.error(e.getMessage());
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
			log.error(e.getMessage());
		}

		return dto;
	}

}
