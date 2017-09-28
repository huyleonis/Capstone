package com.is0967.menutri.dtos;

import com.is0967.menutri.entities.IngredientUnit;

public class IngredientUnitDTO {
   private String ingredientName, name;
   private Double amount;
   private Long id, ingredientId;

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("\nId\t:");
      if (id != null) {
         sb.append(id);
      }
      sb.append("\nIngredientId\t:");
      if (ingredientId != null) {
         sb.append(ingredientId);
      }
      sb.append("\nUnit name\t:");
      if (name != null) {
         sb.append(name);
      }
      sb.append("\nAmount\t:");
      if (amount!= null) {
         sb.append(amount);
      }
      return sb.toString();
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public static IngredientUnitDTO convertFromEntity(IngredientUnit ingredientUnit) {
      IngredientUnitDTO dto = new IngredientUnitDTO();
      if (ingredientUnit.getId() != null) dto.setId(ingredientUnit.getId());
      if (ingredientUnit.getName() != null) dto.setName(ingredientUnit.getName());
      if (ingredientUnit.getAmount() != null) dto.setAmount(ingredientUnit.getAmount());
      if (ingredientUnit.getIngredient().getId() != null)
         dto.setIngredientId(ingredientUnit.getIngredient().getId());
      if (ingredientUnit.getIngredient().getName() != null)
         dto.setIngredientName(ingredientUnit.getIngredient().getName());
      return dto;
   }

   public String getIngredientName() {
      return ingredientName;
   }

   public void setIngredientName(String ingredientName) {
      this.ingredientName = ingredientName;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Double getAmount() {
      return amount;
   }

   public void setAmount(Double amount) {
      this.amount = amount;
   }

   public Long getIngredientId() {
      return ingredientId;
   }

   public void setIngredientId(Long ingredientId) {
      this.ingredientId = ingredientId;
   }
}
