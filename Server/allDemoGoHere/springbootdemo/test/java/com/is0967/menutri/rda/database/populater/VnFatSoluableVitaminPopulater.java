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
import static com.is0967.menutri.rda.data.BodyData.Age.Y60;
import static com.is0967.menutri.rda.data.BodyData.Age.Y9;
import static com.is0967.menutri.rda.data.BodyData.Age.ZERO;
import static com.is0967.menutri.rda.data.Gender.FEMALE;
import static com.is0967.menutri.rda.data.Gender.MALE;
import static com.is0967.menutri.rda.data.MeasurementUnit.MICRO_GRAM;
import static com.is0967.menutri.rda.data.MeasurementUnit.MILLI_GRAM;
import static com.is0967.menutri.rda.data.NutrientType.VITAMIN_A;
import static com.is0967.menutri.rda.data.NutrientType.VITAMIN_D;
import static com.is0967.menutri.rda.data.NutrientType.VITAMIN_E;
import static com.is0967.menutri.rda.data.NutrientType.VITAMIN_K;
import static com.is0967.menutri.rda.data.RdaType.BASIC;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.database.populater.spi.Populater;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;

/**
 * Created by phuctran93 on 3/10/2017.
 */
class VnFatSoluableVitaminPopulater implements Populater
{

    @Override
    public void populate(NutrientDatabase database)
    {
        /* The order is matter */
        // FIXME: Implement a best match filter
        database.add(new NutrientData.Builder(VITAMIN_A, ZERO, M6, 375d, 375d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_A, M6, M12, 400d, 400d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_A, Y1, Y3, 400d, 400d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_A, Y3, Y6, 450d, 450d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_A, Y6, Y9, 500d, 500d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_A, Y9, Y18, 600d, 600d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_A, Y18, Y60, 600d, 600d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_A, Y60, MAX, 600d, 600d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_A, null, null, 800d, 800d, MICRO_GRAM, BASIC).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(VITAMIN_A, null, null, 850d, 850d, MICRO_GRAM, BASIC).gender(FEMALE).lactation(1, 12).build());
        database.add(new NutrientData.Builder(VITAMIN_A, Y9, Y18, 600d, 600d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_A, Y18, Y60, 500d, 500d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_A, Y60, MAX, 600d, 600d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_D, ZERO, M6, 5d, 5d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_D, M6, M12, 5d, 5d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_D, Y1, Y3, 5d, 5d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_D, Y3, Y6, 5d, 5d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_D, Y6, Y9, 5d, 5d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_D, Y9, Y18, 5d, 5d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_D, Y18, Y50, 5d, 5d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_D, Y50, Y60, 10d, 10d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_D, Y60, MAX, 15d, 15d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_D, null, null, 5d, 5d, MICRO_GRAM, BASIC).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(VITAMIN_D, null, null, 5d, 5d, MICRO_GRAM, BASIC).gender(FEMALE).lactation(1, 12).build());
        database.add(new NutrientData.Builder(VITAMIN_D, Y9, Y18, 5d, 5d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_D, Y18, Y50, 5d, 5d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_D, Y50, Y60, 10d, 10d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_D, Y60, MAX, 15d, 15d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_E, ZERO, M6, 3d, 3d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_E, M6, M12, 4d, 4d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_E, Y1, Y3, 5d, 5d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_E, Y3, Y6, 6d, 6d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_E, Y6, Y9, 7d, 7d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_E, Y9, Y12, 10d, 10d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_E, Y12, Y15, 12d, 12d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_E, Y15, Y18, 13d, 13d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_E, Y18, MAX, 12d, 12d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_E, null, null, 12d, 12d, MILLI_GRAM, BASIC).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(VITAMIN_E, null, null, 18d, 18d, MILLI_GRAM, BASIC).gender(FEMALE).lactation(1, 12).build());
        database.add(new NutrientData.Builder(VITAMIN_E, Y9, Y12, 11d, 11d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_E, Y12, Y15, 12d, 12d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_E, Y15, Y18, 12d, 12d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_E, Y18, MAX, 12d, 12d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_K, ZERO, M6, 6d, 6d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_K, M6, M12, 9d, 9d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_K, Y1, Y3, 13d, 13d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_K, Y3, Y6, 19d, 19d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_K, Y6, Y9, 24d, 24d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_K, Y9, Y12, 34d, 34d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_K, Y12, Y15, 50d, 50d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_K, Y15, Y18, 58d, 58d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_K, Y18, MAX, 59d, 59d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_K, null, null, 51d, 51d, MICRO_GRAM, BASIC).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(VITAMIN_K, null, null, 51d, 51d, MICRO_GRAM, BASIC).gender(FEMALE).lactation(1, 12).build());
        database.add(new NutrientData.Builder(VITAMIN_K, Y9, Y12, 35d, 35d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_K, Y12, Y15, 49d, 49d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_K, Y15, Y18, 50d, 50d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_K, Y18, MAX, 51d, 51d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
    }
}
