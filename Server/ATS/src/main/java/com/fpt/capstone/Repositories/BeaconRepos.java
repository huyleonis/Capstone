/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.Beacon;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface BeaconRepos extends JpaRepository<Beacon, Integer>{
    
    /**
     * Get beacon according to its uuid and major and minor
     * @param uuid
     * @param major
     * @param minor
     * @return beacon
     */
    @Query(value = "select * "
            + "from beacon "
            + "where uuid = :uuid "
            + "and major = :major "
            + "and minor = :minor", nativeQuery = true)
    public Beacon getBeacon(@Param("uuid") String uuid, 
            @Param("major") int major, @Param("minor") int minor);
}
