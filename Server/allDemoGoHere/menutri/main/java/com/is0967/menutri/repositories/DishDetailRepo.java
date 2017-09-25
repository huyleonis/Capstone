package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.DishDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Anhtbse61382 on 3/2/2017.
 */
@Repository
public interface DishDetailRepo extends JpaRepository<DishDetail, Long> {
   List<DishDetail> findByDishId(Long dishId);

   DishDetail findByDishIdAndIngredientId(Long dishId, Long ingredientId);

   @Query(value = "select * from dish_detail \n" +
                  "where dish_id in " +
                  " (select dish_id from dish_category_detail where dish_category_id = ?1) " +
                  "and dish_id in" +
                  " (select id from dishes where user_id in " +
                  "  (select followed_user_id from follows where user_id = ?2) " +
                  "  and deleted = false)" +
                  "and ingredient_id in (?3)",nativeQuery = true)
   List<DishDetail> findByIngredientIdIn(Long dishCategoryId, Long userId, List<Long> listIngredientIds);

   @Query(value = "select * from dish_detail where dish_id in \n" +
                  "  (select id from dishes where user_id in\n" +
                  "    (select followed_user_id from follows where user_id = 1)) " +
                  "and ingredient_id = ?2", nativeQuery = true)
   List<DishDetail> findByRelatedUserAndIngredientId(Long userId, Long ingredientId);

   @Modifying
   @Query(value = "insert into dish_detail" +
           "(ingredient_id, dish_id, amount) values(?1, ?2, ?3)", nativeQuery = true)
   void insertDishDetail(Long ingredientId, Long dishId, Double amount);

   @Modifying
   @Query(value = "delete from dish_detail where dish_id = ?1")
   void deleteDishDetailByDishId(Long dishId);


   @Query(value = "select ingredient_id from dish_detail where dish_id = ?1", nativeQuery = true)
   List<Long> findIngredientIdsOfDish(Long dishId);

}
