package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by NBL.Huyen on 15-02-17.
 */
@Entity(name = "ingredient_categories")
public class IngredientCategory {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ingredient_categories_id_seq")
   @SequenceGenerator(name = "ingredient_categories_id_seq", sequenceName = "ingredient_categories_id_seq")
   private long id;

   @Column
   private String name;

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
