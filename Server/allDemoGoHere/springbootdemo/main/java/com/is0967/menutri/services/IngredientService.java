package com.is0967.menutri.services;

import com.is0967.menutri.dtos.IngredientDTO;
import com.is0967.menutri.dtos.IngredientUnitDTO;
import com.is0967.menutri.entities.Ingredient;
import com.is0967.menutri.entities.IngredientUnit;

import java.util.List;

/**
 * Created by NBL.Huyen on 15-02-17.
 */
public interface IngredientService {
   IngredientDTO getIngredient(Long id);

   Ingredient insert(IngredientDTO ingredientDTO);

   List<IngredientDTO> getAllIngredients();

   Ingredient update(IngredientDTO ingredientDTO);

   boolean delete(Long ingredientId);

   long getLastEditTimeIngredient();

   IngredientUnit insertIngredientUnit(IngredientUnitDTO ingredientUnitDTO);

   List<IngredientUnitDTO> getAllIngredientUnits();
}
