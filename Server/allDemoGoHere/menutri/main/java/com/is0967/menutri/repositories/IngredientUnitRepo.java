package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.IngredientUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NBL.Huyen on 22-03-17.
 */
@Repository
public interface IngredientUnitRepo extends JpaRepository<IngredientUnit, Long> {
   List<IngredientUnit> findByIngredientId(Long ingredientId);

   @Modifying
   @Query(value = "insert into ingredient_unit(id, ingredient_id, name, amount) " +
           "values (?1, ?2, 'g', 1)", nativeQuery = true)
   void insertDefaultGramUnit(Long id, Long ingredientId);
}
