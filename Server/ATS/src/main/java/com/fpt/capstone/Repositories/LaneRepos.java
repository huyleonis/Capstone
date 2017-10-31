package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.Account;
import com.fpt.capstone.Entities.Lane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LaneRepos extends JpaRepository<Lane, Integer> {
    
    @Query(value = "select * from lane where id = " +
            "(select lane_id from beacon where uuid = ?1)", nativeQuery = true)
    Lane getLaneByBeacon(String uuid);
}

