package com.is0967.menutri.dtos;

import com.is0967.menutri.entities.Ingredient;
import com.is0967.menutri.entities.IngredientUnit;

import java.util.HashMap;
import java.util.List;

public class IngredientDTO {
   private Long id;
   private String name;
   private String imageurl;
   private Double rejectionPercent;
   private String reference;
   private HashMap<Long, Double> nutrientMap;
   private List<IngredientUnit> listUnits;
   private String searchString;

   public String getSearchString() {
      return searchString;
   }

   public void setSearchString(String searchString) {
      this.searchString = searchString;
   }

   public List<IngredientUnit> getListUnits() {
      return listUnits;
   }

   public void setListUnits(List<IngredientUnit> listUnits) {
      this.listUnits = listUnits;
   }

   public String getReference() {
      return reference;
   }

   public void setReference(String reference) {
      this.reference = reference;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("\nId\t:");
      if (id != null) {
         sb.append(id);
      }
      sb.append("\nName\t:");
      if (name != null) {
         sb.append(name);
      }
      sb.append("\nImageURL\t:");
      if (imageurl != null) {
         sb.append(imageurl);
      }
      sb.append("\nNutrients\t:");
      if (nutrientMap != null) {
         sb.append(nutrientMap);
      }
      sb.append("\nRejectionPercent\t:");
      if (rejectionPercent != null) {
         sb.append(rejectionPercent);
      }
      sb.append("\nReference\t:");
      if (reference != null) {
         sb.append(reference);
      }
      sb.append("\nUnits\t: ");
      if(listUnits!=null) {
         for (IngredientUnit unit: listUnits) {
            sb.append(unit.getName());
         }
      }
      return sb.toString();
   }

   public Double getRejectionPercent() {
      return rejectionPercent;
   }

   public void setRejectionPercent(Double rejectionPercent) {
      this.rejectionPercent = rejectionPercent;
   }

   public HashMap<Long, Double> getNutrientMap() {
      return nutrientMap;
   }

   public void setNutrientMap(HashMap<Long, Double> nutrientMap) {
      this.nutrientMap = nutrientMap;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getImageurl() {
      return imageurl;
   }

   public void setImageurl(String imageurl) {
      this.imageurl = imageurl;
   }

   /**
    * transfer name, imageUrl, id, rejectionPercent, reference
    * @param ingredient
    * @return
    */
   public static IngredientDTO convertFromEntity(Ingredient ingredient) {
      IngredientDTO dto = new IngredientDTO();
      if (ingredient.getName() != null) {
         dto.setName(ingredient.getName());
      }
      if (ingredient.getImageurl() != null) {
         dto.setImageurl(ingredient.getImageurl());
      }
      if (ingredient.getId() != 0) {
         dto.setId(ingredient.getId());
      }
      if(ingredient.getRejectionPercent()!=null) {
         dto.setRejectionPercent(ingredient.getRejectionPercent());
      }
      if(ingredient.getReference()!=null) {
         dto.setReference(ingredient.getReference());
      }
      if(ingredient.getSearchString()!=null) {
         dto.setSearchString(ingredient.getSearchString());
      }
      return dto;

   }

   /**
    * transfer imageUrl, name, reference, rejectionPercent, searchString
    * @param ingredientDTO
    * @return
    */
   public static Ingredient convertToEntity(IngredientDTO ingredientDTO) {
      Ingredient ingredient = new Ingredient();
      if(ingredientDTO.getSearchString()!=null) {
         ingredient.setSearchString(ingredientDTO.getSearchString().toLowerCase());
      }
      if(ingredientDTO.getImageurl()!=null) {
         ingredient.setImageurl(ingredientDTO.getImageurl());
      }
      if(ingredientDTO.getName()!=null) {
         ingredient.setName(ingredientDTO.getName());
      }
      if(ingredientDTO.getReference()!=null) {
         ingredient.setReference(ingredientDTO.getReference());
      }
      if(ingredientDTO.getRejectionPercent()!=null) {
         ingredient.setRejectionPercent(ingredientDTO.getRejectionPercent());
      }
      return ingredient;
   }
}
