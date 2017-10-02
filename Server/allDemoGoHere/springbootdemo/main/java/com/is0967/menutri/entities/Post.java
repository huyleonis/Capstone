package com.is0967.menutri.entities;


import javax.persistence.*;

/**
 * Created by NBL.Huyen on 15-02-17.
 */

/**
 * @Entity(name = "tên bảng trong db")
 */
@Entity(name = "posts")
public class Post {
   public transient static final int SHARE = 0;
   public transient static final int INFO = 1;
   public transient static final int MENU = 2;
   public transient static final int DISH = 3;

   @Id
   @GeneratedValue(generator = "posts_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "posts_id_seq", sequenceName = "posts_id_seq")
   private Long id;

   @Column(name = "user_id")
   private Long userId;

   private Integer type;

   private Long itemId;

   private Boolean deleted;

   private Boolean featured;

   @Column(name = "create_date")
   private Long createDate;

   public Boolean getFeatured() {
      return featured;
   }

   public void setFeatured(Boolean featured) {
      this.featured = featured;
   }

   public Boolean getDeleted() {
      return deleted;
   }

   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }

   public Integer getType() {
      return type;
   }

   public void setType(Integer type) {
      this.type = type;
   }

   public Long getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Long createDate) {
      this.createDate = createDate;
   }

   @Override
   public String toString() {
      {
         StringBuilder sb = new StringBuilder();
         if (id != null) {
            sb.append("Id\t:").append(id + "\n");
         }
         if (type != null) {
            sb.append("Type\t:").append(type + "\n");
         }
         if (userId != null) {
            sb.append("UserId\t:").append(userId + "\n");
         }
         if (itemId != null) {
            sb.append("ItemId\t:").append(itemId + "\n");
         }
         return sb.toString();
      }
   }

   public Post() {
   }

   public Post(Long userId, Integer type, Long itemId) {
      this.userId = userId;
      this.type = type;
      this.itemId = itemId;
   }

   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getItemId() {
      return itemId;
   }

   public void setItemId(Long itemId) {
      this.itemId = itemId;
   }
}
