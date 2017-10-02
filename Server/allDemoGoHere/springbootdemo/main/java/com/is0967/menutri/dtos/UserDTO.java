package com.is0967.menutri.dtos;

import com.is0967.menutri.entities.User;

/**
 * Created by NBL.Huyen on 17-02-17.
 */

public class UserDTO {
   private Long id;
   private String username;
   private String firstName, lastName;
   private String cover, avatar;
   private Integer role;
   private Boolean gender;
   private Integer height, weight;
   private Integer monthOB, yearOB;
   private Long lastCheckNotification;
   private Boolean featured;

   public String getCover() {
      return cover;
   }

   public Integer getMonthOB() {
      return monthOB;
   }

   public void setMonthOB(Integer monthOB) {
      this.monthOB = monthOB;
   }

   public Integer getYearOB() {
      return yearOB;
   }

   public void setYearOB(Integer yearOB) {
      this.yearOB = yearOB;
   }

   public void setCover(String cover) {
      this.cover = cover;
   }

   public Boolean getFeatured() {
      return featured;
   }

   public void setFeatured(Boolean featured) {
      this.featured = featured;
   }

   public Long getLastCheckNotification() {
      return lastCheckNotification;
   }

   public void setLastCheckNotification(Long lastCheckNotification) {
      this.lastCheckNotification = lastCheckNotification;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public Integer getRole() {
      return role;
   }

   public void setRole(Integer role) {
      this.role = role;
   }

   public Boolean getGender() {
      return gender;
   }

   public void setGender(Boolean gender) {
      this.gender = gender;
   }

   public Integer getHeight() {
      return height;
   }

   public void setHeight(Integer height) {
      this.height = height;
   }

   public Integer getWeight() {
      return weight;
   }

   public void setWeight(Integer weight) {
      this.weight = weight;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getAvatar() {
      return avatar;
   }

   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }

   /**
    * avatar, dob, height, weight, firstname, lastname, id, username, role, lastchecknoti
    *
    * @param user
    * @return
    */
   public static UserDTO convertFromEntity(User user) {
      UserDTO dto = new UserDTO();
      if (user.getAvatar() != null) {
         dto.setAvatar(user.getAvatar());
      }
      if (user.getCover() != null) {
         dto.setCover(user.getCover());
      }
      if (user.getMonthOB() != null) {
         dto.setMonthOB(user.getMonthOB());
      }
      if (user.getYearOB() != null) {
         dto.setYearOB(user.getYearOB());
      }
      if (user.getGender() != null) {
         dto.setGender(user.getGender());
      }
      if (user.getHeight() != null) {
         dto.setHeight((user.getHeight()));
      }
      if (user.getWeight() != null) {
         dto.setWeight(user.getWeight());
      }
      if (user.getFirstName() != null) {
         dto.setFirstName(user.getFirstName());
      }
      if (user.getLastName() != null) {
         dto.setLastName(user.getLastName());
      }
      if (user.getUsername() != null) {
         dto.setUsername(user.getUsername());
      }
      if (user.getId() != null) {
         dto.setId(user.getId());
      }
      if (user.getRole() != null) {
         dto.setRole(user.getRole());
      }
      if (user.getLastCheckNotification() != null) {
         dto.setLastCheckNotification(user.getLastCheckNotification());
      }
      if (user.getFeatured() != null) {
         dto.setFeatured(user.getFeatured());
      }
      return dto;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("\nID\t:");
      if (id != null) {
         sb.append(id);
      }
      sb.append("\nAvatar\t:");
      if (avatar != null) {
         sb.append(avatar);
      }
      sb.append("\nCover\t:");
      if (cover != null) {
         sb.append(cover);
      }
      sb.append("\nFirstName\t:");
      if (firstName != null) {
         sb.append(firstName);
      }
      sb.append("\nLastname\t:");
      if (lastName != null) {
         sb.append(lastName);
      }
      sb.append("\nBirth: \t:");
      if (monthOB != null) {
         sb.append(monthOB).append("M");
      }
      if (yearOB != null) {
         sb.append(yearOB).append("Y");
      }
      sb.append("\nUsername\t:");
      if (username != null) {
         sb.append(username + "\n");
      }
      sb.append("\nRole\t:");
      if (role != null) {
         sb.append(role);
      }
      sb.append("\nWeight\t:");
      if (weight != null) {
         sb.append(weight);
      }
      sb.append("\nGender\t:");
      if (gender != null) {
         sb.append(gender);
      }
      sb.append("\nHeight\t:");
      if (height != null) {
         sb.append(height);
      }
      sb.append("\nLastCheck: ");
      if (lastCheckNotification != null) {
         sb.append(lastCheckNotification);
      }
      sb.append("\nFeatured: ");
      if (featured != null) {
         sb.append(featured);
      }
      return sb.toString();
   }
}
