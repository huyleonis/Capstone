package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by an on 2/17/2017.
 */
@Entity(name = "dish_tags")
public class DishTag {

   @Id
   @GeneratedValue(generator = "dish_tags_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "dish_tags_id_seq", sequenceName = "dish_tags_id_seq")
   @Column

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
