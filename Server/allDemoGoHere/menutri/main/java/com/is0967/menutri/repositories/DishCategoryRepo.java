package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.DishCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Anhtbse61382 on 2/19/2017.
 */
@Repository
public interface DishCategoryRepo extends JpaRepository<DishCategory, Long> {
}
