package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepos extends JpaRepository<Vehicle, String> {

    @Query(value = "select * from vehicle", nativeQuery = true)
    List<Vehicle> getall();

    @Query(value = "select * from ats.vehicle where LicensePlate ilike '?1' ", nativeQuery = true)
    public Vehicle findByVehicle(String vehicle);
}