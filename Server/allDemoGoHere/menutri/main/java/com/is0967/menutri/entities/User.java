package com.is0967.menutri.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by NBL.Huyen on 17-02-17.
 */
@Entity(name = "users")
public class User {
   public transient static final int ROLE_ADMIN = 0;
   public transient static final int ROLE_USER = 1;
   public transient static final int ROLE_CERTIFIED = 2;

   @Id
   @GeneratedValue(generator = "users_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq")
   @Column
   private Long id;

   @Column
   private String username;

   @Column
   private String password;

   @Column
   private Boolean gender;

   @Column
   private Integer role;

   @Column
   private Integer height;

   @Column
   private Integer weight;

   @Column
   private String firstName;

   @Column
   private String lastName;

   @Column
   private String avatar;

   @Column
   private Boolean featured;

   @Column
   private Long lastCheckNotification;

   @Column
   private String cover;

   @Column(name = "monthOB")
//   @Max(11)
   private Integer monthOB;

   @Column(name = "yearOB")
//   @Min(1900)
   private Integer yearOB;

   public String getCover() {
      return cover;
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

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Boolean getGender() {
      return gender;
   }

   public void setGender(Boolean gender) {
      this.gender = gender;
   }

   public Integer getRole() {
      return role;
   }

   public void setRole(Integer role) {
      this.role = role;
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

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      if (id != null) {
         sb.append("ID\t:").append(id + "\n");
      }
      if (avatar != null) {
         sb.append("Avatar\t:").append(avatar + "\n");
      }
      if (firstName != null) {
         sb.append("FirstName\t:").append(firstName + "\n");
      }
      if (lastName != null) {
         sb.append("Lastname\t:").append(lastName + "\n");
      }
      if (username != null) {
         sb.append("Username\t:").append(username + "\n");
      }
      if (role != null) {
         sb.append("Role\t:").append(role + "\n");
      }
      if (weight != null) {
         sb.append("Weight\t:").append(weight + "\n");
      }
      if (gender != null) {
         sb.append("Gender\t:").append(gender + "\n");
      }
      if (height != null) {
         sb.append("Height\t:").append(height + "\n");
      }
      return sb.toString();
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
}
