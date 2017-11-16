package com.fpt.capstone.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.capstone.Entities.Station;

@Repository
public interface StationRepos extends JpaRepository<Station, Integer>{
//    @Query(value = "select * from station", nativeQuery = true)
//    List<Station> getall();

    @Query(value = "select * from station where uuid like ?1", nativeQuery = true)
    Station findByUuid(String uuid);

    @Query(value = "select stationId from lane where id = ?1", nativeQuery = true)
    int getStationIdOfLane(int laneId);
}
