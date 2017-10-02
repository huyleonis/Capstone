package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Anhtbse61382 on 2/18/2017.
 */
@Repository
public interface DishRepo extends JpaRepository<Dish, Long> {
   List<Dish> findAllByDeletedFalse();

   @Query(value = "select * from dishes where user_id in" +
                  " (select followed_user_id from follows where user_id = ?1) " +
                  "and deleted = false", nativeQuery = true)
   List<Dish> findRelatedDishes(Long userID);

   Dish findByIdAndDeletedFalse(Long id);

   @Query(value = "select ingredient_id from dish_detail where dish_id = ?1", nativeQuery = true)
   List<BigInteger> findIngredientIdsOfDish(Long dishId);

   List<Dish> findBySearchStringContainingAndDeletedFalse(String searchString);
}
