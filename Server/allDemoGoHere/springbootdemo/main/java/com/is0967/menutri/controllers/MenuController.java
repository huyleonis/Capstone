package com.is0967.menutri.controllers;

import com.is0967.menutri.constants.Constant;
import com.is0967.menutri.dtos.DishDTO;
import com.is0967.menutri.dtos.MenuCreate.MenuDTO;
import com.is0967.menutri.dtos.MenuGenerateConditionDTO;
import com.is0967.menutri.dtos.ResponseDTO;
import com.is0967.menutri.entities.Nutrient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by an on 2/21/2017.
 */
@RequestMapping("/menu")
@Controller
public class MenuController extends AbstractController {

   @RequestMapping(method = RequestMethod.POST)
   @ResponseBody
   public ResponseDTO create(@RequestBody MenuDTO menuDTO) {
      logger.info("\nCREATE MENU: \n" + menuDTO);
      Long createdId = menuService.createOrUpdate(menuDTO);
      if (createdId != null) {
         menuDTO.setId(createdId);
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.MENU_CREATE_SUCCESS, "tạo menu thành công");
         responseDTO.addObject(Constant.JsonName.MENU, menuDTO);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.MENU_CREATE_FAIL, "tạo menu lỗi");
      }
   }

   @RequestMapping(method = RequestMethod.PUT)
   @ResponseBody
   public ResponseDTO update(@RequestBody MenuDTO menuDTO) {
      logger.info("\nUPDATE MENU: \n" + menuDTO);
      Long createdId = menuService.updateMenu(menuDTO);
      if (createdId != null) {
         menuDTO.setId(createdId);
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.MENU_UPDATE_SUCCESS, "tạo menu thành công");
         responseDTO.addObject(Constant.JsonName.MENU, menuDTO);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.MENU_UPDATE_FAIL, "tạo menu lỗi");
      }
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   @ResponseBody
   public ResponseDTO delete(@PathVariable Long id) {
      logger.info("DELETE MENU ID: " + id);
      boolean isDelete = false;
      if (id != null) {
         isDelete = menuService.deleteMenu(id);
      }
      if (isDelete) {
         return new ResponseDTO(Constant.Code.MENU_DELETE_SUCCESS, "Thành công");
      } else {
         return new ResponseDTO(Constant.Code.MENU_DELETE_FAIL, "Xoá thất bại");
      }
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO read(@PathVariable Long id) {
      logger.info("VIEW MENU ID: " + id);
      MenuDTO menuDTO = menuService.getMenu(id);
      if (menuDTO != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.MENU_READ_SUCCESS, "Load menu thành công");
         responseDTO.addObject(Constant.JsonName.MENU, menuDTO);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.MENU_READ_FAIL, "load menu lỗi");
      }
   }

   @RequestMapping(value = "/byUser/{userId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO readByUserId(@PathVariable Long userId) {
      logger.info("VIEW MENU ID: " + userId);
      List<MenuDTO> listMenuDTO = menuService.getMenuByUserId(userId);
      if (listMenuDTO != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.MENU_READ_SUCCESS, "Load menu thành công");
         responseDTO.addObject(Constant.JsonName.MENU_LIST, listMenuDTO);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.MENU_READ_FAIL, "load menu lỗi");
      }
   }

   @RequestMapping(value = "/generate", method = RequestMethod.POST)
   @ResponseBody
   public ResponseDTO generateMenu(@RequestBody MenuGenerateConditionDTO conditionDTO) {
      logger.info(conditionDTO);
      if ((conditionDTO.getGenerateType() == MenuGenerateConditionDTO.BY_CHOSEN_DISH)
              && (conditionDTO.getListDishIds().size() == 0)) {
         return createDummyResponse(conditionDTO);
      }

      HashMap<DishDTO, Double> calculatedMap = menuService.generate(conditionDTO);
      if (calculatedMap != null && calculatedMap.size() != 0) {
         HashMap<Long, Double> remainingMap
                 = menuService.calculateRemaining(conditionDTO.getNutrientAmountMap(), calculatedMap);
         List<LinkedHashMap<Nutrient, Double>> listDishNutritionDetail = new ArrayList<>();
         for (DishDTO dishDTO : calculatedMap.keySet()) {
            LinkedHashMap<Nutrient, Double> mapNutritionAmount =
                    dishService.getDishNutritionalDetails(dishDTO.getId());
            listDishNutritionDetail.add(mapNutritionAmount);
         }

         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.CALCULATE_SUCCESS, "tính táon thành công");
         responseDTO.addObject(Constant.JsonName.CALCULATED_MAP, calculatedMap);
         responseDTO.addObject(Constant.JsonName.REMAINING_MAP, remainingMap);
         responseDTO.addObject(Constant.JsonName.DISH_NUTRIENT_LIST, listDishNutritionDetail);
         return responseDTO;
      } else if (calculatedMap.size() == 0) {
         return createDummyResponse(conditionDTO);
      } else {
         return new ResponseDTO(Constant.Code.CALCULATE_FAIL, "tinh toan that bat - co the loi server");
      }
   }

   private ResponseDTO createDummyResponse(MenuGenerateConditionDTO conditionDTO) {
      ResponseDTO responseDTO = new ResponseDTO(Constant.Code.CALCULATE_SUCCESS, "khong cung cap dish");
      responseDTO.addObject(Constant.JsonName.CALCULATED_MAP, createDummyMap());
      responseDTO.addObject(Constant.JsonName.DISH_NUTRIENT_LIST, createDummyList());
      responseDTO.addObject(Constant.JsonName.REMAINING_MAP, conditionDTO.getNutrientAmountMap());
      return responseDTO;
   }

   private List<LinkedHashMap<Nutrient, Double>> createDummyList() {
      List<LinkedHashMap<Nutrient, Double>> list = new ArrayList<>();
      LinkedHashMap<Nutrient, Double> map = new LinkedHashMap<>();
      List<Nutrient> nutriList = nutrientService.readAll();
      for (Nutrient nutrient : nutriList) {
         map.put(nutrient, 0d);
      }
      list.add(map);
      return list;
   }

   private HashMap<DishDTO, Double> createDummyMap() {
      HashMap<DishDTO, Double> a = new HashMap<>();
      a.put(new DishDTO(), 0d);
      return a;
   }

}
