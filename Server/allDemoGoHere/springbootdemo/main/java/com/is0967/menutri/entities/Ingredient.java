package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by NBL.Huyen on 15-02-17.
 */
/**
 * @Entity(name = "tên bảng trong db")
 */
@Entity(name = "ingredients")
public class Ingredient {
   @Id
   @GeneratedValue(generator = "ingredients_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "ingredients_id_seq", sequenceName = "ingredients_id_seq")
   @Column
   /**
    * GeneratedValue(strategy Identity = autoincrement, tên generator lấy trong "Schema\Public\Sequences\" của Postgres Pgadmin
    */
   private Long id;

   @Column
   private String name;
   @Column(name = "imageurl")
   private String imageurl;

   @Column(name = "rejection_percent")
   private Double rejectionPercent;

   @Column
   private Boolean deleted;

   @Column(name = "search_string")
   private String searchString;

   @Column(name = "create_date")
   private Long createDate;

   @Column(name = "last_edit")
   private Long lastEdit;

   public Long getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Long createDate) {
      this.createDate = createDate;
   }

   public Long getLastEdit() {
      return lastEdit;
   }

   public void setLastEdit(Long lastEdit) {
      this.lastEdit = lastEdit;
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

   public Double getRejectionPercent() {
      return rejectionPercent;
   }

   public void setRejectionPercent(Double rejectionPercent) {
      this.rejectionPercent = rejectionPercent;
   }

   @Column
   private String reference;

   public String getReference() {
      return reference;
   }

   public void setReference(String reference) {
      this.reference = reference;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getImageurl() {
      return imageurl;
   }

   public void setImageurl(String imageurl) {
      this.imageurl = imageurl;
   }
}
