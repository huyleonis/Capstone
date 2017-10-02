package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by an on 2/17/2017.
 */
@Entity(name = "ingredient_detail")
public class IngredientDetail {

   @Id
   @GeneratedValue(generator = "ingredient_detail_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "ingredient_detail_id_seq", sequenceName = "ingredient_detail_id_seq")
   @Column

   private Long id;

   @ManyToOne
   @JoinColumn(name = "ingredient_id")
   private Ingredient ingredient;

   @ManyToOne
   @JoinColumn(name = "nutrient_id")
   private Nutrient nutrient;

   @Column
   private Double amount;

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

   public Nutrient getNutrient() {
      return nutrient;
   }

   public void setNutrient(Nutrient nutrient) {
      this.nutrient = nutrient;
   }

   public Double getAmount() {
      return amount;
   }

   public void setAmount(Double amount) {
      this.amount = amount;
   }
}
