package com.is0967.menutri.dtos;

import com.is0967.menutri.entities.NutritionalNeed;

import java.util.HashMap;

public class NutritionalNeedDTO {
   private String name;
   private Long id;
   private Long userId;
   private HashMap<Long, Double> nutrientAmountMap;

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("\nId\t:");
      if (id != null) {
         sb.append(id);
      }
      sb.append("\nUserId\t:");
      if (userId != null) {
         sb.append(userId);
      }
      sb.append("\nName\t:");
      if (name != null) {
         sb.append(name);
      }
      if (nutrientAmountMap != null) {
         sb.append(nutrientAmountMap);
      }
      return sb.toString();
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public HashMap<Long, Double> getNutrientAmountMap() {
      return nutrientAmountMap;
   }

   public void setNutrientAmountMap(HashMap<Long, Double> nutrientAmountMap) {
      this.nutrientAmountMap = nutrientAmountMap;
   }

   /**
    * id, name, reference, content
    *
    * @param nutritionalNeedDTO
    * @return
    */
   public static NutritionalNeed convertToEntity(NutritionalNeedDTO nutritionalNeedDTO) {
      String name = nutritionalNeedDTO.getName();
      Long id = nutritionalNeedDTO.getId();

      NutritionalNeed need = new NutritionalNeed();
      if (id != null) need.setId(id);
      if (name != null) need.setName(name);

      return need;
   }

   public NutritionalNeedDTO() {
   }

   /**
    * id, userid, name, content
    *
    * @param nutritionalNeed
    * @return
    */
   public static NutritionalNeedDTO convertFromEntity(NutritionalNeed nutritionalNeed) {
      NutritionalNeedDTO nutritionalNeedDTO = new NutritionalNeedDTO();
      Long id = nutritionalNeed.getId();
      String name = nutritionalNeed.getName();
      Long userId = nutritionalNeed.getUser().getId();

      if (id != null) nutritionalNeedDTO.setId(id);
      if(name!=null) nutritionalNeedDTO.setName(name);
      if(userId!=null) nutritionalNeedDTO.setUserId(userId);
      return nutritionalNeedDTO;
   }
}
