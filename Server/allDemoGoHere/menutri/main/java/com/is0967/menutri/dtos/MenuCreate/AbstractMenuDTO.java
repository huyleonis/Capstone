package com.is0967.menutri.dtos.MenuCreate;

import com.is0967.menutri.entities.Menu;
import com.is0967.menutri.entities.MenuTag;
import com.is0967.menutri.entities.User;

import java.util.List;

public abstract class AbstractMenuDTO {
   protected Long id, userId, parentMenuId;
   protected User user;
   protected String name, description, searchString, imageurl;
   protected Integer dayOfMenu;

   public String getImageurl() {
      return imageurl;
   }

   public void setImageurl(String imageurl) {
      this.imageurl = imageurl;
   }

   public Integer getDayOfMenu() {
      return dayOfMenu;
   }

   public void setDayOfMenu(Integer dayOfMenu) {
      this.dayOfMenu = dayOfMenu;
   }

   /**
    *  id, description, name, parentmenuid, searchstring, dayofmenu
    * @param dto
    * @return
    */
   public static Menu convertToEntity(AbstractMenuDTO dto) {
      Menu menu = new Menu();
      if(dto.getId()!=null) menu.setId(dto.getId());
      if(dto.getDescription()!=null) menu.setDescription(dto.getDescription());
      if(dto.getName()!=null) menu.setName(dto.getName());
      if(dto.getParentMenuId()!=null) menu.setParentMenuId(dto.getParentMenuId());
      if(dto.getSearchString()!=null) menu.setSearchString(dto.getSearchString().toLowerCase());
      if(dto.getDayOfMenu()!=null) menu.setDayOfMenu(dto.getDayOfMenu());
      if(dto.getImageurl()!=null) menu.setImageurl(dto.getImageurl());
      return menu;
   }

   /**
    * id, parentmenuid, user, description, name, searchstring
    * @param dto
    * @param menu
    * @return
    */
   public static AbstractMenuDTO convertFromEntity(AbstractMenuDTO dto, Menu menu) {
      if(menu.getId()!=null) dto.setId(menu.getId());
      if(menu.getParentMenuId()!=null) dto.setParentMenuId(menu.getParentMenuId());
      if(menu.getUser()!=null) dto.setUser(menu.getUser());
      if(menu.getDescription()!=null) dto.setDescription(menu.getDescription());
      if(menu.getName()!=null) dto.setName(menu.getName());
      if(menu.getSearchString()!=null) dto.setSearchString(menu.getSearchString());
      if(menu.getDayOfMenu()!=null) dto.setDayOfMenu(menu.getDayOfMenu());
      if(menu.getImageurl()!=null) dto.setImageurl(menu.getImageurl());
      return dto;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("\nId\t:");
      if (id != null) {
         sb.append(id);
      }
      sb.append("\nUserId\t:");
      if (userId != null) {
         sb.append(userId);
      }
      sb.append("\nParent menu id\t: ");
      if (parentMenuId!= null) {
         sb.append(parentMenuId);
      }
      sb.append("\nSearchString\t: ");
      if (searchString != null && !searchString.isEmpty()) {
         sb.append(searchString);
      }
      sb.append("\nImageurl\t: ");
      if (imageurl!= null) {
         sb.append(imageurl);
      }
      return sb.toString();
   }

   public String getSearchString() {
      return searchString;
   }

   public void setSearchString(String searchString) {
      this.searchString = searchString;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public Long getParentMenuId() {
      return parentMenuId;
   }

   public void setParentMenuId(Long parentMenuId) {
      this.parentMenuId = parentMenuId;
   }
}
