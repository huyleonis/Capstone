package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.NutritionalNeedDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by phuctran93 on 2/21/2017.
 */
public interface NutritionalNeedDetailRepo extends JpaRepository<NutritionalNeedDetail, Long> {
   List<NutritionalNeedDetail> findByNutritionalNeedId(Long id);

   NutritionalNeedDetail findByNutritionalNeedIdAndNutrientId(Long nutritionalNeedId, Long nutrientId);
}
