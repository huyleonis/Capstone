package com.is0967.menutri.services;

import com.is0967.menutri.dtos.DishDTO;
import com.is0967.menutri.entities.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Anhtbse61382 on 2/18/2017.
 */
public interface DishService {
   Dish insertDish(DishDTO dishDTO);

   DishDTO editDish(DishDTO dishDTO);

   boolean delete(Long id);

   List<DishDTO> getAllDish();

   List<DishDTO> getRelatedDish(Long userId);

   DishDTO getDishById(Long id);

   LinkedHashMap<Nutrient, Double> getDishNutritionalDetails(Long dishId);

   List<DishCategory> getAllDishCategories();

   List<Menu> getMenuContainsDish(Long dishId, Long userId);

   List<DishTag> getTopTags();

}
