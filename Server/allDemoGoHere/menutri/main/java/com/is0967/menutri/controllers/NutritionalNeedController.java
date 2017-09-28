package com.is0967.menutri.controllers;

import com.is0967.menutri.constants.Constant;
import com.is0967.menutri.dtos.NutritionalNeedDTO;
import com.is0967.menutri.dtos.ResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by phuctran93 on 2/21/2017.
 */
@Controller
@RequestMapping("/nutritional-need")
public class NutritionalNeedController extends AbstractController {
   @RequestMapping(method = RequestMethod.POST)
   @ResponseBody
   public ResponseDTO create(@RequestBody NutritionalNeedDTO nutritionalNeedDTO) {
      logger.info("\nCREATE NUTRITIONAL NEED: " + nutritionalNeedDTO);
      NutritionalNeedDTO dto = nutritionalNeedService.create(nutritionalNeedDTO);
      if (dto != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.NUTRITIONAL_NEED_CREATE_SUCCESS,
                 "tạo nhu cầu dd thành công");
         responseDTO.addObject(Constant.JsonName.NUTRITIONAL_NEED, dto);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.NUTRITIONAL_NEED_CREATE_FAIL, "tạo nhu cầu dd thất bại");
      }
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO read(@PathVariable Long id) {
      logger.info("VIEW NUTRITIONAL NEED ID: " + id);
      NutritionalNeedDTO need = nutritionalNeedService.read(id);
      if (need != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.NUTRITIONAL_NEED_READ_SUCCESS,
                 "lấy nutritional need thành công");
         responseDTO.addObject(Constant.JsonName.NUTRITIONAL_NEED, need);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.NUTRITIONAL_NEED_READ_FAIL, "lấy nutritional need lỗi");
      }
   }

   @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO readByUserId(@PathVariable Long userId) {
      logger.info("VIEW NUTRITIONAL NEED OF USER ID: " + userId);
      List<NutritionalNeedDTO> result = nutritionalNeedService.readByUserId(userId);
      if (result != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.NUTRITIONAL_NEED_READ_SUCCESS,
                 "lấy nutritional need thành công");
         responseDTO.addObject(Constant.JsonName.NUTRITIONAL_NEED_LIST, result);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.NUTRITIONAL_NEED_READ_FAIL, "lấy nutritional need lỗi");
      }
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   @ResponseBody
   public ResponseDTO delete(@PathVariable Long id) {
      logger.info("DELETE NUTRITIONAL NEED ID: " + id);
      boolean isDeleted = nutritionalNeedService.delete(id);
      if (isDeleted) {
         return new ResponseDTO(Constant.Code.NUTRITIONAL_NEED_DELETE_SUCCESS, "xoá need thành công");
      } else {
         return new ResponseDTO(Constant.Code.NUTRITIONAL_NEED_DELETE_FAIL, "xoá lỗi hoặc need ko tồn tại");
      }
   }

   @RequestMapping(method = RequestMethod.PUT)
   @ResponseBody
   public ResponseDTO update(@RequestBody NutritionalNeedDTO nutritionalNeedDTO) {
      logger.info("\nUPDATE NUTRITIONAL NEED: " + nutritionalNeedDTO);
      NutritionalNeedDTO dto = nutritionalNeedService.update(nutritionalNeedDTO);
      if (dto != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.NUTRITIONAL_NEED_UPDATE_SUCCESS, "update thành công");
         responseDTO.addObject(Constant.JsonName.NUTRITIONAL_NEED, dto);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.NUTRITIONAL_NEED_UPDATE_FAIL, "update lỗi");
      }
   }
}
