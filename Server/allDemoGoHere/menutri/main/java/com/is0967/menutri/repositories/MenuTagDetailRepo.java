package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.MenuTagDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NBL.Huyen on 10-03-17.
 */
@Repository
public interface MenuTagDetailRepo extends JpaRepository<MenuTagDetail, Long> {

   @Modifying
   @Query(value = "insert into menutags_detail" +
           "(menu_id, tag_id) values(?1, ?2)", nativeQuery = true)
   void insertMenuTagDetail(Long menuId, Long tagId);

   @Modifying
   @Query(value = "delete from menutags_detail where menu_id=?1", nativeQuery = true)
   void deleteMenuTagDetailByMenuId(Long menuId);

   List<MenuTagDetail> findByMenuId(Long menuId);
}
