package com.fpt.capstone.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.capstone.Repositories.VehicleRepos;

@Service
public class VehicleServiceImpl implements VehicleService {
	
	@Autowired
	private VehicleRepos vehicleRepos;

}
