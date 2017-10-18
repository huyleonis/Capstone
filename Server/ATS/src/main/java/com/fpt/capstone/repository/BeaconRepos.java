package com.fpt.capstone.repository;

import com.fpt.capstone.entity.Beacon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeaconRepos extends JpaRepository<Beacon, Integer> {

}
