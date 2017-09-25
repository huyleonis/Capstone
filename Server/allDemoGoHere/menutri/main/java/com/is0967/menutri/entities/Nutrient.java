package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by an on 2/17/2017.
 */
@Entity(name = "nutrients")
public class Nutrient {
   @Id
   @GeneratedValue(generator = "nutrients_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "nutrients_id_seq", sequenceName = "nutrients_id_seq")
   private Long id;

   @Column
   private String name;

   @Column(name = "unit_of_measurement")
   private String unitOfMeasurement;

   private String keyName;

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof Nutrient)) return false;

      Nutrient nutrient = (Nutrient) o;

      return name.equals(nutrient.name);
   }

   @Override
   public int hashCode()
   {
      return name.hashCode();
   }

   @Override
   public String toString()
   {
      return "Nutrient{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", unit='" + unitOfMeasurement + '\'' +
              ", keyName='" + keyName + '\'' +
              '}';
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

   public String getUnitOfMeasurement() {
      return unitOfMeasurement;
   }

   public void setUnitOfMeasurement(String unitOfMeasurement) {
      this.unitOfMeasurement = unitOfMeasurement;
   }

   public String getKeyName()
   {
      return keyName;
   }

   public void setKeyName(String keyName)
   {
      this.keyName = keyName;
   }
}
