package com.is0967.menutri.services;

import com.is0967.menutri.dtos.DishDTO;
import com.is0967.menutri.entities.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Anhtbse61382 on 2/18/2017.
 */
@Service
public class DishServiceImpl extends AbstractServiceImpl implements DishService {

   @Override
   @Transactional
   public Dish insertDish(DishDTO dishDTO) {
      Dish createdEntity = null;
      try {
         createdEntity = insertNewDish(dishDTO);
         insertDishCategories(dishDTO, createdEntity.getId());
         insertDishTags(dishDTO, createdEntity.getId());
         insertDishDetails(dishDTO, createdEntity);
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return createdEntity;
      }
   }

   private void insertDishDetails(DishDTO dishDTO, Dish createdEntity) {
      List<DishDetail> dishDetails = dishDTO.getListDishDetails();
      for (DishDetail dishDetail : dishDetails) {
         dishDetail.setDish(createdEntity);
         dishDetailRepo.save(dishDetail);
      }
   }

   private void insertDishTags(DishDTO dishDTO, Long entityId) {
      List<String> tags = dishDTO.getListTag();
      if (tags != null) {
         for (String dishTag : tags) {
            DishTag existingTag = dishTagRepo.findByNameLike(dishTag);

            if (existingTag != null) {
               dishTagRepo.save(existingTag);
               dishTagDetailRepo.insertTagDetail(entityId, existingTag.getId());
            } else {
               DishTag newTag = new DishTag();
               newTag.setName(dishTag);
               newTag = dishTagRepo.save(newTag);
               dishTagDetailRepo.insertTagDetail(entityId, newTag.getId());
            }
         }
      }
   }

   private void insertDishCategories(DishDTO dishDTO, Long entityId) {
      List<DishCategory> listCategory = dishDTO.getListCategory();
      List<Long> listCategoryId = new ArrayList<>();
      if (listCategory != null) {
         for (DishCategory category : listCategory) {
            listCategoryId.add(category.getId());
         }
         if (listCategoryId != null) {
            for (Long categoryId : listCategoryId) {
               dishCategoryDetailRepo.insertDishCategoryDetail(entityId, categoryId);
            }
         }
      }
   }

   private Dish insertNewDish(DishDTO dishDTO) {
      Dish dishEntity = DishDTO.convertToEntity(dishDTO);
      User user = userRepo.findOne(dishDTO.getUser().getId());
      dishEntity.setUser(user);
      if (dishDTO.getOriginalUser() != null) dishEntity.setOriginalUser(dishDTO.getOriginalUser());
      dishEntity.setType(dishDTO.getType());
      dishEntity.setDeleted(false);
      return dishRepo.save(dishEntity);
   }

