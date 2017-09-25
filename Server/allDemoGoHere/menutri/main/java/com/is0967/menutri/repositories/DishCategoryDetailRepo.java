package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.DishCategoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Anhtbse61382 on 3/2/2017.
 */
@Repository
public interface DishCategoryDetailRepo extends JpaRepository<DishCategoryDetail, Long> {
   List<DishCategoryDetail> findByDishId(Long Id);

   @Modifying
   @Query(value = "delete from dish_category_detail where dish_id = ?1", nativeQuery = true)
   void deleteDishCategoryDetailByDishID(Long dishId);

   @Modifying
   @Query(value = "insert into dish_category_detail" +
           "(dish_id, dish_category_id) values(?1, ?2)", nativeQuery = true)
   void insertDishCategoryDetail(Long dishId, Long categoryId);



   @Query(value = "select * from dish_category_detail where dish_id in \n" +
                  "  (select id from dishes where user_id in" +
                  "    (select followed_user_id from follows where user_id = ?1) and deleted = false)" +
                  "and dish_category_id = ?2", nativeQuery = true)
   List<DishCategoryDetail> findDishCategoryDetailRelatedToUser(Long userId, Long dishCategoryId);
}
