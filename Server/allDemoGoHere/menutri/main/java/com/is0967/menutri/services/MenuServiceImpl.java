package com.is0967.menutri.services;

import com.is0967.menutri.dtos.DishDTO;
import com.is0967.menutri.dtos.MenuCreate.AbstractMenuDTO;
import com.is0967.menutri.dtos.MenuCreate.DayMenuDTO;
import com.is0967.menutri.dtos.MenuCreate.MealMenuDTO;
import com.is0967.menutri.dtos.MenuCreate.MenuDTO;
import com.is0967.menutri.dtos.MenuGenerateConditionDTO;
import com.is0967.menutri.dtos.PostDTO;
import com.is0967.menutri.entities.*;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
\ * Created by huyennbl on 2/21/2017.
 */
@Service
public class MenuServiceImpl extends AbstractServiceImpl implements MenuService {
   @Autowired
   DishService dishService;

   @Autowired
   CalculatingService calculatingService;

   @Autowired
   SocialNetworkService socialNetworkService;

   @Override
   public boolean deleteMenu(Long menuId) {
      boolean isDelete = false;
      try {
         Menu menu = menuRepo.findByIdAndDeletedFalse(menuId);
         menu.setDeleted(true);
         menuRepo.save(menu);
         isDelete = true;
      } catch (HibernateException ex) {
         logger.error(ex.getMessage());
      } finally {
         return isDelete;
      }
   }

   @Override
   @Transactional
   public Long createOrUpdate(MenuDTO dto) {
      Long createdId = null;
      try {
         Menu menu = dto.convertToEntity(dto);

         Long userId = dto.getUserId();
         menu.setUser(userRepo.findOne(userId));
         menu.setDeleted(false);

         Menu createdMenu = menuRepo.save(menu);
         Long menuId = createdMenu.getId();
         insertTags(dto, menuId);


         List<DayMenuDTO> dayMenuDTOs = dto.getListDayMenu();
         for (DayMenuDTO dayMenuDTO : dayMenuDTOs) {
            insertDayMenu(createdMenu, dayMenuDTO);
         }

         createdId = menuId;
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return createdId;
      }
   }

   private void insertDayMenu(Menu createdMenu, DayMenuDTO dayMenuDTO) {
      Menu dayMenu = dayMenuDTO.convertToEntity(dayMenuDTO);
      dayMenu.setUser(createdMenu.getUser());
      dayMenu.setParentMenuId(createdMenu.getId());
      dayMenu.setDeleted(false);
      dayMenu = menuRepo.save(dayMenu);

      List<MealMenuDTO> mealMenuDTOs = dayMenuDTO.getListMealMenu();
      for (MealMenuDTO mealMenuDTO : mealMenuDTOs) {
         insertMealMenu(dayMenu, mealMenuDTO);
      }
   }

   private void insertMealMenu(Menu dayMenu, MealMenuDTO mealMenuDTO) {
      Menu menu  = mealMenuDTO.convertToEntity(mealMenuDTO);
      menu.setUser(dayMenu.getUser());
      menu.setParentMenuId(dayMenu.getId());
      menu.setDeleted(false);
      menu = menuRepo.save(menu);

      insertDishAmount(mealMenuDTO, menu);
   }

