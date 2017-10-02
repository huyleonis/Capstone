package com.is0967.menutri.entities;

import javax.persistence.*;

@Entity(name = "nutritional_needs")
public class NutritionalNeed {
   @Id
   @GeneratedValue(generator = "nutritional_needs_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "nutritional_needs_id_seq", sequenceName = "nutritional_needs_id_seq")
   private long id;

   private String name;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   private Boolean deleted;

   public Boolean getDeleted() {
      return deleted;
   }

   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }

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

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public NutritionalNeed() {
   }
}