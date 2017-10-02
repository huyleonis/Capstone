package com.is0967.menutri.controllers;

import com.is0967.menutri.constants.Constant;
import com.is0967.menutri.dtos.ResponseDTO;
import com.is0967.menutri.entities.Nutrient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by phuctran93 on 2/20/2017.
 */
@RestController
@RequestMapping(value = "/nutrient")
public class NutrientController extends AbstractController {

   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO read() {
      List<Nutrient> listNutrient = nutrientService.readAll();
      if(listNutrient!=null && !listNutrient.isEmpty()) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.NUTRIENT_READ_SUCCESS,
                 "số nutrient đã query:" + listNutrient.size());
         responseDTO.addObject(Constant.JsonName.NUTRIENT_LIST, listNutrient);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.NUTRIENT_READ_FAIL, "lấy nutrient lỗi");
      }
   }
}


