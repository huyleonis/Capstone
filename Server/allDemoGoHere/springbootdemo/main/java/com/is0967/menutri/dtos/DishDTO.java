package com.is0967.menutri.dtos;

import com.is0967.menutri.entities.*;

import java.util.List;

public class DishDTO {
   private String name, description, imageurl, searchString;
   private Long id;
   private User user, originalUser;
   private Integer ration;
   private List<DishDetail> listDishDetails;
   private List<DishCategory> listCategory;
   private List<String> listTag;
   private Integer type;
   private List<Long> dishCategoryId;

   public List<Long> getDishCategoryId() {
      return dishCategoryId;
   }

   public void setDishCategoryId(List<Long> dishCategoryId) {
      this.dishCategoryId = dishCategoryId;
   }

   public List<DishCategory> getListCategory() {
      return listCategory;
   }

   public void setListCategory(List<DishCategory> listCategory) {
      this.listCategory = listCategory;
   }

   public Integer getType() {
      return type;
   }

   public void setType(Integer type) {
      this.type = type;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public User getOriginalUser() {
      return originalUser;
   }

   public void setOriginalUser(User originalUser) {
      this.originalUser = originalUser;
   }

   public Integer getRation() {
      return ration;
   }

   public void setRation(Integer ration) {
      this.ration = ration;
   }

   public String getSearchString() {
      return searchString;
   }

   public void setSearchString(String searchString) {
      this.searchString = searchString;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("\nUserID\t:");
      if (user != null) {
         sb.append(user.getId());
      }
      sb.append("\nDishID\t:");
      if (id != null) {
         sb.append(id);
      }
      sb.append("\nName\t:");
      if (name != null) {
         sb.append(name);
      }
      sb.append("\nContent\t:");
      if (description != null) {
         sb.append(description);
      }
      sb.append("\nImageURL\t:");
      if (imageurl != null) {
         sb.append(imageurl);
      }
      sb.append("\nListCategoryDetail\t:");
      if (listCategory != null) {
         for (DishCategory dishCategory : listCategory) {
            sb.append(dishCategory.getName()).append(", ");
         }
      }
      sb.append("\nListTags\t:");
      if (listTag != null) {
         for (String tag : listTag) {
            sb.append(tag).append(", ");
         }
      }
      sb.append("\nSearchString\t:");
      if (searchString != null && !searchString.isEmpty()) {
         sb.append(searchString);
      }
      sb.append("\nDish details:");
      if (listDishDetails != null && !listDishDetails.isEmpty()) {
         sb.append(listDishDetails);
      }
      sb.append("\nOriginal User ID:");
      if (originalUser != null) {
         sb.append(originalUser.getId());
      }
      return sb.toString();
   }

   public static DishDTO convertFromEntity(Dish dish) {
      DishDTO dto = new DishDTO();
      if (dish.getId() != null) dto.setId(dish.getId());
      if (dish.getDescription() != null) dto.setDescription(dish.getDescription());
      if (dish.getRation() != null) dto.setRation(dish.getRation());
      if (dish.getSearchString() != null) dto.setSearchString(dish.getSearchString());
      if (dish.getImageurl() != null) dto.setImageurl(dish.getImageurl());
      if (dish.getName() != null) dto.setName(dish.getName());
      if (dish.getUser() != null) dto.setUser(dish.getUser());
      if (dish.getOriginalUser() != null) dto.setOriginalUser(dish.getOriginalUser());
      if (dish.getType() != null) dto.setType(dish.getType());
      return dto;
   }

   public static Dish convertToEntity(DishDTO dishDTO) {
      Dish dish = new Dish();
      if (dishDTO.getId() != null) dish.setId(dishDTO.getId());
      if (dishDTO.getImageurl() != null) dish.setImageurl(dishDTO.getImageurl());
      if (dishDTO.getDescription() != null) dish.setDescription(dishDTO.getDescription());
      if (dishDTO.getName() != null) dish.setName(dishDTO.getName());
      if (dishDTO.getSearchString() != null) dish.setSearchString(dishDTO.getSearchString().toLowerCase());
      if (dishDTO.getRation() != null) dish.setRation(dishDTO.getRation());
      return dish;
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

   public String getImageurl() {
      return imageurl;
   }

   public void setImageurl(String imageurl) {
      this.imageurl = imageurl;
   }

   public List<DishDetail> getListDishDetails() {
      return listDishDetails;
   }

   public void setListDishDetails(List<DishDetail> listDishDetails) {
      this.listDishDetails = listDishDetails;
   }

   public List<String> getListTag() {
      return listTag;
   }

   public void setListTag(List<String> listTag) {
      this.listTag = listTag;
   }
}
