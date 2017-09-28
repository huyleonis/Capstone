package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.NutritionalNeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by phuctran93 on 2/21/2017.
 */
@Repository
public interface NutritionalNeedRepo extends JpaRepository<NutritionalNeed, Long> {
   NutritionalNeed findByIdAndDeletedFalse(Long id);

   List<NutritionalNeed> findByUserIdAndDeletedFalse(Long userId);
}
