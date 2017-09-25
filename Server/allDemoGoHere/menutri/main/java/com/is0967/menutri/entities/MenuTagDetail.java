package com.is0967.menutri.entities;


import javax.persistence.*;

/**
 * Created by Anhtbse61382 on 2/17/2017.
 */
@Entity(name = "menutags_detail")
public class MenuTagDetail {
   @Id
   @GeneratedValue(generator = "menutags_detail_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "menutags_detail_id_seq", sequenceName = "menutags_detail_id_seq")
   @Column
   private Long id;

   @ManyToOne
   @JoinColumn(name = "tag_id")
   private MenuTag tag;

   @ManyToOne
   @JoinColumn(name = "menu_id")
   private Menu menu;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public MenuTag getTag() {
      return tag;
   }

   public void setTag(MenuTag tag) {
      this.tag = tag;
   }

   public Menu getMenu() {
      return menu;
   }

   public void setMenu(Menu menu) {
      this.menu = menu;
   }
}
