package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by Anhtbse61382 on 2/17/2017.
 */
@Entity(name = "menu")
public class Menu {
   @Id
   @GeneratedValue(generator = "menu_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "menu_id_seq", sequenceName = "menu_id_seq")
   @Column
   private Long id;
   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;
   @Column
   private String description;
   @Column
   private String name;
   @Column
   private Boolean deleted;

   @Column(name = "parent_menu_id")
   private Long parentMenuId;

   @Column(name = "day_of_menu")
   private Integer dayOfMenu;

   @Column(name = "search_string")
   private String searchString;

   @Column
   private String imageurl;

   public String getImageurl() {
      return imageurl;
   }

   public void setImageurl(String imageurl) {
      this.imageurl = imageurl;
   }

   public Long getParentMenuId() {
      return parentMenuId;
   }

   public void setParentMenuId(Long parentMenuId) {
      this.parentMenuId = parentMenuId;
   }

   public Integer getDayOfMenu() {
      return dayOfMenu;
   }

   public void setDayOfMenu(Integer dayOfMenu) {
      this.dayOfMenu = dayOfMenu;
   }

   public String getSearchString() {
      return searchString;
   }

   public void setSearchString(String searchString) {
      this.searchString = searchString;
   }

   public Boolean getDeleted() {
      return deleted;
   }

   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }


   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
