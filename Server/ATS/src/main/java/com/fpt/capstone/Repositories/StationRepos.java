package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepos extends JpaRepository<Station, Integer>{
//    @Query(value = "select * from station", nativeQuery = true)
//    List<Station> getall();

    @Query(value = "select * from station where uuid like ?1", nativeQuery = true)
    Station findByUuid(String uuid);
}
