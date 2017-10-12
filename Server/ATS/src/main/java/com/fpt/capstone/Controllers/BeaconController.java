/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Controllers;

import com.fpt.capstone.Dtos.BeaconDTO;
import com.fpt.capstone.Entities.Beacon;
import com.fpt.capstone.Repositories.BeaconRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/beacon")
public class BeaconController {
    
    @Autowired
    private BeaconRepos beaconRepos;
    
    @RequestMapping(value = "get/{uuid}/{major}/{minor}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BeaconDTO getBeacon(@PathVariable String uuid, 
            @PathVariable String major, @PathVariable String minor) {

        System.out.println("Get Beacon information: " + uuid + " - " + major + " - " + minor);
        int iMajor = Integer.parseInt(major);
        int iMinor = Integer.parseInt(minor);
        
        Beacon beaconEntity = beaconRepos.getBeacon(uuid, iMajor, iMinor);
        if (beaconEntity != null) {
            BeaconDTO dto = BeaconDTO.convertFromEntity(beaconEntity);
            System.out.println("Get beacon info - type " + dto.getType());
            return dto;
        } else {
            BeaconDTO dto = new BeaconDTO();
            dto.setId(0);
            dto.setUuid(uuid);
            dto.setMajor(iMajor);
            dto.setMinor(iMinor);
            dto.setType("other");
            return dto;
        }        
        
    }
}
