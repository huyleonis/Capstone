package com.is0967.menutri.rda.database.spi;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.database.filter.NutrientFilter;
import java.util.List;

/**
 * Created by phuctran93 on 3/3/2017.
 */
public interface NutrientDatabase
{

    void add(NutrientData nutrientData);

    List<NutrientData> find(NutrientFilter filter);

    NutrientData findFirstBasic(NutrientFilter filter);

    NutrientData findFirstExtra(NutrientFilter filter);

    List<NutrientData> findBasic(NutrientFilter filter);

    List<NutrientData> findExtra(NutrientFilter filter);

    NutrientData findCalculatedData(NutrientFilter filter);
}
