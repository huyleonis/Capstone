package com.fpt.capstone.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpt.capstone.Entities.Lane;

@Repository
public interface LaneRepos extends JpaRepository<Lane, Integer> {
    
    @Query(value = "select * from lane where id = " +
            "(select lane_id from beacon where uuid = ?1)", nativeQuery = true)
    Lane getLaneByBeacon(String uuid);
    
    @Query(value = "select * from lane where station_id = :stationId", nativeQuery = true)
    List<Lane> findByStationId(@Param("stationId") int stationId);
}
