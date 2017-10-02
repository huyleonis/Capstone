package com.is0967.menutri.providingalgorithm;

import java.util.HashMap;

public class Provider {
   {
      providedMap = new HashMap<>();
   }
   private Long id;
   protected HashMap<Long, Double> providedMap;

   public HashMap<Long, Double> getProvidedMap() {
      return providedMap;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setProvidedMap(HashMap<Long, Double> providedMap) {
      this.providedMap = providedMap;
   }

   public void put(Long id, Double value) {
      this.providedMap.put(id, value);
   }
}
