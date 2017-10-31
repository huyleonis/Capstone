package com.fpt.capstone.dto;

import com.fpt.capstone.entity.Station;

public class StationDTO{
    private int id;
    private String location;
    private String name;
    private String zone;
//    private String account;
//    private String uuid;

//    public String getAccount() {
//        return account;
//    }

//    public void setAccount(String account) {
//        this.account = account;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

//    public String getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(String uuid) {
//        this.uuid = uuid;
//    }

    public static StationDTO convertFromEntity(Station station){
        StationDTO dto = new StationDTO();

//        String accountID = station.getAccount().getUsername();
        if(station.getId() != -1){
            dto.setId((station.getId()));
        }
        if(station.getName() != null){
            dto.setName(station.getName());
        }
        if(station.getLocation() != null){
            dto.setLocation(station.getLocation());
        }
//        if(station.getUuid() != null){
//            dto.setUuid(station.getUuid());
//        }

        if (station.getZone() != null){
            dto.setZone(station.getZone());
        }

//        dto.setAccount(accountID);

        return dto;
    }
}
