package com.is0967.menutri.services;

import com.is0967.menutri.entities.Nutrient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by phuctran93 on 2/20/2017.
 */
@Service
public class NutrientServiceImpl extends AbstractServiceImpl implements NutrientService {
    @Override
    public List<Nutrient> readAll() {
        return nutrientRepo.findByOrderByIdAsc();
    }
}
