package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by an on 2/17/2017.
 */
@Entity(name = "dish_categories")
public class DishCategory {
   @Id
   @GeneratedValue(generator = "dish_categories_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "dish_categories_id_seq", sequenceName = "dish_categories_id_seq")
   @Column

   private long id;

   private String name;

   @Column(name = "max_dish_per_meal")
   private Integer maxDishPerMeal;

   public Integer getMaxDishPerMeal() {
      return maxDishPerMeal;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
