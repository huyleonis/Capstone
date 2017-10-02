package com.is0967.menutri.providingalgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculator {
   {
      this.listProvider = new ArrayList<>();
   }

   private final double CALC_FACTOR = 0.0001;
   private List<Provider> listProvider;
   private Requirement requirement;
   private HashMap<Provider, Double> calculationResult = new HashMap<Provider, Double>();
   HashMap<Provider, Integer> calculatedTimes = new HashMap<>();


   public HashMap<Provider, Double> calculate() {
      this.calculatedTimes.clear();
      HashMap<Long, Double> requirementMap = requirement.getRequiredMap();
      requirementMap = removeZeroRequirement(requirementMap);

      HashMap<Provider, Double> baseScaleRate = calculateBaseScaleRate(listProvider, requirementMap);

      while (!requirementMap.isEmpty() && !listProvider.isEmpty()) {
         HashMap<Provider, Double> differentPercentageMap = generateDifferentPercentage(listProvider, requirementMap);
         Provider lowestDiffPercentProvider = getLowestDiffPercentageProvider(differentPercentageMap);
         Provider provided = calculateProvidedValue(lowestDiffPercentProvider, baseScaleRate);
         requirementMap = calculateNewRequirement(provided, requirementMap, lowestDiffPercentProvider);
         removeProviders(requirementMap, listProvider);
      }
      calculateFinalRate(baseScaleRate);

      return calculationResult;
   }

   private HashMap<Long, Double> removeZeroRequirement(HashMap<Long, Double> requirementMap) {
      HashMap<Long, Double> filteredMap = new HashMap<>();
      for (Map.Entry<Long, Double> longDoubleEntry : requirementMap.entrySet()) {
         if (longDoubleEntry.getValue() != 0.0) {
            filteredMap.put(longDoubleEntry.getKey(), longDoubleEntry.getValue());
         }
      }
      return filteredMap;
   }

   private void calculateFinalRate(HashMap<Provider, Double> baseScaleRateMap) {
      for (Map.Entry<Provider, Integer> entry : calculatedTimes.entrySet()) {
         int times = entry.getValue();
         Provider provider = entry.getKey();
         Double baseScaleRate = baseScaleRateMap.get(provider);
         Double currentResult = calculationResult.get(provider);
         double finalResult;
         if (currentResult != null) {
            finalResult = (currentResult + times * CALC_FACTOR) / baseScaleRate;
         } else {
            finalResult = times * CALC_FACTOR / baseScaleRate;
         }
         calculationResult.put(provider, finalResult);
      }
   }

   private HashMap<Provider, Double> calculateBaseScaleRate(List<Provider> listProvider,
                                                            HashMap<Long, Double> requirementMap) {
      HashMap<Provider, Double> baseRateMap = new HashMap<Provider, Double>();

      for (Provider provider : listProvider) {
         Double highestPercent = 0.0;
         HashMap<Long, Double> providedMap = provider.getProvidedMap();

         for (Map.Entry<Long, Double> requirement : requirementMap.entrySet()) {
            Long requiredKey = requirement.getKey();
            Double requiredValue = requirement.getValue();

            if (providedMap.containsKey(requiredKey)) {
               Double providedValue = providedMap.get(requiredKey);
               Double tempPercent = providedValue / requiredValue;
               if (tempPercent > highestPercent) highestPercent = tempPercent;
            }
         }
         baseRateMap.put(provider, highestPercent);
      }
      return baseRateMap;
   }

   private void removeProviders(HashMap<Long, Double> requirementMap, List<Provider> listProvider) {
      List<Long> keysToRemove = new ArrayList<>();
      List<Provider> providersToRemove = new ArrayList<>();
      for (Map.Entry<Long, Double> requirement : requirementMap.entrySet()) {
         if (requirement.getValue() == 0) {
            Long key = requirement.getKey();
            keysToRemove.add(key);

            addProvidersHaveKey(providersToRemove, listProvider, key);
         }
      }

      for (Long key : keysToRemove) {
         requirementMap.remove(key);
      }

      for (Provider provider : providersToRemove) {
         listProvider.remove(provider);
      }
   }

   private void addProvidersHaveKey(List<Provider> providersToRemove, List<Provider> listProvider, Long key) {
      for (Provider provider : listProvider) {
         Double provideValue = provider.getProvidedMap().get(key);
         if (provideValue != null && provideValue > 0) {
            providersToRemove.add(provider);
         }
      }
   }

   private HashMap<Long, Double> calculateNewRequirement(Provider provided, HashMap<Long, Double> requirementMap,
                                                         Provider lowestDiffPercentProvider) {
      HashMap<Long, Double> providedMap = provided.getProvidedMap();
      HashMap<Long, Double> newRequirementMap = new HashMap<>();

      subtractProvided(newRequirementMap, requirementMap, providedMap);
      newRequirementMap = recalculateIfUnderZero(newRequirementMap, requirementMap,
              providedMap, lowestDiffPercentProvider);
      return newRequirementMap;
   }

   private HashMap<Long, Double> recalculateIfUnderZero(HashMap<Long, Double> newRequirementMap,
                                                        HashMap<Long, Double> requirementMap,
                                                        HashMap<Long, Double> providedMap,
                                                        Provider lowestDiffPercentProvider) {
      Long rescaleElementKey = getRescaleElement(newRequirementMap, providedMap);
      if (rescaleElementKey != null) {
         newRequirementMap = rescale(rescaleElementKey, requirementMap, lowestDiffPercentProvider);
      }
      return newRequirementMap;
   }

   private HashMap<Long, Double> rescale(Long key, HashMap<Long, Double> requirementMap,
                                         Provider lowestDiffPercentProvider) {
      HashMap<Long, Double> providerMap = lowestDiffPercentProvider.getProvidedMap();
      HashMap<Long, Double> newRequirementMap = new HashMap<>();
      Double requireValue = requirementMap.get(key);
      Double providedValue = providerMap.get(key);
      Double newScaleRate = providedValue / requireValue;

      for (Map.Entry<Long, Double> requirement : requirementMap.entrySet()) {
         Long requirementKey = requirement.getKey();
         Double oldRequiredValue = requirement.getValue();

         Double elementProvidedValue = providerMap.get(requirementKey);
         Double newRequiredValue = oldRequiredValue - (elementProvidedValue / newScaleRate);
         if (newRequiredValue < 0.000000001) newRequiredValue = 0.0;
         newRequirementMap.put(requirementKey, newRequiredValue);
      }
      calculationResult.put(lowestDiffPercentProvider, requireValue / providedValue);

      Integer currentCalcTimes = calculatedTimes.get(lowestDiffPercentProvider);
      if (currentCalcTimes != null) {
         calculatedTimes.put(lowestDiffPercentProvider, currentCalcTimes - 1);
      }
      return newRequirementMap;
   }

   private Long getRescaleElement(HashMap<Long, Double> newRequirementMap, HashMap<Long, Double> providedMap) {
      double lowestUnderZeroPercent = Double.MIN_VALUE;
      Long result = null;

      for (Map.Entry<Long, Double> requirement : newRequirementMap.entrySet()) {
         Double requiredValue = requirement.getValue();
         if (requiredValue < 0) {
            Long requiredKey = requirement.getKey();
            Double absValue = Math.abs(requiredValue);
            Double providedValue = providedMap.get(requiredKey);
            Double tempPercent = absValue / providedValue;
            if (tempPercent > lowestUnderZeroPercent) {
               lowestUnderZeroPercent = tempPercent;
               result = requiredKey;
            }
         }
      }

      return result;
   }

   private void subtractProvided(HashMap<Long, Double> newRequirementMap,
                                 HashMap<Long, Double> requirementMap, HashMap<Long, Double> providedMap) {
      for (Map.Entry<Long, Double> element : providedMap.entrySet()) {
         Long elementKey = element.getKey();
         Double providedValue = element.getValue();
         Double requiredValue = requirementMap.get(elementKey);
         if (requiredValue != null) {
            newRequirementMap.put(elementKey, requiredValue - providedValue);
         }
      }
   }

   private Provider calculateProvidedValue(Provider lowestDiffPercentProvider,
                                           HashMap<Provider, Double> baseScaleRate) {
      HashMap<Long, Double> providedMap = lowestDiffPercentProvider.getProvidedMap();
      Provider result = new Provider();
      HashMap<Long, Double> resultMap = result.getProvidedMap();
      for (Map.Entry<Long, Double> element : providedMap.entrySet()) {
         Long elementKey = element.getKey();
         Double elementValue = element.getValue();
         Double scaleRate = baseScaleRate.get(lowestDiffPercentProvider);
         Double providedValue = elementValue / scaleRate * CALC_FACTOR;

         resultMap.put(elementKey, providedValue);
      }

      Integer currentTime = calculatedTimes.get(lowestDiffPercentProvider); //time = số lần
      if (currentTime != null) {
         calculatedTimes.put(lowestDiffPercentProvider, currentTime + 1);
      } else {
         calculatedTimes.put(lowestDiffPercentProvider, 1);
      }
      return result;
   }


   private Provider getLowestDiffPercentageProvider(HashMap<Provider, Double> differentPercentageMap) {
      Provider result = null;
      Double lowestDiff = Double.MAX_VALUE;

      for (Map.Entry<Provider, Double> provider : differentPercentageMap.entrySet()) {
         Double diffPercent = provider.getValue();
         if (diffPercent < lowestDiff) {
            lowestDiff = diffPercent;
            result = provider.getKey();
         }
      }
      return result;
   }

   /**
    * @param listProvider
    * @param requirementMap
    * @return map được tạo ra bao gồm các provider có cung cấp cho requirement, loại bỏ các provider không cung cấp gì.
    */
   private HashMap<Provider, Double> generateDifferentPercentage(List<Provider> listProvider,
                                                                 HashMap<Long, Double> requirementMap) {
      HashMap<Provider, Double> result = new HashMap<Provider, Double>();
      int requiredAmount = requirementMap.size();

      for (Provider provider : listProvider) {
         int providedAmount = 0;
         Double lowestPercent = Double.MAX_VALUE;
         Double highestPercent = Double.MIN_VALUE;
         HashMap<Long, Double> providedMap = provider.getProvidedMap();

         for (Map.Entry<Long, Double> requirement : requirementMap.entrySet()) {
            Long requiredKey = requirement.getKey();
            Double requiredValue = requirement.getValue();

            if (providedMap.containsKey(requiredKey)) {
               providedAmount++;
               Double providedValue = providedMap.get(requiredKey);
               Double tempPercent = providedValue / requiredValue;
               if (tempPercent < lowestPercent) lowestPercent = tempPercent;
               if (tempPercent > highestPercent) highestPercent = tempPercent;
            }
         }

         Double diffPercent;
         if (providedAmount < requiredAmount) {
            if (providedAmount > 0) { // xoá những provider không cung cấp gì
               diffPercent = highestPercent;
               result.put(provider, diffPercent);
            }
         } else {
            diffPercent = highestPercent - lowestPercent;
            result.put(provider, diffPercent);
         }
      }

      return result;
   }


   public HashMap<Provider, Double> getCalculationResult() {
      return calculationResult;
   }

   public void setCalculationResult(HashMap<Provider, Double> calculationResult) {
      this.calculationResult = calculationResult;
   }

   public List<Provider> getListProvider() {
      return listProvider;
   }

   public void setListProvider(List<Provider> listProvider) {
      this.listProvider = listProvider;
   }

   public Requirement getRequirement() {
      return requirement;
   }

   public void setRequirement(Requirement requirement) {
      this.requirement = requirement;
   }

   public void addProvider(Provider provider) {
      this.listProvider.add(provider);
   }

}
