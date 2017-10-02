package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.IngredientCategoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NBL.Huyen on 15-02-17.
 */
@Repository
public interface IngredientCategoryDetailRepo extends JpaRepository<IngredientCategoryDetail, Long> {
   List<IngredientCategoryDetail> findByIngredientId(Long ingredientId);
}
