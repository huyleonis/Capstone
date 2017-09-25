package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by an on 2/17/2017.
 */
@Entity(name = "dishes")
public class Dish {
   public transient static final int TYPE_OWN = 0;
   public transient static final int TYPE_CLONE = 1;

   @Id
   @GeneratedValue(generator = "dishes_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "dishes_id_seq", sequenceName = "dishes_id_seq")
   @Column
   private Long id;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   @Column
   private String imageurl;

   @Column
   private String description;

   @Column
   private String name;

   @Column
   private Integer type;

   @Column
   private Integer ration;

   @Column
   private Boolean deleted;

   @Column(name = "search_string")
   private String searchString;

   @ManyToOne
   @JoinColumn(name = "original_user_id")
   private User originalUser;

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

   public Integer getType() {
      return type;
   }

   public void setType(Integer type) {
      this.type = type;
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

   public String getImageurl() {
      return imageurl;
   }

   public void setImageurl(String imageurl) {
      this.imageurl = imageurl;
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

   @Override
   public String toString() {
      return "Dish{" +
              "id=" + id==null?"null":id +
              ", user=" + user ==null?"null": user +
              ", imageurl='" + imageurl==null?"null":imageurl + '\'' +
              ", description='" + description ==null?"null": description + '\'' +
              ", name='" + name==null?"null":name + '\'' +
              '}';
   }


   public Dish(User user, String imageurl, String description, String name, String searchString) {
      this.user = user;
      this.imageurl = imageurl;
      this.description = description;
      this.name = name;
      this.searchString = searchString;
   }

   public Dish() {
   }
}
