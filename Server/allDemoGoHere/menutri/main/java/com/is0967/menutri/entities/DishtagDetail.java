package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by an on 2/17/2017.
 */
@Entity(name = "dishtag_detail")
public class DishtagDetail {

   @Id
   @GeneratedValue(generator = "dishtag_detail_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "dishtag_detail_id_seq", sequenceName = "dishtag_detail_id_seq")
   @Column

   private long id;

   @ManyToOne
   @JoinColumn(name = "dish_id")
   private Dish dish;


   @ManyToOne
   @JoinColumn(name = "tag_id")
   private DishTag dishTag;

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

   public DishTag getDishTag() {
      return dishTag;
   }

   public void setDishTag(DishTag dishTag) {
      this.dishTag = dishTag;
   }
}
