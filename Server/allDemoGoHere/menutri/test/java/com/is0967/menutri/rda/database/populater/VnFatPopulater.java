package com.is0967.menutri.rda.database.populater;

import static com.is0967.menutri.rda.data.BodyData.Age.M12;
import static com.is0967.menutri.rda.data.BodyData.Age.M6;
import static com.is0967.menutri.rda.data.BodyData.Age.MAX;
import static com.is0967.menutri.rda.data.BodyData.Age.Y1;
import static com.is0967.menutri.rda.data.BodyData.Age.Y18;
import static com.is0967.menutri.rda.data.BodyData.Age.Y3;
import static com.is0967.menutri.rda.data.BodyData.Age.ZERO;
import static com.is0967.menutri.rda.data.Gender.FEMALE;
import static com.is0967.menutri.rda.data.Gender.MALE;
import static com.is0967.menutri.rda.data.MeasurementUnit.PERCENT_OF_TOTAL_ENERGY;
import static com.is0967.menutri.rda.data.NutrientType.ALPHA_LINOLENIC_ACID;
import static com.is0967.menutri.rda.data.NutrientType.FAT;
import static com.is0967.menutri.rda.data.NutrientType.LINOLEIC_ACID;
import static com.is0967.menutri.rda.data.RdaType.BASIC;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.database.populater.spi.Populater;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;

/**
 * Created by phuctran93 on 3/3/2017.
 */
class VnFatPopulater implements Populater
{

    @Override
    public void populate(NutrientDatabase database)
    {
        database.add(new NutrientData.Builder(FAT, ZERO, M6, 45d, 50d, PERCENT_OF_TOTAL_ENERGY, BASIC).build());
        database.add(new NutrientData.Builder(FAT, M6, M12, 40d, 40d, PERCENT_OF_TOTAL_ENERGY, BASIC).build());
        database.add(new NutrientData.Builder(FAT, Y1, Y3, 35d, 40d, PERCENT_OF_TOTAL_ENERGY, BASIC).build());
        database.add(new NutrientData.Builder(FAT, Y3, Y18, 20d, 25d, PERCENT_OF_TOTAL_ENERGY, BASIC).build());
        database.add(new NutrientData.Builder(FAT, Y18, MAX, 18d, 25d, PERCENT_OF_TOTAL_ENERGY, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(FAT, Y18, MAX, 20d, 25d, PERCENT_OF_TOTAL_ENERGY, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(LINOLEIC_ACID, ZERO, M12, 4.5d, 4.5d, PERCENT_OF_TOTAL_ENERGY, BASIC).build());
        database.add(new NutrientData.Builder(LINOLEIC_ACID, Y1, Y3, 3d, 3d, PERCENT_OF_TOTAL_ENERGY, BASIC).build());
        database.add(new NutrientData.Builder(LINOLEIC_ACID, Y3, Y18, 2d, 2d, PERCENT_OF_TOTAL_ENERGY, BASIC).build());
        database.add(new NutrientData.Builder(LINOLEIC_ACID, Y18, MAX, 2d, 2d, PERCENT_OF_TOTAL_ENERGY, BASIC).build());
        database.add(new NutrientData.Builder(ALPHA_LINOLENIC_ACID, null, null, 0.5d, 0.5d, PERCENT_OF_TOTAL_ENERGY, BASIC).build());
    }
}
