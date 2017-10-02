package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by NBL.Huyen on 15-02-17.
 */


@Repository
public interface IngredientCategoryRepo extends JpaRepository<Ingredient, Long> {
}