   /**
    * @param menuId
    * @return
    */
   @Override
   public MenuDTO getMenu(Long menuId) {
      MenuDTO menuDTO = null;
      try {
         Menu menu = menuRepo.findByIdAndDeletedFalse(menuId);
         if (menu != null) {
            menuDTO = new MenuDTO();
            menuDTO = (MenuDTO) AbstractMenuDTO.convertFromEntity(menuDTO, menu);
            List<MenuTagDetail> listTagsDetails = menuTagDetailRepo.findByMenuId(menuId);
            List<String> listTags = new ArrayList<>();
            for (MenuTagDetail menuTagDetail : listTagsDetails) {
               listTags.add(menuTagDetail.getTag().getName());
            }
            menuDTO.setTags(listTags);

            List<DayMenuDTO> listDayMenuDTO = getListDayMenuDTO(menuId);
            menuDTO.setListDayMenu(listDayMenuDTO);
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return menuDTO;
      }
   }

   @Override
   public List<MenuDTO> getMenuByUserId(Long userId) {
      List<Menu> listMenu = menuRepo.findByUserIdAndParentMenuIdNullAndDeletedFalse(userId);
      List<MenuDTO> listMenuDTOs = new ArrayList<>();
      for (Menu menu : listMenu) {
         MenuDTO menuDTO = new MenuDTO();
         menuDTO.setId(menu.getId());
         if (menu.getName() != null) menuDTO.setName(menu.getName());
         listMenuDTOs.add(menuDTO);
      }
      return listMenuDTOs;
   }

   private List<DayMenuDTO> getListDayMenuDTO(Long menuId) {
      List<Menu> listDayMenu = menuRepo.findAllByParentMenuId(menuId);
      List<DayMenuDTO> listDayMenuDTO = new ArrayList<>();

      for (Menu dayMenu : listDayMenu) {
         Long dayMenuId = dayMenu.getId();
         DayMenuDTO dayMenuCreateDTO = new DayMenuDTO();
         dayMenuCreateDTO = (DayMenuDTO) AbstractMenuDTO.convertFromEntity(dayMenuCreateDTO, dayMenu);

         List<MealMenuDTO> listMealMenuDTOs = getListMealMenuDTO(dayMenuId);
         dayMenuCreateDTO.setListMealMenu(listMealMenuDTOs);

         listDayMenuDTO.add(dayMenuCreateDTO);
      }
      return listDayMenuDTO;
   }

   private List<MealMenuDTO> getListMealMenuDTO(Long dayMenuId) {
      List<Menu> listMealMenu = menuRepo.findAllByParentMenuId(dayMenuId);
      List<MealMenuDTO> listMealMenuDTOs = new ArrayList<>();
      for (Menu mealMenu : listMealMenu) {
         MealMenuDTO dto = new MealMenuDTO();
         dto = (MealMenuDTO) AbstractMenuDTO.convertFromEntity(dto, mealMenu);
         getDishAmountMap(dto, mealMenu.getId());
         listMealMenuDTOs.add(dto);
      }
      return listMealMenuDTOs;
   }

   private void getDishAmountMap(MealMenuDTO mealMenuDTO, Long menuId) {
      List<MenuDetail> menuDetails = menuDetailRepo.findByMenuId(menuId);
      HashMap<Dish, Double> mapDishAmount = new HashMap<>();
      for (MenuDetail menuDetail : menuDetails) {
         mapDishAmount.put(menuDetail.getDish(), menuDetail.getAmount());
      }
      mealMenuDTO.setMapDishAmount(mapDishAmount);
   }


   private HashMap<DishDTO, Double> generateByNumOfDish(
           HashMap<Long, Double> nutrientAmount, Long dishCategoryId, int numOfDish, Long userId) {

      List<DishCategoryDetail> dishDetails
              = dishCategoryDetailRepo.findDishCategoryDetailRelatedToUser(userId, dishCategoryId); /******************/
      List<Long> listDishIds = new ArrayList<>();
      for (DishCategoryDetail dishDetail : dishDetails) {
         listDishIds.add(dishDetail.getDish().getId());
      }
      List<Long> randomizedDishIds = randomizeDishIds(listDishIds, numOfDish);

      HashMap<Long, Double> result =
              calculatingService.calculateByDishes(nutrientAmount, randomizedDishIds);
      HashMap<DishDTO, Double> convertedMap = convert(result);
      return convertedMap;
   }

   private HashMap<DishDTO, Double> convert(HashMap<Long, Double> result) {
      HashMap<DishDTO, Double> converteredMap = new HashMap<>();
      for (Map.Entry<Long, Double> entry : result.entrySet()) {
         Long dishId = entry.getKey();
         DishDTO dishDTO = dishService.getDishById(dishId);
         converteredMap.put(dishDTO, entry.getValue());
      }
      return converteredMap;
   }

   private HashMap<DishDTO, Double> generateByChosenDish(HashMap<Long, Double> nutrientAmount,
                                                         Long dishCategoryId, List<Long> dishIds) {
      int numOfDish = generateRandomNumOfDish(dishCategoryId);
      List<Long> randomizedDishIds = randomizeDishIds(dishIds, numOfDish);
      HashMap<Long, Double> result
              = calculatingService.calculateByDishes(nutrientAmount, randomizedDishIds);
      HashMap<DishDTO, Double> convertedMap = convert(result);
      return convertedMap;
   }

   private int generateRandomNumOfDish(Long dishCategoryId) {
      DishCategory dishCategory = dishCategoryRepo.findOne(dishCategoryId);
      Integer maxDish = dishCategory.getMaxDishPerMeal();
      Random random = new Random();
      return random.nextInt(maxDish) + 1;
   }

   private HashMap<DishDTO, Double> generateByTags(
           HashMap<Long, Double> nutrientAmount, Long dishCategoryId,
           List<String> tags, Long userId) {
      List<Long> dishIds = new ArrayList<>();

      for (String name : tags) {
         DishTag tag = dishTagRepo.findByNameLike(name);
         List<DishtagDetail> listTagDetails =
                 dishTagDetailRepo.findByTagIdAndDishCategoryAndRelatedToUser(userId,dishCategoryId, tag.getId());

         for (DishtagDetail tagDetail : listTagDetails) {
            Long dishId = tagDetail.getDish().getId();
            dishIds.add(dishId);
         }
      }
      int numOfDish = generateRandomNumOfDish(dishCategoryId);

      List<Long> listRandomDishIds = randomizeDishIds(dishIds, numOfDish);

      HashMap<Long, Double> result
              = calculatingService.calculateByDishes(nutrientAmount, listRandomDishIds);
      HashMap<DishDTO, Double> convertedMap = convert(result);
      return convertedMap;
   }

   private HashMap<DishDTO, Double> generateByIngredients(HashMap<Long, Double> nutrientAmount,
                                                          Long dishCategoryId, List<Long> ingredients, Long userId) {
      List<Long> listDishIds = new ArrayList<>();
      List<DishDetail> dishDetails = dishDetailRepo.findByIngredientIdIn(dishCategoryId, userId, ingredients);
      for (DishDetail dishDetail : dishDetails) {
         Long dishId = dishDetail.getDish().getId();
         if (!listDishIds.contains(dishId)) listDishIds.add(dishId);
      }
      int numOfDish = generateRandomNumOfDish(dishCategoryId);
      List<Long> randomizedDishIds = randomizeDishIds(listDishIds, numOfDish);

      HashMap<Long, Double> result =
              calculatingService.calculateByDishes(nutrientAmount, randomizedDishIds);
      HashMap<DishDTO, Double> convertedMap = convert(result);
      return convertedMap;
   }

   @Override
   public HashMap<DishDTO, Double> generate(MenuGenerateConditionDTO conditionDTO) {
      HashMap<DishDTO, Double> result = null;
      try {
         final Long WATER_ID = 1l;
         conditionDTO.getNutrientAmountMap().remove(WATER_ID);


         int type = conditionDTO.getGenerateType();
         HashMap<Long, Double> nutrientAmountMap = conditionDTO.getNutrientAmountMap();
         Long dishCategoryId = conditionDTO.getDishCategoryId();
         Long userId = conditionDTO.getUserId();
         switch (type) {
            case MenuGenerateConditionDTO.BY_TAG:
               List<String> tags = conditionDTO.getTags();
               result = generateByTags(nutrientAmountMap, dishCategoryId, tags, userId);
               break;
            case MenuGenerateConditionDTO.BY_CHOSEN_DISH:
               List<Long> chosenDishIds = conditionDTO.getListDishIds();
               result = generateByChosenDish(nutrientAmountMap, dishCategoryId, chosenDishIds);
               break;
            case MenuGenerateConditionDTO.BY_NUM_OF_DISH:
               int numOfDish = conditionDTO.getNumOfDish();
               result = generateByNumOfDish(nutrientAmountMap, dishCategoryId,
                       numOfDish, userId);
               break;
            case MenuGenerateConditionDTO.BY_INGREDIENTS:
               List<Long> listIngredientIds = conditionDTO.getListIngredientIds();
               result = generateByIngredients(nutrientAmountMap, dishCategoryId,
                       listIngredientIds, userId);
               break;
            case MenuGenerateConditionDTO.BY_RANDOM:
               DishCategory dishCategory = dishCategoryRepo.findOne(dishCategoryId);
               numOfDish = dishCategory.getMaxDishPerMeal();
               result = generateByNumOfDish(nutrientAmountMap, dishCategoryId,
                       numOfDish, userId);
               break;
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return result;
      }
   }

   @Override
   public HashMap<Long, Double> calculateRemaining(HashMap<Long, Double> requiredMap,
                                                   HashMap<DishDTO, Double> calculatedMap) {
      HashMap<Long, Double> provided = calculateProvidedNutrition(calculatedMap);
      HashMap<Long, Double> remaining = calcRemainingNutrition(requiredMap, provided);
      return remaining;
   }

   private HashMap<Long, Double> calcRemainingNutrition(HashMap<Long, Double> requiredMap, HashMap<Long, Double> provided) {
      for (Map.Entry<Long, Double> providedValueEntry : provided.entrySet()) {
         Long nutrientId = providedValueEntry.getKey();
         Double providedValue = providedValueEntry.getValue();
         Double requiredValue = requiredMap.get(nutrientId);
         if (requiredValue != null) {
            requiredMap.put(nutrientId, requiredValue - providedValue);
         }
      }
      return requiredMap;
   }

   private HashMap<Long, Double> calculateProvidedNutrition(HashMap<DishDTO, Double> calculatedMap) {
      HashMap<Long, Double> provided = new HashMap<>();

      for (Map.Entry<DishDTO, Double> dishAmountEntry : calculatedMap.entrySet()) {
         Long dishId = dishAmountEntry.getKey().getId();
         Double dishAmount = dishAmountEntry.getValue();
         LinkedHashMap<Nutrient, Double> dishNutrientMap = dishService.getDishNutritionalDetails(dishId);

         for (Map.Entry<Nutrient, Double> nutrientAmountEntry : dishNutrientMap.entrySet()) {
            Long nutrientId = nutrientAmountEntry.getKey().getId();
            Double nutrientAmount = nutrientAmountEntry.getValue();

            Double providedAmount = provided.get(nutrientId);
            if (providedAmount != null) {
               double newAmount = providedAmount + nutrientAmount * dishAmount;
               provided.put(nutrientId, newAmount);
            } else {
               provided.put(nutrientId, nutrientAmount * dishAmount);
            }
         }
      }
      return provided;
   }

   private List<Long> getCommon(List<List<Long>> listDishIdsOfEachIngredient) {
      List<Long> resultList = listDishIdsOfEachIngredient.get(0);
      int i = 1;
      while (i < listDishIdsOfEachIngredient.size()) {
         List<Long> inLoopList = listDishIdsOfEachIngredient.get(i);
         resultList.retainAll(inLoopList);
         i++;
      }
      return resultList;
   }

   private void insertTags(MenuDTO menuDTO, Long menuId) {
      List<String> tags = menuDTO.getTags();
      for (String tag : tags) {
         MenuTag existingTag = menuTagRepo.findByName(tag);
         if (existingTag != null) {
            menuTagDetailRepo.insertMenuTagDetail(menuId, existingTag.getId());
         } else {
            MenuTag newTag = new MenuTag();
            newTag.setName(tag);
            newTag = menuTagRepo.save(newTag);
            menuTagDetailRepo.insertMenuTagDetail(menuId, newTag.getId());
         }
      }
   }

   private void insertDishAmount(MealMenuDTO mealMenuDTO, Menu menu) {
      HashMap<Dish, Double> mapDishAmount = mealMenuDTO.getMapDishAmount();

      for (Map.Entry<Dish, Double> dishAmount : mapDishAmount.entrySet()) {
         Double amount = dishAmount.getValue();
         Dish dish = dishAmount.getKey();
         if (dish.getUser().getId() == mealMenuDTO.getUserId()) {
            menuDetailRepo.insertMenuDetail(menu.getId(), dish.getId(), amount);
         } else { //clone dish nếu không thuộc sở hữu user
            DishDTO a = dishService.getDishById(dish.getId());
            dish.setId(null);
            dish.setOriginalUser(dish.getUser());
            dish.setUser(menu.getUser());
            dish.setType(Dish.TYPE_CLONE);
            dish.setDeleted(false);
            Dish clonedDish = dishRepo.save(dish);

            PostDTO postDTO = new PostDTO();
            postDTO.setFeatured(false);
            postDTO.setType(Post.DISH);
            postDTO.setUserId(menu.getUser().getId());
            postDTO.setItemId(clonedDish.getId());
            postDTO.setCreateDate(System.currentTimeMillis());

            menuDetailRepo.insertMenuDetail(menu.getId(), clonedDish.getId(), amount);
         }
      }
   }

   @Override
   @Transactional
   public Long updateMenu(MenuDTO menuDTO) {
      Long updatedId = null;
      try {
         deleteMenu(menuDTO.getId());
         menuDTO.setId(null);
         updatedId = createOrUpdate(menuDTO);
      } catch (Exception e) {
         updatedId = null;
         logger.error(e.getMessage());
      } finally {
         return updatedId;
      }
   }

   private List<Long> randomizeDishIds(List<Long> dishIds, int numOfNeed) {
      int numOfDish = dishIds.size();
      if (numOfDish <= numOfNeed) {
         return dishIds;
      }
      Random random = new Random();
      while (numOfDish > numOfNeed) {
         int randomPosition = random.nextInt(numOfDish);
         dishIds.remove(randomPosition);
         numOfDish = dishIds.size();
      }
      return dishIds;
   }
}


