package com.is0967.menutri.dtos;

import java.util.HashMap;
import java.util.List;

/**
 * Created by NBL.Huyen on 28-03-17.
 */
public class MenuGenerateConditionDTO {
   public transient static final int BY_TAG = 0;
   public transient static final int BY_CHOSEN_DISH = 1;
   public transient static final int BY_NUM_OF_DISH = 2;
   public transient static final int BY_INGREDIENTS = 3;
   public transient static final int BY_RANDOM = 4;

   public static final boolean DISH_INCLUDE_INGREDIENT = false;
   public static final boolean DISH_INCLUDE_ALL_INGREDIENT = true;

   private int generateType;
   private Long userId;
   private boolean mode;
   private HashMap<Long, Double> nutrientAmountMap;
   private Long dishCategoryId;
   private Integer numOfDish;
   private List<String> tags;
   private List<Long> listDishIds;
   private List<Long> listIngredientIds;

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("\nRequirement: ");
      if (nutrientAmountMap != null && !nutrientAmountMap.isEmpty()) {
         sb.append(nutrientAmountMap);
      }
      sb.append("\nUserID: ");
      if (userId != null) {
         sb.append(userId);
      }
      sb.append("Dish Category: ").append(dishCategoryId);
      switch (generateType) {
         case BY_TAG:
            sb.append("\nBy Tag: ");
            for (String tag : tags) {
               sb.append(tag).append(", ");
            }
            break;
         case BY_CHOSEN_DISH:
            sb.append("\nBy Chosen Dish IDs: ");
            for (Long dishId : listDishIds) {
               sb.append(dishId).append(", ");
            }
            break;
         case BY_INGREDIENTS:
            sb.append("\n By chosen ingredients: ");
            for (Long ingredientId : listIngredientIds) {
               sb.append(ingredientId).append(", ");
            }
            if (mode == DISH_INCLUDE_ALL_INGREDIENT) {
               sb.append("\n Find by dish INCLUDE ingredient");
            } else {
               sb.append("\n Find by dish INCLUDE ALL ingredient");
            }
            break;
         case BY_NUM_OF_DISH:
            sb.append("\nBy num of dish: ").append(numOfDish);
            break;
      }
      return sb.toString();
   }

   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public List<Long> getListDishIds() {
      return listDishIds;
   }

   public void setListDishIds(List<Long> listDishIds) {
      this.listDishIds = listDishIds;
   }

   public int getGenerateType() {
      return generateType;
   }

   public void setGenerateType(int generateType) {
      this.generateType = generateType;
   }

   public boolean isMode() {
      return mode;
   }

   public void setMode(boolean mode) {
      this.mode = mode;
   }

   public HashMap<Long, Double> getNutrientAmountMap() {
      return nutrientAmountMap;
   }

   public void setNutrientAmountMap(HashMap<Long, Double> nutrientAmountMap) {
      this.nutrientAmountMap = nutrientAmountMap;
   }

   public Long getDishCategoryId() {
      return dishCategoryId;
   }

   public void setDishCategoryId(Long dishCategoryId) {
      this.dishCategoryId = dishCategoryId;
   }

   public Integer getNumOfDish() {
      return numOfDish;
   }

   public void setNumOfDish(Integer numOfDish) {
      this.numOfDish = numOfDish;
   }

   public List<String> getTags() {
      return tags;
   }

   public void setTags(List<String> tags) {
      this.tags = tags;
   }

   public List<Long> getListIngredientIds() {
      return listIngredientIds;
   }

   public void setListIngredientIds(List<Long> listIngredientIds) {
      this.listIngredientIds = listIngredientIds;
   }
}
