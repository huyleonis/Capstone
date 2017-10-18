package com.fpt.capstone.conroller;

import com.fpt.capstone.constant.Constant;
import com.fpt.capstone.dto.PriceDTO;
import com.fpt.capstone.dto.ResponseDTO;
import com.fpt.capstone.entity.Price;
import com.fpt.capstone.service.PriceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/price")
public class PriceController extends AbstractController {

    @RequestMapping(value = "findPriceDriver/{uuid}/{username}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PriceDTO findPriceByUuidAndUsername(@PathVariable String uuid, @PathVariable String username) {
        System.out.println("Get Price from Driver");
        System.out.println("    + UUID: " + uuid);
        System.out.println("    + username: " + username);

        return priceService.getPriceByUuidAndUsername(uuid, username);
    }

    @RequestMapping(value = "findPriceStaff/{stationId}/{licensePlate}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PriceDTO findPriceByStationIdAndLicensePlate(@PathVariable int stationId, @PathVariable String licensePlate) {
        return priceService.getPriceByStationIdAndLicensePlate(stationId, licensePlate);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO create(Price price) {

        logger.info("\nCREATE PRICE: " + price);
        PriceDTO priceDTO = priceService.insert(price);
        if (priceDTO != null) {
            ResponseDTO responseDTO =
                    new ResponseDTO(Constant.Code.CREATE_PRICE_SUCCESS, "Tao Phi Thanh Cong");
            responseDTO.addObject(Constant.JsonName.PRICE, priceDTO);
            return responseDTO;
        }

        return new ResponseDTO(Constant.Code.CREATE_PRICE_FAIL, "Tao Phi That Bai");
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO update(Price price) {

        logger.info("\nEDIT PRICE: " + price);
        PriceDTO priceDTO = priceService.update(price);
        if (priceDTO != null) {
            ResponseDTO responseDTO =
                    new ResponseDTO(Constant.Code.EDIT_PRICE_SUCCESS, "Cap Nhat Phi Thanh Cong");
            responseDTO.addObject(Constant.JsonName.PRICE, priceDTO);
            return responseDTO;
        }

        return new ResponseDTO(Constant.Code.EDIT_PRICE_FAIL, "Cap Nhat Phi That Bai");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO read(@PathVariable int id) {

        logger.info("\nVIEW PRICE ID: " + id);
        PriceDTO priceDTO = priceService.getPriceById(id);
        if (priceDTO != null) {
            ResponseDTO responseDTO =
                    new ResponseDTO(Constant.Code.GET_PRICE_SUCCESS, "Tim Phi Thanh Cong");
            responseDTO.addObject(Constant.JsonName.PRICE, priceDTO);
            return responseDTO;
        }

        return new ResponseDTO(Constant.Code.GET_PRICE_FAIL, "Tim Phi That Bai");
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO readAll() {

        logger.info("\nVIEW ALL PRICE");
        List<PriceDTO> dtos = priceService.getAllPrice();
        if (!dtos.isEmpty()) {
            ResponseDTO responseDTO = new ResponseDTO(Constant.Code.GET_PRICE_SUCCESS, "Tim Phi Thanh Cong");
            responseDTO.addObject(Constant.JsonName.PRICE, dtos);
            return responseDTO;
        }

        return new ResponseDTO(Constant.Code.GET_PRICE_FAIL, "Tim Phi That Bai");
    }
}
