package com.fpt.capstone.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.capstone.Entities.Vehicletype;

@Repository
public interface VehicletypeRepos extends JpaRepository<Vehicletype, Integer> {

}
