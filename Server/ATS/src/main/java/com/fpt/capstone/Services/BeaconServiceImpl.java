package com.fpt.capstone.Services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.capstone.Dtos.BeaconDTO;
import com.fpt.capstone.Entities.Beacon;
import com.fpt.capstone.Repositories.BeaconRepos;

@Service
public class BeaconServiceImpl implements BeaconService {

	private final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private BeaconRepos beaconRepos;

	@Override
	public List<BeaconDTO> getAllBeacon() {

		List<Beacon> beacons = beaconRepos.findAll();
		List<BeaconDTO> dtos = new ArrayList<>();
		
		for (Beacon beacon : beacons) {
			BeaconDTO dto = BeaconDTO.convertFromEntity(beacon);
			dtos.add(dto);
		}

		return dtos;
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
			log.error(e.getMessage());
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
			log.error(e.getMessage());
		}

		return dto;
	}

	@Override
	public boolean active(Beacon beacon) {
		Beacon existingBeacon = beaconRepos.findOne(beacon.getId());
		if (existingBeacon != null) {
			existingBeacon.setActive(true);
			Beacon processedBeacon = beaconRepos.save(existingBeacon);
			if (processedBeacon != null) {
				System.out.println(processedBeacon.getActive());
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean deactive(Beacon beacon) {
		Beacon existingBeacon = beaconRepos.findOne(beacon.getId());
		if (existingBeacon != null) {
			existingBeacon.setActive(false);
			Beacon processedBeacon = beaconRepos.save(existingBeacon);
			if (processedBeacon != null) {
				System.out.println(processedBeacon.getActive());
				return true;
			}
		}

		return false;
	}
}
