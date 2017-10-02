package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by an on 2/17/2017.
 */
@Entity(name = "nutritional_need_detail")
public class NutritionalNeedDetail {

   @Id
   @GeneratedValue(generator = "nutritional_need_detail_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "nutritional_need_detail_id_seq", sequenceName = "nutritional_need_detail_id_seq")
   @Column
   private Long id;

   @Column(name = "nutrient_id")
   private Long nutrientId;

   @Column(name = "nutritional_need_id")
   private Long nutritionalNeedId;

   @Column
   private Double amount;

   public NutritionalNeedDetail() {
   }

   public NutritionalNeedDetail(Long nutrientId, Long nutritionalNeedId, Double amount) {
      this.nutrientId = nutrientId;
      this.nutritionalNeedId = nutritionalNeedId;
      this.amount = amount;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getNutrientId() {
      return nutrientId;
   }

   public void setNutrientId(Long nutrientId) {
      this.nutrientId = nutrientId;
   }

   public Long getNutritionalNeedId() {
      return nutritionalNeedId;
   }

   public void setNutritionalNeedId(Long nutritionalNeedId) {
      this.nutritionalNeedId = nutritionalNeedId;
   }

   public Double getAmount() {
      return amount;
   }

   public void setAmount(Double amount) {
      this.amount = amount;
   }

}
