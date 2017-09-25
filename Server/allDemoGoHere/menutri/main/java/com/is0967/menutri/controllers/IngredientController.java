package com.is0967.menutri.controllers;

import com.is0967.menutri.constants.Constant;
import com.is0967.menutri.dtos.IngredientDTO;
import com.is0967.menutri.dtos.IngredientUnitDTO;
import com.is0967.menutri.dtos.ResponseDTO;
import com.is0967.menutri.entities.Ingredient;
import com.is0967.menutri.entities.IngredientUnit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by NBL.Huyen on 15-02-17.
 */
@Controller
@RequestMapping("/ingredient")
public class IngredientController extends AbstractController {

   /* HÀM DEMO
   *  @RequestMapping để map địa chỉ web, ví dụ hàm này đc gọi ở localhost:8080/json
   *  build default port 8080
   *  @ResponeBody để thư viện trả về json
   * */
   @RequestMapping(method = RequestMethod.POST)
   @ResponseBody
   public ResponseDTO create(@RequestBody IngredientDTO ingredientDTO) {
      logger.info("\nINSERT INGREDIENT: \n" + ingredientDTO);
      Ingredient createdIngredient = ingredientService.insert(ingredientDTO);
      if (createdIngredient != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.INGREDIENT_CREATE_SUCCESS, "Insert sucess");
         responseDTO.addObject(Constant.JsonName.INGREDIENT, createdIngredient);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.INGREDIENT_CREATE_FAIL, " insert fail ");
      }
   }


   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO read(@PathVariable Long id) {
      logger.info("VIEW INGREDIENT ID: " + id);
      IngredientDTO dto = ingredientService.getIngredient(id);
      if (dto != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.INGREDIENT_READ_SUCCESS, "lấy ingredient thành công");
         responseDTO.addObject(Constant.JsonName.INGREDIENT, dto);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.INGREDIENT_READ_FAIL, "lấy ingredient lỗi");
      }
   }


   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO readAll() {
      StringBuilder sb = new StringBuilder();
      sb.append("VIEW ALL INGREDIENTS | ");
      Long startTime = System.currentTimeMillis();
      List<IngredientDTO> listIngredients = ingredientService.getAllIngredients();
      ResponseDTO responseDTO = new ResponseDTO(
              Constant.Code.INGREDIENT_READ_SUCCESS, "Số ingredient đã query: " + listIngredients.size());
      responseDTO.addObject(Constant.JsonName.INGREDIENT_LIST, listIngredients);
      Long endTime = System.currentTimeMillis();
      sb.append("Time: ").append(endTime - startTime).append("ms");
      logger.info(sb.toString());
      return responseDTO;
   }

   @RequestMapping(method = RequestMethod.PUT)
   @ResponseBody
   public ResponseDTO update(@RequestBody IngredientDTO ingredientDTO) {
      logger.info("\nUPDATE INGREDIENT: \n" + ingredientDTO);
      Ingredient updatedEntity = ingredientService.update(ingredientDTO);
      if (updatedEntity != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.INGREDIENT_UPDATE_SUCCESS, "Edit thành công");
         responseDTO.addObject(Constant.JsonName.INGREDIENT, updatedEntity);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.INGREDIENT_UPDATE_FAIL, "Edit lỗi");
      }
   }

   @RequestMapping(value = "/lastEdit", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO getLastEditTime() {
      ResponseDTO responseDTO = new ResponseDTO();
      responseDTO.addObject(Constant.JsonName.NUM_INGREDIENT, ingredientService.getLastEditTimeIngredient());
      return responseDTO;
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   @ResponseBody
   public ResponseDTO deleteIngredient(@PathVariable Long id) {
      logger.info("DELETE INGREDIENT ID: " + id);
      boolean isDeleted = ingredientService.delete(id);
      if (isDeleted) {
         return new ResponseDTO(Constant.Code.INGREDIENT_DELETE_SUCCESS, "xoá nguyên liệu thành công");
      } else {
         return new ResponseDTO(Constant.Code.INGREDIENT_DELETE_FAIL, "xoá nguyên liệu thất bại");
      }
   }

   @RequestMapping(value = "/unit", method = RequestMethod.POST)
   @ResponseBody
   public ResponseDTO insertIngredientUnit(@RequestBody IngredientUnitDTO ingredientUnitDTO) {
      logger.info("\nINSERT INGREDIENT UNIT: " + ingredientUnitDTO);
      IngredientUnit insertedEntity = ingredientService.insertIngredientUnit(ingredientUnitDTO);
      if (insertedEntity != null) {
         ResponseDTO responseDTO
                 = new ResponseDTO(Constant.Code.INGREDIENT_UNIT_CREATE_SUCCESS, "tạo unit thành công");
         responseDTO.addObject(Constant.JsonName.INGREDIENT_UNIT, ingredientUnitDTO);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.INGREDIENT_UNIT_CREATE_FAIL, "tạo unit lỗi");
      }
   }

   @RequestMapping(value = "/unit", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO getAllIngredientUnits() {
      List<IngredientUnitDTO> units = ingredientService.getAllIngredientUnits();
      StringBuilder sb = new StringBuilder();
      sb.append("VIEW ALL INGREDIENT UNIT | ");
      Long startTime = System.currentTimeMillis();
      ResponseDTO responseDTO = new ResponseDTO(
              Constant.Code.INGREDIENT_UNIT_READ_SUCCESS, "Số ingredient unit đã query: " + units.size());
      responseDTO.addObject(Constant.JsonName.INGREDIENT_UNIT_LIST, units);
      Long endTime = System.currentTimeMillis();
      sb.append("Time: ").append(endTime - startTime).append("ms");
      logger.info(sb.toString());
      return responseDTO;
   }

}
