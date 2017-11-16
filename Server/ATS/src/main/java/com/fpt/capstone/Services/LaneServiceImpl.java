package com.fpt.capstone.Services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.capstone.Dtos.LaneDTO;
import com.fpt.capstone.Entities.Lane;
import com.fpt.capstone.Repositories.LaneRepos;

@Service
public class LaneServiceImpl implements LaneService {

	private final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	LaneRepos laneRepos;

	@Override
	public LaneDTO getLaneByBeacon(String uuid) {
		Lane lane = laneRepos.getLaneByBeacon(uuid);
		if (lane != null) {
			return LaneDTO.convertFromEntity(lane);
		}

		return null;
	}

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
			log.error(e.getMessage());
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
			log.error(e.getMessage());
		}

		return dto;
	}

}
