package com.is0967.menutri.services;

import com.is0967.menutri.entities.DishDetail;
import com.is0967.menutri.entities.IngredientDetail;
import com.is0967.menutri.entities.Nutrient;
import com.is0967.menutri.providingalgorithm.Calculator;
import com.is0967.menutri.providingalgorithm.Provider;
import com.is0967.menutri.providingalgorithm.Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NBL.Huyen on 01-03-17.
 */
@Service
public class CalculatingServiceImpl extends AbstractServiceImpl implements CalculatingService {
   private final Long MONEY_KEY = -1l;

   @Autowired
   DishService dishService;

   @Override
   public HashMap<Long, Double> calculateByDishes(
           HashMap<Long, Double> nutrientAmountMap, List<Long> listDishIds) {
      List<Provider> listProviders = new ArrayList<>();
      Requirement requirement = new Requirement();

      requirement.setRequiredMap(nutrientAmountMap);
      getProvidersData(listProviders, listDishIds);

      HashMap<Long, Double> result = calculateAndConvertResult(listProviders, requirement);
      return result;
   }

   private HashMap<Long, Double> calculateAndConvertResult(List<Provider> listProviders, Requirement requirement) {
      Calculator calculator = new Calculator();
      calculator.setRequirement(requirement);
      calculator.setListProvider(listProviders);
      HashMap<Provider, Double> calculatedMap = calculator.calculate();


      HashMap<Long, Double> result = new HashMap<>();
      //convert to Long Double map
      for (Map.Entry<Provider, Double> entry : calculatedMap.entrySet()) {
         result.put(entry.getKey().getId(), entry.getValue());
      }
      return result;
   }

   private void getProvidersData(List<Provider> listProviders, List<Long> listDishIds) {
      for (Long dishId : listDishIds) {
         Provider provider = new Provider();
         provider.setId(dishId);

         HashMap<Nutrient, Double> nutrientProvideMap
                 = dishService.getDishNutritionalDetails(dishId);
         HashMap<Long, Double> nutrientIdProvideMap = new HashMap<>();
         for (Map.Entry<Nutrient, Double> entry : nutrientProvideMap.entrySet()) {
            nutrientIdProvideMap.put(entry.getKey().getId(), entry.getValue());
         }

         provider.setProvidedMap(nutrientIdProvideMap);
         listProviders.add(provider);
      }
   }

   private void getNutrientProvideDetail(DishDetail dishDetail, Provider provider) {
      Long ingredientId = dishDetail.getIngredient().getId();
      Double rejectionPercent = dishDetail.getIngredient().getRejectionPercent();
      Double ingredientAmount = dishDetail.getAmount();

      List<IngredientDetail> listIngredientDetail =
              ingredientDetailRepo.findByIngredientIdOrderByNutrientId(ingredientId);
      for (IngredientDetail ingredientDetail : listIngredientDetail) {
         Long nutrientId = ingredientDetail.getNutrient().getId();
         Double nutrientAmount = ingredientDetail.getAmount();

         //nutrient trong db đc tính theo 100g ăn đc
         Double provideValue = nutrientAmount * ingredientAmount / 100
                 * ((100 - rejectionPercent) / 100);
         provider.put(nutrientId, provideValue);
      }
   }

}
