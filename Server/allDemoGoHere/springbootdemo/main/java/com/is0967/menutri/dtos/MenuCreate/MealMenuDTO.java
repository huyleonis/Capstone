package com.is0967.menutri.dtos.MenuCreate;

import com.is0967.menutri.entities.Dish;

import java.util.HashMap;

public class MealMenuDTO extends AbstractMenuDTO {
   private HashMap<Dish, Double> mapDishAmount;

   public HashMap<Dish, Double> getMapDishAmount() {
      return mapDishAmount;
   }

   public void setMapDishAmount(HashMap<Dish, Double> mapDishAmount) {
      this.mapDishAmount = mapDishAmount;
   }
}
