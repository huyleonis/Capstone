package com.is0967.menutri.services;

import com.is0967.menutri.dtos.IngredientDTO;
import com.is0967.menutri.dtos.IngredientUnitDTO;
import com.is0967.menutri.entities.Ingredient;
import com.is0967.menutri.entities.IngredientDetail;
import com.is0967.menutri.entities.IngredientUnit;
import com.is0967.menutri.entities.Nutrient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NBL.Huyen on 15-02-17.
 */

/**
 * Nhớ Annotation @Service của mấy class implement service
 * Extends luôn AbstractServiceImpl
 */
@Service
public class IngredientServiceImpl extends AbstractServiceImpl implements IngredientService {

   @Override
   public IngredientDTO getIngredient(Long ingredientId) {
      IngredientDTO dto = null;
      try {
         Ingredient ingredient = ingredientRepo.findOne(ingredientId);
         dto = getIngredientDetails(ingredient);
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return dto;
      }
   }

   private IngredientDTO getIngredientDetails(Ingredient ingredient) {
      IngredientDTO dto = IngredientDTO.convertFromEntity(ingredient);

      List<IngredientDetail> listDetail = ingredientDetailRepo.
              findByIngredientIdOrderByNutrientId(ingredient.getId());
      HashMap<Long, Double> nutrientMap = new HashMap<>();
      for (IngredientDetail detail : listDetail) {
         nutrientMap.put(detail.getNutrient().getId(), detail.getAmount());
      }
      dto.setNutrientMap(nutrientMap);

      List<IngredientUnit> listUnits = ingredientUnitRepo.findByIngredientId(ingredient.getId());
      dto.setListUnits(listUnits);
      return dto;
   }

   @Override
   @Transactional
   public Ingredient insert(IngredientDTO ingredientDTO) {
      Ingredient newIngredient = null;
      try {
         Ingredient ingredient = IngredientDTO.convertToEntity(ingredientDTO);
         ingredient.setCreateDate(System.currentTimeMillis());
         ingredient.setDeleted(false);
         newIngredient = ingredientRepo.save(ingredient);
         Long newIngredientId = newIngredient.getId();

         ingredientUnitRepo.insertDefaultGramUnit(newIngredientId*-1, newIngredientId);

         HashMap<Long, Double> mapNutrient = ingredientDTO.getNutrientMap();
         for (Map.Entry<Long, Double> nutrient : mapNutrient.entrySet()) {
            Long nutrientId = nutrient.getKey();
            Double amount = nutrient.getValue();

            IngredientDetail newDetail = new IngredientDetail();
            newDetail.setAmount(amount);
            newDetail.setNutrient(nutrientRepo.findOne(nutrientId));
            newDetail.setIngredient(ingredientRepo.findOne(newIngredientId));
            ingredientDetailRepo.save(newDetail);
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return newIngredient;
      }
   }

   @Override
   public List<IngredientDTO> getAllIngredients() {
      List<IngredientDTO> listDTOs = new ArrayList<>();
      List<Ingredient> listIngredients = ingredientRepo.findAllByDeletedFalseOrderByIdAsc();
      for (Ingredient ingredient : listIngredients) {
         IngredientDTO dto = getIngredientDetails(ingredient);
         listDTOs.add(dto);
      }
      return listDTOs;
   }

   @Override
   @Transactional
   public Ingredient update(IngredientDTO ingredientDTO) {
      Long ingredientId = ingredientDTO.getId();
      Ingredient newEntity = null;
      try {
         if (ingredientId != null) {
            Ingredient entity = ingredientRepo.findOne(ingredientId);
            if (entity != null) {
               newEntity = ingredientDTO.convertToEntity(ingredientDTO);
               newEntity.setId(ingredientId);
               newEntity.setLastEdit(System.currentTimeMillis());
               ingredientRepo.save(newEntity);

               HashMap<Long, Double> nutrientMap = ingredientDTO.getNutrientMap();
               List<IngredientDetail> listDetail = ingredientDetailRepo.findByIngredientIdOrderByNutrientId(ingredientId);
               for (IngredientDetail detail : listDetail) {
                  Nutrient nutrient = detail.getNutrient();
                  Double nutrientNewValue = nutrientMap.get(nutrient.getId());
                  detail.setAmount(nutrientNewValue);
                  ingredientDetailRepo.save(detail);
               }
            }
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      }
      return newEntity;
   }

   @Override
   @Transactional
   public boolean delete(Long ingredientId) {
      boolean isDeleted = false;
      try {
         Ingredient ingredient = ingredientRepo.findOne(ingredientId);
         ingredient.setDeleted(true);
         ingredientRepo.save(ingredient);
         isDeleted = true;
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return isDeleted;
      }
   }

   @Override
   public long getLastEditTimeIngredient() {
      return ingredientRepo.getLastEditIngredient().getLastEdit();
   }

   @Override
   public IngredientUnit insertIngredientUnit(IngredientUnitDTO ingredientUnitDTO) {
      IngredientUnit entity = null;
      try {
         Ingredient ingredient = ingredientRepo.findOne(ingredientUnitDTO.getIngredientId());
         IngredientUnit ingredientUnit = new IngredientUnit();
         ingredientUnit.setIngredient(ingredient);
         ingredientUnit.setAmount(ingredientUnitDTO.getAmount());
         ingredientUnit.setName(ingredientUnitDTO.getName());
         entity = ingredientUnitRepo.save(ingredientUnit);
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return entity;
      }
   }

   @Override
   public List<IngredientUnitDTO> getAllIngredientUnits() {
      List<IngredientUnit> ingredientUnitList = ingredientUnitRepo.findAll();
      List<IngredientUnitDTO> listDTOs = new ArrayList<>();
      for (IngredientUnit ingredientUnit : ingredientUnitList) {
         listDTOs.add(IngredientUnitDTO.convertFromEntity(ingredientUnit));
      }
      return listDTOs;
    }

}
