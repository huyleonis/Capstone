package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NBL.Huyen on 21-02-17.
 */
@Repository
public interface MenuRepo extends JpaRepository<Menu, Long> {
   @Query(value = "insert into menu" +
           "(user_id, description, name, search_string) values(?1,?2,?3,?4)", nativeQuery = true)
   Menu insertMenu(Long userId, String description, String name, String searchString);

   List<Menu> findAllByParentMenuId(Long parentMenuId);

   Menu findByIdAndDeletedFalse(Long menuId);

   @Query(value = "" +
           "select * from menu where id in " +
           "(select parent_menu_id from menu where id in " +
               "(select parent_menu_id from menu where id in" +
                     "(select menu_id from menu_detail where dish_id = ?1)" +
               ") " +
           ") and user_id = ?2", nativeQuery = true)
   List<Menu> findMenusContainDish(Long dishId, Long userId);

   List<Menu> findBySearchStringContainingAndDeletedFalse(String searchString);

   List<Menu> findByUserIdAndParentMenuIdNullAndDeletedFalse(Long userId);
}
