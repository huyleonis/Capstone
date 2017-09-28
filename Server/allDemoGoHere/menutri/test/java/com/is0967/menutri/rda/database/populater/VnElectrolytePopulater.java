package com.is0967.menutri.rda.database.populater;

import static com.is0967.menutri.rda.data.BodyData.Age.M12;
import static com.is0967.menutri.rda.data.BodyData.Age.M6;
import static com.is0967.menutri.rda.data.BodyData.Age.MAX;
import static com.is0967.menutri.rda.data.BodyData.Age.Y1;
import static com.is0967.menutri.rda.data.BodyData.Age.Y18;
import static com.is0967.menutri.rda.data.BodyData.Age.Y2;
import static com.is0967.menutri.rda.data.BodyData.Age.Y5;
import static com.is0967.menutri.rda.data.BodyData.Age.Y9;
import static com.is0967.menutri.rda.data.BodyData.Age.ZERO;
import static com.is0967.menutri.rda.data.MeasurementUnit.MILLI_GRAM;
import static com.is0967.menutri.rda.data.NutrientType.CHLORIDE;
import static com.is0967.menutri.rda.data.NutrientType.POTASSIUM;
import static com.is0967.menutri.rda.data.NutrientType.SODIUM;
import static com.is0967.menutri.rda.data.RdaType.BASIC;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.database.populater.spi.Populater;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;

/**
 * Created by phuctran93 on 3/9/2017.
 */
class VnElectrolytePopulater implements Populater
{

    @Override
    public void populate(NutrientDatabase database)
    {
        database.add(new NutrientData.Builder(SODIUM, ZERO, M6, 120d, 120d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(SODIUM, M6, M12, 200d, 200d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(SODIUM, Y1, Y2, 225d, 225d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(SODIUM, Y2, Y5, 300d, 300d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(SODIUM, Y5, Y9, 400d, 400d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(SODIUM, Y9, Y18, 500d, 500d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(SODIUM, Y18, MAX, 500d, 500d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CHLORIDE, ZERO, M6, 180d, 180d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CHLORIDE, M6, M12, 300d, 300d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CHLORIDE, Y1, Y2, 350d, 350d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CHLORIDE, Y2, Y5, 500d, 500d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CHLORIDE, Y5, Y9, 600d, 600d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CHLORIDE, Y9, Y18, 750d, 750d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CHLORIDE, Y18, MAX, 750d, 750d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(POTASSIUM, ZERO, M6, 500d, 500d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(POTASSIUM, M6, M12, 700d, 700d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(POTASSIUM, Y1, Y2, 1000d, 1000d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(POTASSIUM, Y2, Y5, 1400d, 1400d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(POTASSIUM, Y5, Y9, 1600d, 1600d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(POTASSIUM, Y9, Y18, 2000d, 2000d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(POTASSIUM, Y18, MAX, 2000d, 2000d, MILLI_GRAM, BASIC).build());
    }
}
