package com.is0967.menutri.rda.database.populater;

import static com.is0967.menutri.rda.data.ActivityLevel.HEAVY;
import static com.is0967.menutri.rda.data.ActivityLevel.LIGHT;
import static com.is0967.menutri.rda.data.ActivityLevel.MODERATE;
import static com.is0967.menutri.rda.data.Gender.FEMALE;
import static com.is0967.menutri.rda.data.Gender.MALE;
import static com.is0967.menutri.rda.data.MeasurementUnit.ACTIVITY_FACTOR;
import static com.is0967.menutri.rda.data.MeasurementUnit.KILO_CALORIE;
import static com.is0967.menutri.rda.data.NutrientType.ENERGY;
import static com.is0967.menutri.rda.data.RdaType.EXTRA;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.database.populater.spi.Populater;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;

/**
 * Created by phuctran93 on 3/2/2017.
 */
class VnEnergyPopulater implements Populater
{

    @Override
    public void populate(NutrientDatabase database)
    {
//        database.add(new NutrientData.Builder(ENERGY, ZERO, M6, 555d, 555d, KILO_CALORIE, CALCULATED).build());
//        database.add(new NutrientData.Builder(ENERGY, M6, M12, 710d, 710d, KILO_CALORIE, CALCULATED).build());
//        database.add(new NutrientData.Builder(ENERGY, Y1, Y3, 1180d, 1180d, KILO_CALORIE, CALCULATED).build());
//        database.add(new NutrientData.Builder(ENERGY, Y3, Y6, 1470d, 1470d, KILO_CALORIE, CALCULATED).build());
//        database.add(new NutrientData.Builder(ENERGY, Y6, Y9, 1825d, 1825d, KILO_CALORIE, CALCULATED).build());
//        database.add(new NutrientData.Builder(ENERGY, Y9, Y12, 2110d, 2110d, KILO_CALORIE, CALCULATED).gender(MALE).build());
//        database.add(new NutrientData.Builder(ENERGY, Y12, Y15, 2650d, 2650d, KILO_CALORIE, CALCULATED).gender(MALE).build());
//        database.add(new NutrientData.Builder(ENERGY, Y15, Y18, 2980d, 2980d, KILO_CALORIE, CALCULATED).gender(MALE).build());
//        database.add(new NutrientData.Builder(ENERGY, Y9, Y12, 2010d, 2010d, KILO_CALORIE, CALCULATED).gender(FEMALE).build());
//        database.add(new NutrientData.Builder(ENERGY, Y12, Y15, 2200d, 2200d, KILO_CALORIE, CALCULATED).gender(FEMALE).build());
//        database.add(new NutrientData.Builder(ENERGY, Y15, Y18, 2240d, 2240d, KILO_CALORIE, CALCULATED).gender(FEMALE).build());
        database.add(new NutrientData.Builder(ENERGY, null, null, 1.55d, 1.55d, ACTIVITY_FACTOR, EXTRA).gender(MALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(ENERGY, null, null, 1.78d, 1.78d, ACTIVITY_FACTOR, EXTRA).gender(MALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(ENERGY, null, null, 2.1d, 2.1d, ACTIVITY_FACTOR, EXTRA).gender(MALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(ENERGY, null, null, 1.56d, 1.56d, ACTIVITY_FACTOR, EXTRA).gender(FEMALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(ENERGY, null, null, 1.64d, 1.64d, ACTIVITY_FACTOR, EXTRA).gender(FEMALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(ENERGY, null, null, 1.82d, 1.82d, ACTIVITY_FACTOR, EXTRA).gender(FEMALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(ENERGY, null, null, 360d, 360d, KILO_CALORIE, EXTRA).pregnancy(4, 6).build());
        database.add(new NutrientData.Builder(ENERGY, null, null, 475d, 475d, KILO_CALORIE, EXTRA).pregnancy(7, 9).build());
        database.add(new NutrientData.Builder(ENERGY, null, null, 505d, 505d, KILO_CALORIE, EXTRA).lactation(1, 12).healthy(true).build());
        database.add(new NutrientData.Builder(ENERGY, null, null, 675d, 675d, KILO_CALORIE, EXTRA).lactation(1, 12).healthy(false).build());
        database.add(new NutrientData.Builder(ENERGY, null, null, 1.1d, 1.1d, ACTIVITY_FACTOR, EXTRA).hiv(true).build());
        database.add(new NutrientData.Builder(ENERGY, null, null, 1.2d, 1.3d, ACTIVITY_FACTOR, EXTRA).aids(true).build());
    }
}
