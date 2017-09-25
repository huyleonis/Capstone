package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.MenuDetail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by an on 2/22/2017.
 */
@Repository
public interface MenuDetailRepo extends CrudRepository<MenuDetail, Long> {

   @Modifying
   @Query(value = "insert into menu_detail " +
           "(menu_id, dish_id, amount) values(?1, ?2, ?3)", nativeQuery = true)
   void insertMenuDetail(Long menuId, Long dishId, Double amount);

   void deleteMenuDetailByDishId(Long dishId);

   void deleteMenuDetailByMenuId(Long menuId);

   List<MenuDetail> findByMenuId(Long menuId);
}