   @Override
   @Transactional
   public DishDTO editDish(DishDTO dishDTO) {
      DishDTO edited = null;
      Long dishId = dishDTO.getId();
      try {
         if (dishDTO != null && dishId != null) {
            dishCategoryDetailRepo.deleteDishCategoryDetailByDishID(dishId);
            dishTagDetailRepo.deleteDishTagDetailByDishId(dishId);
            dishDetailRepo.deleteDishDetailByDishId(dishId);
            insertDish(dishDTO);
            edited = dishDTO;
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return edited;
      }

   }

   @Override
   public LinkedHashMap<Nutrient, Double> getDishNutritionalDetails(Long dishId) {
      LinkedHashMap<Nutrient, Double> nutrientAmountMap = null;

      if (dishRepo.findOne(dishId) != null) {
         nutrientAmountMap = new LinkedHashMap<>();

         List<DishDetail> listDishDetail = dishDetailRepo.findByDishId(dishId);
         for (DishDetail dishDetail : listDishDetail) {
            Double ingredientAmount = dishDetail.getAmount();
            Long ingredientId = dishDetail.getIngredient().getId();
            Double rejectionPercent = dishDetail.getIngredient().getRejectionPercent();

            List<IngredientDetail> ListIngredientDetail =
                    ingredientDetailRepo.findByIngredientIdOrderByNutrientId(ingredientId);
            for (IngredientDetail ingredientDetail : ListIngredientDetail) {
               Nutrient nutrient = ingredientDetail.getNutrient();
               Double nutrientAmount = ingredientDetail.getAmount();
               Double currentAmount = nutrientAmountMap.get(nutrient);
               Double newAmount = nutrientAmount * ingredientAmount / 100
                       * (100 - rejectionPercent) / 100;
               if (currentAmount != null) {
                  nutrientAmountMap.put(nutrient, currentAmount + newAmount);
               } else {
                  nutrientAmountMap.put(nutrient, newAmount);
               }
            }
         }
      }
      return nutrientAmountMap;
   }

   @Override
   public List<DishCategory> getAllDishCategories() {
      return dishCategoryRepo.findAll();
   }

   @Override
   public List<Menu> getMenuContainsDish(Long dishId, Long userId) {
      return menuRepo.findMenusContainDish(dishId, userId);
   }

   @Override
   public List<DishTag> getTopTags() {
      return dishTagRepo.findTop3Tags();
   }

   @Override
   @Transactional
   public boolean delete(Long dishId) {
      boolean isDelete = false;
      try {
//         if (dishId != null) {
//            dishCategoryDetailRepo.deleteDishCategoryDetailByDishID(dishId);
//            dishTagDetailRepo.deleteDishTagDetailByDishId(dishId);
//            dishDetailRepo.deleteDishDetailByDishId(dishId);
//            menuDetailRepo.deleteMenuDetailByDishId(dishId);
//            dishRepo.delete(dishId);
//            isDelete = true;
//         }
//         CODE ABOVE IS USED FOR PHYSICAL DELETE

         Dish dish = dishRepo.findByIdAndDeletedFalse(dishId);
         if (dish != null) {
            dish.setDeleted(true);
            dishRepo.save(dish);
            isDelete = true;
         }
      } catch (Exception e) {
         logger.error(e.getLocalizedMessage());
      } finally {
         return isDelete;
      }
   }

   @Override
   public List<DishDTO> getAllDish() {
      List<DishDTO> listDTOs = new ArrayList<>();
      List<Dish> listDish = dishRepo.findAllByDeletedFalse();

      for (Dish dish : listDish) {
         listDTOs.add(getDataFromDish(dish));
      }
      return listDTOs;
   }

   @Override
   public List<DishDTO> getRelatedDish(Long userId) {
      List<Dish> listDishes = dishRepo.findRelatedDishes(userId);
      List<DishDTO> dishDTOs = new ArrayList<>();
      for (Dish dish : listDishes) {
         dishDTOs.add(getDataFromDish(dish));
      }
      return dishDTOs;
   }

   @Override
   public DishDTO getDishById(Long dishId) {
      DishDTO dto = null;
      try {
         Dish dish = dishRepo.findOne(dishId);
         dto = getDataFromDish(dish);
      } catch (Exception e) {
         dto = null;
         logger.error(e.getMessage());
      } finally {
         return dto;
      }
   }

   private DishDTO getDataFromDish(Dish dish) {
      DishDTO dto = DishDTO.convertFromEntity(dish);
      Long dishId = dish.getId();
      List<DishCategoryDetail> dishCategoryDetails = dishCategoryDetailRepo.findByDishId(dishId);
      List<DishCategory> categoryList = new ArrayList<>();
      for (DishCategoryDetail dishCategoryDetail : dishCategoryDetails) {
         categoryList.add(dishCategoryDetail.getDishCategory());
      }
      dto.setListCategory(categoryList);

      List<DishDetail> dishDetails = dishDetailRepo.findByDishId(dishId);
      dto.setListDishDetails(dishDetails);

      List<DishtagDetail> dishTags = dishTagDetailRepo.findByDishId(dishId);
      List<String> tags = new ArrayList<>();
      for (DishtagDetail dishTag : dishTags) {
         tags.add(dishTag.getDishTag().getName());
      }
      dto.setListTag(tags);

      List<DishCategoryDetail> a = dishCategoryDetailRepo.findByDishId(dishId);
      List<Long> dishCategoryIds = new ArrayList<>();
      for (DishCategoryDetail dishCategoryDetail : a) {
         dishCategoryIds.add(dishCategoryDetail.getDishCategory().getId());
      }
      dto.setDishCategoryId(dishCategoryIds);
      return dto;
   }

}
