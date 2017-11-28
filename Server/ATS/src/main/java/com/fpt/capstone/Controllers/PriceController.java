package com.fpt.capstone.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.PriceDTO;
import com.fpt.capstone.Entities.Price;
import com.fpt.capstone.Services.PriceService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private PriceService priceService;

    static final int ACTIVE = 6;

    /**
     * Hiển thị trang price.jsp
     *
     * @return price view
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewPrice() {
        ModelAndView m = new ModelAndView("price");
        m.addObject("currSelected", ACTIVE);
        m.addObject("currTitle", "Price Management");
        return m;
    }

    /**
     * Lấy danh sách price
     *
     * @return Danh sách Price dưới dạng JSONARRAY
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAllPrice() throws JsonProcessingException {
        List<PriceDTO> dtos = priceService.getAllPrice();
        return new Gson().toJson(dtos);
    }

    /**
     * Tìm thông tin giá tiền và loại xe cho Desktop Application
     *
     * @param stationId mã trạm
     * @param licensePlate biển số xe
     * @return PriceDTO
     */
    @RequestMapping(value = "findPriceStaff/{stationId}/{licensePlate}", method = RequestMethod.GET)
    public PriceDTO findPriceByStationIdAndLicensePlate(@PathVariable int stationId,
            @PathVariable String licensePlate) {
        return priceService.findPriceByStationIdAndLicensePlate(stationId, licensePlate);
    }

    /**
     * Tạo price mới
     *
     * @param price Price entity
     * @return kểt quả thực hiện
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody Price price) {
        boolean isSuccessful = false;
        PriceDTO dto = priceService.insert(price);
        if (dto != null) {
            isSuccessful = true;
        }
        return (isSuccessful) ? "success" : "fail";
    }

    /**
     *
     * @param price Price entity
     * @return kểt quả thực hiện
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Price price) {
        boolean isSuccessful = false;
        PriceDTO dto = priceService.update(price);
        if (dto != null) {
            isSuccessful = true;
        }
        return (isSuccessful) ? "success" : "fail";
    }

    /**
     * active price
     * @param price
     * @return
     */
    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public String active(@RequestBody Price price) {

        boolean isSuccessful = priceService.active(price);

        return (isSuccessful)? "success" : "fail";
    }

    /**
     * deactive price
     * @param price
     * @return
     */
    @RequestMapping(value = "/deactive", method = RequestMethod.POST)
    public String deactive(@RequestBody Price price) {

        boolean isSuccessful = priceService.deactive(price);

        return (isSuccessful)? "success" : "fail";
    }
}
