package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.DishTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NBL.Huyen on 09-03-17.
 */
@Repository
public interface DishTagRepo extends JpaRepository<DishTag, Long> {
   DishTag findByNameLike(String name);

   @Query(value = "select * from dish_tags order by counter limit 3", nativeQuery = true)
   List<DishTag> findTop3Tags();
}
