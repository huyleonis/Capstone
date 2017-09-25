package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by Anhtbse61382 on 2/17/2017.
 */
@Entity(name = "ingredient_unit")
public class IngredientUnit {
   @Id
   @GeneratedValue(generator = "ingredient_unit_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "ingredient_unit_id_seq", sequenceName = "ingredient_unit_id_seq")
   @Column
   private Long id;

   @ManyToOne
   @JoinColumn(name = "ingredient_id")
   private Ingredient ingredient;

   @Column
   private String name;

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

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Double getAmount() {
      return amount;
   }

   public void setAmount(Double amount) {
      this.amount = amount;
   }
}
