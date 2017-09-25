package com.is0967.menutri.providingalgorithm;

import java.util.HashMap;

public class Requirement {
   {
      requiredMap = new HashMap<>();
   }
   protected HashMap<Long, Double> requiredMap;

   public HashMap<Long, Double> getRequiredMap() {
      return requiredMap;
   }

   public void setRequiredMap(HashMap<Long, Double> requiredMap) {
      this.requiredMap = requiredMap;
   }

   public void put(Long id, Double value) {
      this.requiredMap.put(id, value);
   }
}