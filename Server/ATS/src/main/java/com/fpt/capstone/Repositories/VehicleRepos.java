package com.fpt.capstone.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpt.capstone.Entities.Vehicle;

@Repository
public interface VehicleRepos extends JpaRepository<Vehicle, Integer> {

    @Query(value = "select * from vehicle where licensePlate = :licensePlate", nativeQuery = true)
    Vehicle findByLicensePlate(@Param("licensePlate") String licensePlate);
}