package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.IngredientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by an on 2/23/2017.
 */
@Repository
public interface IngredientDetailRepo extends JpaRepository<IngredientDetail, Long> {
   List<IngredientDetail> findByIngredientIdOrderByNutrientId(Long Id);

   @Query(value = "insert into ingredient_detail" +
           "(ingredient_id, nutrient_id, amount) values (?1, ?2, ?3)", nativeQuery = true)
   void insertIngredientDetail(Long ingredientId, Long nutrientId, Double amount);
}
