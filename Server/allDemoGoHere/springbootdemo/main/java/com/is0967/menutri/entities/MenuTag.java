package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by Anhtbse61382 on 2/17/2017.
 */
@Entity(name = "menu_tags")
public class MenuTag {
   @Id
   @GeneratedValue(generator = "menu_tags_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "menu_tags_id_seq", sequenceName = "menu_tags_id_seq")
   @Column
   private Long id;
   @Column
   private String name;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
