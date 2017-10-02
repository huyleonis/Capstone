package com.is0967.menutri.services;

import com.is0967.menutri.dtos.DishDTO;
import com.is0967.menutri.dtos.MenuCreate.MenuDTO;
import com.is0967.menutri.dtos.MenuGenerateConditionDTO;

import java.util.HashMap;
import java.util.List;

/**
 * Created by an on 2/21/2017.
 */
public interface MenuService {
   boolean deleteMenu(Long id);

   Long createOrUpdate(MenuDTO menuDTO);

   Long updateMenu(MenuDTO dto);

   MenuDTO getMenu(Long id);

   List<MenuDTO> getMenuByUserId(Long userId);

   HashMap<DishDTO, Double> generate(MenuGenerateConditionDTO conditionDTO);

   HashMap<Long, Double> calculateRemaining(HashMap<Long, Double> requiredMap,
                                            HashMap<DishDTO, Double> calculatedMap);

}
