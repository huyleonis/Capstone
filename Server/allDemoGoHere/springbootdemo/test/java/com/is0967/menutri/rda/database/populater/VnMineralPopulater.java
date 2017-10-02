package com.is0967.menutri.rda.database.populater;

import static com.is0967.menutri.rda.data.BodyData.Age.M12;
import static com.is0967.menutri.rda.data.BodyData.Age.M6;
import static com.is0967.menutri.rda.data.BodyData.Age.MAX;
import static com.is0967.menutri.rda.data.BodyData.Age.Y1;
import static com.is0967.menutri.rda.data.BodyData.Age.Y12;
import static com.is0967.menutri.rda.data.BodyData.Age.Y15;
import static com.is0967.menutri.rda.data.BodyData.Age.Y18;
import static com.is0967.menutri.rda.data.BodyData.Age.Y3;
import static com.is0967.menutri.rda.data.BodyData.Age.Y50;
import static com.is0967.menutri.rda.data.BodyData.Age.Y6;
import static com.is0967.menutri.rda.data.BodyData.Age.Y9;
import static com.is0967.menutri.rda.data.BodyData.Age.ZERO;
import static com.is0967.menutri.rda.data.Gender.FEMALE;
import static com.is0967.menutri.rda.data.Gender.MALE;
import static com.is0967.menutri.rda.data.MeasurementUnit.MILLI_GRAM;
import static com.is0967.menutri.rda.data.NutrientType.CALCIUM;
import static com.is0967.menutri.rda.data.NutrientType.MAGNESIUM;
import static com.is0967.menutri.rda.data.NutrientType.PHOSPHORUS;
import static com.is0967.menutri.rda.data.RdaType.BASIC;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.database.populater.spi.Populater;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;

/**
 * Created by phuctran93 on 3/8/2017.
 */
class VnMineralPopulater implements Populater
{

    @Override
    public void populate(NutrientDatabase database)
    {
        /* The order is importance */
        database.add(new NutrientData.Builder(CALCIUM, ZERO, M6, 300d, 300d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CALCIUM, M6, M12, 400d, 400d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CALCIUM, Y1, Y3, 500d, 500d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CALCIUM, Y3, Y6, 600d, 600d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CALCIUM, Y6, Y9, 700d, 700d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CALCIUM, Y9, Y18, 1000d, 1000d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CALCIUM, null, null, 1000d, 1000d, MILLI_GRAM, BASIC).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(CALCIUM, null, null, 1000d, 1000d, MILLI_GRAM, BASIC).gender(FEMALE).lactation(1, 12).build());
        database.add(new NutrientData.Builder(CALCIUM, Y18, Y50, 700d, 700d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CALCIUM, Y50, MAX, 1000d, 1000d, MILLI_GRAM, BASIC).build());

        database.add(new NutrientData.Builder(PHOSPHORUS, ZERO, M6, 90d, 90d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(PHOSPHORUS, M6, M12, 275d, 275d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(PHOSPHORUS, Y1, Y3, 460d, 460d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(PHOSPHORUS, Y3, Y6, 500d, 500d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(PHOSPHORUS, Y6, Y9, 500d, 500d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(PHOSPHORUS, Y9, Y18, 1250d, 1250d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(PHOSPHORUS, Y18, MAX, 700d, 700d, MILLI_GRAM, BASIC).build());

        database.add(new NutrientData.Builder(MAGNESIUM, ZERO, M6, 36d, 36d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(MAGNESIUM, M6, M12, 54d, 54d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(MAGNESIUM, Y1, Y3, 65d, 65d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(MAGNESIUM, Y3, Y6, 76d, 76d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(MAGNESIUM, Y6, Y9, 100d, 100d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(MAGNESIUM, Y9, Y12, 155d, 155d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(MAGNESIUM, Y12, Y15, 225d, 225d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(MAGNESIUM, Y15, Y18, 260d, 260d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(MAGNESIUM, Y9, Y12, 160d, 160d, MILLI_GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(MAGNESIUM, Y12, Y15, 220d, 220d, MILLI_GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(MAGNESIUM, Y15, Y18, 240d, 240d, MILLI_GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(MAGNESIUM, null, null, 250d, 250d, MILLI_GRAM, BASIC).gender(FEMALE).lactation(1, 12).build());
        database.add(new NutrientData.Builder(MAGNESIUM, Y18, MAX, 205d, 205d, MILLI_GRAM, BASIC).build());
    }
}
