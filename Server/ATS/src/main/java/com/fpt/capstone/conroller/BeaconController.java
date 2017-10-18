package com.fpt.capstone.conroller;

import com.fpt.capstone.constant.Constant;
import com.fpt.capstone.dto.BeaconDTO;
import com.fpt.capstone.dto.ResponseDTO;
import com.fpt.capstone.entity.Beacon;
import com.fpt.capstone.entity.Station;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/beacon")
public class BeaconController extends AbstractController {

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO create(Beacon beacon) {

        beacon.setUuid("7777777");
        beacon.setMajor(8888);
        beacon.setMinor(8888);
        beacon.setType(1);

        Station station = new Station();
        station.setId(2);
        beacon.setStation(station);

        logger.info("\nCREATE BEACON: " + beacon);
        BeaconDTO beaconDTO = beaconService.insert(beacon);
        if (beaconDTO != null) {
            ResponseDTO responseDTO =
                    new ResponseDTO(Constant.Code.CREATE_BEACON_SUCCESS, "Tao Beacon Thanh Cong");
            responseDTO.addObject(Constant.JsonName.BEACON, beaconDTO);
            return responseDTO;
        }

        return new ResponseDTO(Constant.Code.CREATE_BEACON_FAIL, "Tao Beacon That Bai");
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO update(Beacon beacon) {

        beacon.setId(8);
        beacon.setUuid("7777777");
        beacon.setMajor(8888);
        beacon.setMinor(8888);
        beacon.setType(0);

        Station station = new Station();
        station.setId(2);
        beacon.setStation(station);

        logger.info("\nEDIT BEACON: " + beacon);
        BeaconDTO beaconDTO = beaconService.update(beacon);
        if (beaconDTO != null) {
            ResponseDTO responseDTO = new ResponseDTO(Constant.Code.EDIT_BEACON_SUCCESS, "Cap Nhat Beacon Thanh Cong");
            responseDTO.addObject(Constant.JsonName.BEACON, beaconDTO);
            return responseDTO;
        }

        return new ResponseDTO(Constant.Code.EDIT_BEACON_FAIL, "Cap Nhat Beacon That Bai");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO read(@PathVariable int id) {

        logger.info("\nVIEW BEACON ID: " + id);
        BeaconDTO beaconDTO = beaconService.getBeaconById(id);
        if (beaconDTO != null) {
            ResponseDTO responseDTO = new ResponseDTO(Constant.Code.GET_BEACON_SUCCESS, "Tim Beacon Thanh Cong");
            responseDTO.addObject(Constant.JsonName.BEACON, beaconDTO);
            return responseDTO;
        }

        return new ResponseDTO(Constant.Code.GET_BEACON_FAIL, "Tim Beacon That Bai");
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO readAll() {

        logger.info("\nVIEW ALL BEACON");
        List<BeaconDTO> dtos = beaconService.getAllBeacon();
        if (!dtos.isEmpty()) {
            ResponseDTO responseDTO = new ResponseDTO(Constant.Code.GET_BEACON_SUCCESS, "Tim Beacon Thanh Cong");
            responseDTO.addObject(Constant.JsonName.BEACON, dtos);
            return responseDTO;
        }

        return new ResponseDTO(Constant.Code.GET_BEACON_FAIL, "Tim Beacon That Bai");
    }
}
