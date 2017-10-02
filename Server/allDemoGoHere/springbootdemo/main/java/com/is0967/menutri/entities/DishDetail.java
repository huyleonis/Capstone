package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by Anhtbse61382 on 2/17/2017.
 */
@Entity(name = "dish_detail")
public class DishDetail {
   @Id
   @GeneratedValue(generator = "dish_detail_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "dish_detail_id_seq", sequenceName = "dish_detail_id_seq")
   @Column
   private Long id;

   @ManyToOne
   @JoinColumn(name = "dish_id")
   private Dish dish;

   @ManyToOne
   @JoinColumn(name = "ingredient_id")
   private Ingredient ingredient;

   @Column
   private Double amount;

   @ManyToOne
   @JoinColumn(name = "ingredient_unit_id")
   private IngredientUnit ingredientUnit;

   @Column(name = "ingredient_unit_amount")
   private Double ingredientUnitAmount;

   public IngredientUnit getIngredientUnit() {
      return ingredientUnit;
   }

   public void setIngredientUnit(IngredientUnit ingredientUnit) {
      this.ingredientUnit = ingredientUnit;
   }

   public Double getIngredientUnitAmount() {
      return ingredientUnitAmount;
   }

   public void setIngredientUnitAmount(Double ingredientUnitAmount) {
      this.ingredientUnitAmount = ingredientUnitAmount;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Dish getDish() {
      return dish;
   }

   public void setDish(Dish dish) {
      this.dish = dish;
   }

   public Ingredient getIngredient() {
      return ingredient;
   }

   public void setIngredient(Ingredient ingredient) {
      this.ingredient = ingredient;
   }

   public Double getAmount() {
      return amount;
   }

   public void setAmount(Double amount) {
      this.amount = amount;
   }
}
