package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by Anhtbse61382 on 2/17/2017.
 */
@Entity(name = "menu_detail")
public class MenuDetail {
   @Id
   @GeneratedValue(generator = "menu_detail_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "menu_detail_id_seq", sequenceName = "menu_detail_id_seq")
   @Column
   private Long id;

   @ManyToOne
   @JoinColumn(name = "dish_id")
   private Dish dish;

   @ManyToOne
   @JoinColumn(name = "menu_id")
   private Menu menu;

   @Column
   private Double amount;

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

   public Menu getMenu() {
      return menu;
   }

   public void setMenu(Menu menu) {
      this.menu = menu;
   }

   public Double getAmount() {
      return amount;
   }

   public void setAmount(Double amount) {
      this.amount = amount;
   }
}
