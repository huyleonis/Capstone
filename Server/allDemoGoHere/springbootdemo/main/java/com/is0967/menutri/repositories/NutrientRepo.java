package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by phuctran93 on 2/20/2017.
 */
public interface NutrientRepo extends JpaRepository<Nutrient, Long> {
   List<Nutrient>findByOrderByIdAsc();
}
