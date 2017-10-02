package com.is0967.menutri.controllers;

import com.is0967.menutri.constants.Constant;
import com.is0967.menutri.dtos.DishDTO;
import com.is0967.menutri.dtos.ResponseDTO;
import com.is0967.menutri.entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Anhtbse61382 on 2/18/2017.
 */
@RequestMapping("/dish")
@Controller
public class DishController extends AbstractController {
   @RequestMapping(method = RequestMethod.POST)
   @ResponseBody
   public ResponseDTO create(@RequestBody DishDTO dishDTO) {
      logger.info("\nCREATE DISH" + dishDTO);
      Dish dish = dishService.insertDish(dishDTO);
      if (dish != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.DISH_CREATE_SUCCESS, "Tạo dish thành công");
         responseDTO.addObject(Constant.JsonName.DISH, dish);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.DISH_CREATE_FAIL, "tạo dish fail");
      }
   }

   @RequestMapping(method = RequestMethod.PUT)
   @ResponseBody
   public ResponseDTO update(@RequestBody DishDTO dishDTO) {
      logger.info("\nUPDATE DISH" + dishDTO);
      DishDTO updatedDish = dishService.editDish(dishDTO);
      if (updatedDish != null) {
         ResponseDTO ResponseDTO = new ResponseDTO(Constant.Code.DISH_UPDATE_SUCCESS, "update dish thành công");
         ResponseDTO.addObject(Constant.JsonName.DISH, updatedDish);
         return ResponseDTO;
      } else {
         return new ResponseDTO(Constant.Code.DISH_UPDATE_FAIL, "update dish thất bại");
      }
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   @ResponseBody
   public ResponseDTO delete(@PathVariable Long id) {
      logger.info("DELETE DISH: " + id);
      boolean isDelete = false;
      if (id != null) {
         isDelete = dishService.delete(id);
      }
      if (isDelete) {
         return new ResponseDTO(Constant.Code.DISH_DELETE_SUCCESS, "Xoá dish thành công");
      } else {
         return new ResponseDTO(Constant.Code.DISH_DELETE_FAIL, "Xoá dish thất bại");
      }
   }

   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO readAll() {
      StringBuilder sb = new StringBuilder();
      sb.append("VIEW ALL DISH | ");
      Long startTime = System.currentTimeMillis();
      List<DishDTO> listDishes = dishService.getAllDish();
      ResponseDTO responseDTO =
              new ResponseDTO(Constant.Code.DISH_READ_SUCCESS, "số dish đã query: " + listDishes.size());
      responseDTO.addObject(Constant.JsonName.DISH_LIST, listDishes);
      Long endTime = System.currentTimeMillis();
      sb.append("Time: ").append(endTime - startTime).append("ms");
      logger.info(sb.toString());
      return responseDTO;
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO read(@PathVariable Long id) {
      logger.info("VIEW DISH ID: " + id);
      DishDTO dto = dishService.getDishById(id);
      if (dto != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.DISH_READ_SUCCESS, "lấy dish thành công");
         responseDTO.addObject(Constant.JsonName.DISH, dto);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.DISH_READ_FAIL, "lấy dish lỗi");
      }
   }

   @RequestMapping(value = "/{id}/{userId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO getMenusContainingDish(@PathVariable Long id, @PathVariable Long userId) {
      logger.info("GET MENUS CONTAINING DISH ID: " + id + " | USER ID:" + userId);
      List<Menu> listMenu = dishService.getMenuContainsDish(id, userId);
      if (listMenu != null && !listMenu.isEmpty()) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.MENU_READ_SUCCESS, "lấy menu chứa dish thành công");
         responseDTO.addObject(Constant.JsonName.MENU_LIST, listMenu);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.MENU_READ_FAIL, "lỗi server hoặc không có menu nào chứa dish");
      }
   }

   @RequestMapping(value = "/nutrition/{dishId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO readNutrition(@PathVariable Long dishId) {
      logger.info("VIEW NUTRITION OF DISH ID: " + dishId);
      LinkedHashMap<Nutrient, Double> nutritionMap = dishService.getDishNutritionalDetails(dishId);
      if (nutritionMap != null) {
         ResponseDTO responseDTO =
                 new ResponseDTO(Constant.Code.DISH_NUTRITION_READ_SUCCESS, "lấy thông tin dinh dưỡng thành công");
         responseDTO.addObject(Constant.JsonName.NUTRIENT_LIST, nutritionMap);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.DISH_NUTRITION_READ_FAIL, "lấy thông tin dinh dưỡng thất bại");
      }
   }

   @RequestMapping(value = "/category", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO getAllCategory() {
      List<DishCategory> listDish = dishService.getAllDishCategories();
      if (!listDish.isEmpty()) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.DISH_CATEGORY_READ_SUCCESS, "get thành công");
         responseDTO.addObject(Constant.JsonName.DISH_CATEGORY_LIST, listDish);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.DISH_CATEGORY_READ_FAIL, "ko có category hoặc get lỗi");
      }
   }

   @RequestMapping(value = "/tag/top", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO getTopTags() {
      List<DishTag> tags = dishService.getTopTags();
      if (tags != null && !tags.isEmpty()) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.DISH_TAG_READ_SUCCESS, "lấy top tag thành công");
         responseDTO.addObject(Constant.JsonName.DISH_TAG_LIST, tags);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.DISH_TAG_READ_FAIL, "lấy top tag thất bại hoặc database ko có dữ liệu");
      }
   }

   @RequestMapping(value = "/related/{userId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO getRelatedDishes(@PathVariable Long userId) {
      logger.info("GET RELATED DISH OF USER ID: " + userId);
      List<DishDTO> dishes = dishService.getRelatedDish(userId);
      if (dishes != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.DISH_READ_SUCCESS, "lấy dish thành công");
         responseDTO.addObject(Constant.JsonName.DISH_LIST, dishes);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.DISH_READ_FAIL, "lay dish that bai");
      }
   }
}
