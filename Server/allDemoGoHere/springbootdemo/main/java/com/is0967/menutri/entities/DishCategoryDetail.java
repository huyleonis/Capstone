package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by an on 2/17/2017.
 */
@Entity(name = "dish_category_detail")
public class DishCategoryDetail {

   @javax.persistence.Id
   @GeneratedValue(generator = "dish_category_detail_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "dish_category_detail_id_seq", sequenceName = "dish_category_detail_id_seq")
   @Column
   private long id;

   @ManyToOne
   @JoinColumn(name = "dish_id")
   private Dish dish;

   @ManyToOne
   @JoinColumn(name = "dish_category_id")
   private DishCategory dishCategory;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public Dish getDish() {
      return dish;
   }

   public void setDish(Dish dish) {
      this.dish = dish;
   }

   public DishCategory getDishCategory() {
      return dishCategory;
   }

   public void setDishCategory(DishCategory dishCategory) {
      this.dishCategory = dishCategory;
   }
}
