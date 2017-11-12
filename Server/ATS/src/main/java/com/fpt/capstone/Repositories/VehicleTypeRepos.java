package com.fpt.capstone.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.capstone.Entities.VehicleType;

@Repository
public interface VehicleTypeRepos extends JpaRepository<VehicleType, Integer> {

}
