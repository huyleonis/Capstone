package com.is0967.menutri.rda.database.populater;

import static com.is0967.menutri.rda.data.MeasurementUnit.GRAM;
import static com.is0967.menutri.rda.data.MeasurementUnit.GRAM_PER_KILO_CALORIE;
import static com.is0967.menutri.rda.data.MeasurementUnit.PERCENT_OF_TOTAL_ENERGY;
import static com.is0967.menutri.rda.data.NutrientType.CARBOHYDRATE_AND_FIBER;
import static com.is0967.menutri.rda.data.NutrientType.NON_STARCH_AND_FIBER;
import static com.is0967.menutri.rda.data.NutrientType.OLIGO_SACCHARIDES;
import static com.is0967.menutri.rda.data.NutrientType.SUGARS;
import static com.is0967.menutri.rda.data.RdaType.BASIC;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.database.populater.spi.Populater;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;

/**
 * Created by phuctran93 on 3/4/2017.
 */
class VnCarbAndFiberPopulater implements Populater
{

    @Override
    public void populate(NutrientDatabase database)
    {
        database.add(new NutrientData.Builder(CARBOHYDRATE_AND_FIBER, null, null, 61d, 71d, PERCENT_OF_TOTAL_ENERGY, BASIC).build());
        database.add(new NutrientData.Builder(OLIGO_SACCHARIDES, null, null, 42.7d, 49.7d, PERCENT_OF_TOTAL_ENERGY, BASIC).build());
        database.add(new NutrientData.Builder(SUGARS, null, null, null, 7.1d, PERCENT_OF_TOTAL_ENERGY, BASIC).build());
        database.add(new NutrientData.Builder(NON_STARCH_AND_FIBER, null, null, 0.014d, 0.014d, GRAM_PER_KILO_CALORIE, BASIC).limit(20d, null, GRAM).build());
    }
}
