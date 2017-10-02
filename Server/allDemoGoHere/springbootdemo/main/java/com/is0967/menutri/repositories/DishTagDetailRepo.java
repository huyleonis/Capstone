package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.DishtagDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NBL.Huyen on 09-03-17.
 */
@Repository
public interface DishTagDetailRepo extends JpaRepository<DishtagDetail, Long> {
   @Modifying
   @Query(value = "insert into dishtag_detail(dish_id, tag_id) values(?1, ?2)", nativeQuery = true)
   void insertTagDetail(Long dishId, Long tagId);

   @Modifying
   @Query(value = "delete from dishtag_detail where dish_id = ?1 ", nativeQuery = true)
   void deleteDishTagDetailByDishId(Long dishId);

   List<DishtagDetail> findByDishId(Long dishId);

   @Query(value = "select * from dishtag_detail where dish_id in" +
                  " (select id from dishes where user_id in" +
                  "   (select followed_user_id from follows where user_id = ?1) " +
                  "  and id in (select dish_id from dish_category_detail where dish_category_id = ?2)" +
                  "  and deleted = FALSE)" +
                  "and tag_id = ?3", nativeQuery = true)
   List<DishtagDetail> findByTagIdAndDishCategoryAndRelatedToUser(Long userId, Long dishCategoryId, Long tagId);
}
