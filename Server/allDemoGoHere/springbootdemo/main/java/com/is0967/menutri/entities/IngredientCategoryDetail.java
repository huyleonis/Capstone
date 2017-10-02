package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by NBL.Huyen on 15-02-17.
 */


@Entity(name = "ingredient_category_detail")
public class IngredientCategoryDetail {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ingredient_category_detail_id_seq")
   @SequenceGenerator(name = "ingredient_category_detail_id_seq", sequenceName = "ingredient_category_detail_id_seq")

   private Long id;

   @ManyToOne
   @JoinColumn(name = "ingredient_id")
   private Ingredient ingredient;

   @ManyToOne
   @JoinColumn(name = "category_id")
   private IngredientCategory category;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Ingredient getIngredient() {
      return ingredient;
   }

   public void setIngredient(Ingredient ingredient) {
      this.ingredient = ingredient;
   }

   public IngredientCategory getCategory() {
      return category;
   }

   public void setCategory(IngredientCategory category) {
      this.category = category;
   }
}
