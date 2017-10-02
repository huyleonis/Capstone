package com.is0967.menutri.rda.database.populater;

import static com.is0967.menutri.rda.data.BodyData.Age.M12;
import static com.is0967.menutri.rda.data.BodyData.Age.M6;
import static com.is0967.menutri.rda.data.BodyData.Age.MAX;
import static com.is0967.menutri.rda.data.BodyData.Age.Y1;
import static com.is0967.menutri.rda.data.BodyData.Age.Y18;
import static com.is0967.menutri.rda.data.BodyData.Age.Y60;
import static com.is0967.menutri.rda.data.BodyData.Age.Y9;
import static com.is0967.menutri.rda.data.BodyData.Age.ZERO;
import static com.is0967.menutri.rda.data.Gender.FEMALE;
import static com.is0967.menutri.rda.data.MeasurementUnit.MILLI_LITRE;
import static com.is0967.menutri.rda.data.MeasurementUnit.MILLI_LITRE_PER_KILO_CALORIE;
import static com.is0967.menutri.rda.data.NutrientType.WATER;
import static com.is0967.menutri.rda.data.RdaType.BASIC;
import static com.is0967.menutri.rda.data.RdaType.EXTRA;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.database.populater.spi.Populater;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;

/**
 * Created by phuctran93 on 3/10/2017.
 */
class VnWaterPopulater implements Populater
{

    @Override
    public void populate(NutrientDatabase database)
    {
        database.add(new NutrientData.Builder(WATER, ZERO, M6, 1.5d, 1.5d, MILLI_LITRE_PER_KILO_CALORIE, BASIC).build());
        database.add(new NutrientData.Builder(WATER, M6, M12, 1.5d, 1.5d, MILLI_LITRE_PER_KILO_CALORIE, BASIC).build());
        database.add(new NutrientData.Builder(WATER, Y1, Y9, 1.5d, 1.5d, MILLI_LITRE_PER_KILO_CALORIE, BASIC).build());
        database.add(new NutrientData.Builder(WATER, Y9, Y18, 1.5d, 1.5d, MILLI_LITRE_PER_KILO_CALORIE, BASIC).build());
        database.add(new NutrientData.Builder(WATER, Y18, Y60, 1d, 1d, MILLI_LITRE_PER_KILO_CALORIE, BASIC).build());
        database.add(new NutrientData.Builder(WATER, Y60, MAX, 1d, 1d, MILLI_LITRE_PER_KILO_CALORIE, BASIC).build());
        database.add(new NutrientData.Builder(WATER, null, null, 300d, 300d, MILLI_LITRE, EXTRA).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(WATER, null, null, 750d, 950d, MILLI_LITRE, EXTRA).gender(FEMALE).lactation(1, 6).build());
        database.add(new NutrientData.Builder(WATER, null, null, 750d, 950d, MILLI_LITRE, EXTRA).gender(FEMALE).lactation(6, 12).build());
    }
}
