package com.is0967.menutri.rda.database.populater;

import static com.is0967.menutri.rda.data.BioAvailability.HIGH;
import static com.is0967.menutri.rda.data.BioAvailability.INTERMEDIATE;
import static com.is0967.menutri.rda.data.BioAvailability.LOW;
import static com.is0967.menutri.rda.data.BodyData.Age.M12;
import static com.is0967.menutri.rda.data.BodyData.Age.M6;
import static com.is0967.menutri.rda.data.BodyData.Age.MAX;
import static com.is0967.menutri.rda.data.BodyData.Age.Y1;
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
import static com.is0967.menutri.rda.data.MeasurementUnit.DOUBLE_SD;
import static com.is0967.menutri.rda.data.MeasurementUnit.MICRO_GRAM;
import static com.is0967.menutri.rda.data.MeasurementUnit.MICRO_GRAM_PER_KILO_GRAM;
import static com.is0967.menutri.rda.data.MeasurementUnit.MILLI_GRAM;
import static com.is0967.menutri.rda.data.NutrientType.IRON;
import static com.is0967.menutri.rda.data.NutrientType.SELENIUM;
import static com.is0967.menutri.rda.data.NutrientType.ZINC;
import static com.is0967.menutri.rda.data.RdaType.BASIC;
import static com.is0967.menutri.rda.data.RdaType.EXTRA;
import static com.is0967.menutri.rda.data.RdaType.FACTOR;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.database.populater.spi.Populater;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;

/**
 * Created by phuctran93 on 3/9/2017.
 */
class VnTraceElementPopulater implements Populater
{

    @Override
    public void populate(NutrientDatabase database)
    {
        // FIXME: implement the best match filter so that the inserted order doesn't affect the calculation result.
        database.add(new NutrientData.Builder(IRON, ZERO, M6, 0.93d, 0.93d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).build());
        database.add(new NutrientData.Builder(IRON, M6, M12, 12.4d, 12.4d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).build());
        database.add(new NutrientData.Builder(IRON, Y1, Y3, 7.7d, 7.7d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).build());
        database.add(new NutrientData.Builder(IRON, Y3, Y6, 8.4d, 8.4d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).build());
        database.add(new NutrientData.Builder(IRON, Y6, Y9, 11.9d, 11.9d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).build());
        database.add(new NutrientData.Builder(IRON, Y9, Y15, 19.5d, 19.5d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(MALE).build());
        database.add(new NutrientData.Builder(IRON, Y15, Y18, 25.1d, 25.1d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(MALE).build());
        database.add(new NutrientData.Builder(IRON, Y18, MAX, 18.3d, 18.3d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(MALE).build());
        database.add(new NutrientData.Builder(IRON, Y9, Y15, 43.6d, 43.6d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).menstruating(true).build());
        database.add(new NutrientData.Builder(IRON, Y9, Y15, 18.7d, 18.7d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).build());
        database.add(new NutrientData.Builder(IRON, Y15, Y18, 41.3d, 41.3d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(IRON, Y18, Y50, 39.2d, 39.2d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(IRON, Y50, MAX, 15.1d, 15.1d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(IRON, null, null, 20d, 20d, MILLI_GRAM, EXTRA).bioAvailability(INTERMEDIATE).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(IRON, ZERO, M6, 0.93d, 0.93d, MILLI_GRAM, BASIC).bioAvailability(HIGH).build());
        database.add(new NutrientData.Builder(IRON, M6, M12, 9.3d, 9.3d, MILLI_GRAM, BASIC).bioAvailability(HIGH).build());
        database.add(new NutrientData.Builder(IRON, Y1, Y3, 5.8d, 5.8d, MILLI_GRAM, BASIC).bioAvailability(HIGH).build());
        database.add(new NutrientData.Builder(IRON, Y3, Y6, 6.3d, 6.3d, MILLI_GRAM, BASIC).bioAvailability(HIGH).build());
        database.add(new NutrientData.Builder(IRON, Y6, Y9, 8.9d, 8.9d, MILLI_GRAM, BASIC).bioAvailability(HIGH).build());
        database.add(new NutrientData.Builder(IRON, Y9, Y15, 14.6d, 14.6d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(MALE).build());
        database.add(new NutrientData.Builder(IRON, Y15, Y18, 18.8d, 18.8d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(MALE).build());
        database.add(new NutrientData.Builder(IRON, Y18, MAX, 13.7d, 13.7d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(MALE).build());
        database.add(new NutrientData.Builder(IRON, Y9, Y15, 32.7d, 32.7d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).menstruating(true).build());
        database.add(new NutrientData.Builder(IRON, Y9, Y15, 14d, 14d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).build());
        database.add(new NutrientData.Builder(IRON, Y15, Y18, 31d, 31d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(IRON, Y18, Y50, 29.4d, 29.4d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(IRON, Y50, MAX, 11.3d, 11.3d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(IRON, null, null, 15d, 15d, MILLI_GRAM, EXTRA).bioAvailability(HIGH).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(IRON, ZERO, M6, 0.93d, 0.93d, MILLI_GRAM, BASIC).bioAvailability(LOW).build());
        database.add(new NutrientData.Builder(IRON, M6, M12, 18.6d, 18.6d, MILLI_GRAM, BASIC).bioAvailability(LOW).build());
        database.add(new NutrientData.Builder(IRON, Y1, Y3, 11.6d, 11.6d, MILLI_GRAM, BASIC).bioAvailability(LOW).build());
        database.add(new NutrientData.Builder(IRON, Y3, Y6, 12.6d, 12.6d, MILLI_GRAM, BASIC).bioAvailability(LOW).build());
        database.add(new NutrientData.Builder(IRON, Y6, Y9, 17.8d, 17.8d, MILLI_GRAM, BASIC).bioAvailability(LOW).build());
        database.add(new NutrientData.Builder(IRON, Y9, Y15, 29.2d, 29.2d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(MALE).build());
        database.add(new NutrientData.Builder(IRON, Y15, Y18, 37.6d, 37.6d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(MALE).build());
        database.add(new NutrientData.Builder(IRON, Y18, MAX, 27.4d, 27.4d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(MALE).build());
        database.add(new NutrientData.Builder(IRON, Y9, Y15, 65.4d, 65.4d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).menstruating(true).build());
        database.add(new NutrientData.Builder(IRON, Y9, Y15, 28d, 28d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).build());
        database.add(new NutrientData.Builder(IRON, Y15, Y18, 62d, 62d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(IRON, Y18, Y50, 58.8d, 58.8d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(IRON, Y50, MAX, 22.6d, 22.6d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(IRON, null, null, 30d, 30d, MILLI_GRAM, EXTRA).bioAvailability(LOW).gender(FEMALE).pregnancy(1, 9).build());
        /* The order is importance */
        database.add(new NutrientData.Builder(ZINC, ZERO, M6, 2.8d, 2.8d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).build());
        database.add(new NutrientData.Builder(ZINC, M6, M12, 4.1d, 4.1d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).build());
        database.add(new NutrientData.Builder(ZINC, Y1, Y3, 4.1d, 4.1d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).build());
        database.add(new NutrientData.Builder(ZINC, Y3, Y6, 5.1d, 5.1d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).build());
        database.add(new NutrientData.Builder(ZINC, Y6, Y9, 5.6d, 5.6d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).build());
        database.add(new NutrientData.Builder(ZINC, Y9, Y18, 9.7d, 9.7d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(MALE).build());
        database.add(new NutrientData.Builder(ZINC, Y18, Y60, 7d, 7d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(MALE).build());
        database.add(new NutrientData.Builder(ZINC, Y60, MAX, 4.9d, 4.9d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(MALE).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 5.5d, 5.5d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).pregnancy(1, 3).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 7d, 7d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).pregnancy(4, 6).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 10d, 10d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).pregnancy(7, 9).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 9.5d, 9.5d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).lactation(1, 3).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 8.8d, 8.8d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).lactation(4, 6).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 7.2d, 7.2d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).lactation(7, 12).build());
        database.add(new NutrientData.Builder(ZINC, Y9, Y18, 7.8d, 7.8d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).build());
        database.add(new NutrientData.Builder(ZINC, Y18, Y50, 4.9d, 4.9d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(ZINC, Y50, Y60, 4.9d, 4.9d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(ZINC, Y60, MAX, 7d, 7d, MILLI_GRAM, BASIC).bioAvailability(INTERMEDIATE).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(ZINC, ZERO, M6, 1.1d, 1.1d, MILLI_GRAM, BASIC).bioAvailability(HIGH).build());
        database.add(new NutrientData.Builder(ZINC, M6, M12, 2.5d, 2.5d, MILLI_GRAM, BASIC).bioAvailability(HIGH).build());
        database.add(new NutrientData.Builder(ZINC, Y1, Y3, 2.4d, 2.4d, MILLI_GRAM, BASIC).bioAvailability(HIGH).build());
        database.add(new NutrientData.Builder(ZINC, Y3, Y6, 3.1d, 3.1d, MILLI_GRAM, BASIC).bioAvailability(HIGH).build());
        database.add(new NutrientData.Builder(ZINC, Y6, Y9, 3.3d, 3.3d, MILLI_GRAM, BASIC).bioAvailability(HIGH).build());
        database.add(new NutrientData.Builder(ZINC, Y9, Y18, 5.7d, 5.7d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(MALE).build());
        database.add(new NutrientData.Builder(ZINC, Y18, Y60, 4.2d, 4.2d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(MALE).build());
        database.add(new NutrientData.Builder(ZINC, Y60, MAX, 3d, 3d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(MALE).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 3.4d, 3.4d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).pregnancy(1, 3).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 4.2d, 4.2d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).pregnancy(4, 6).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 6d, 6d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).pregnancy(7, 9).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 5.8d, 5.8d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).lactation(1, 3).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 5.3d, 5.3d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).lactation(4, 6).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 4.3d, 4.3d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).lactation(7, 12).build());
        database.add(new NutrientData.Builder(ZINC, Y9, Y18, 4.6d, 4.6d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).build());
        database.add(new NutrientData.Builder(ZINC, Y18, Y50, 3d, 3d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(ZINC, Y50, Y60, 3d, 3d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(ZINC, Y60, MAX, 4.2d, 4.2d, MILLI_GRAM, BASIC).bioAvailability(HIGH).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(ZINC, ZERO, M6, 6.6d, 6.6d, MILLI_GRAM, BASIC).bioAvailability(LOW).build());
        database.add(new NutrientData.Builder(ZINC, M6, M12, 8.3d, 8.3d, MILLI_GRAM, BASIC).bioAvailability(LOW).build());
        database.add(new NutrientData.Builder(ZINC, Y1, Y3, 8.4d, 8.4d, MILLI_GRAM, BASIC).bioAvailability(LOW).build());
        database.add(new NutrientData.Builder(ZINC, Y3, Y6, 10.3d, 10.3d, MILLI_GRAM, BASIC).bioAvailability(LOW).build());
        database.add(new NutrientData.Builder(ZINC, Y6, Y9, 11.3d, 11.3d, MILLI_GRAM, BASIC).bioAvailability(LOW).build());
        database.add(new NutrientData.Builder(ZINC, Y9, Y18, 19.2d, 19.2d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(MALE).build());
        database.add(new NutrientData.Builder(ZINC, Y18, Y60, 14d, 14d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(MALE).build());
        database.add(new NutrientData.Builder(ZINC, Y60, MAX, 9.8d, 9.8d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(MALE).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 11d, 11d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).pregnancy(1, 3).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 14d, 14d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).pregnancy(4, 6).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 20d, 20d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).pregnancy(7, 9).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 19d, 19d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).lactation(1, 3).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 17.5d, 17.5d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).lactation(4, 6).build());
        database.add(new NutrientData.Builder(ZINC, null, null, 14.4d, 14.4d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).lactation(7, 12).build());
        database.add(new NutrientData.Builder(ZINC, Y9, Y18, 15.5d, 15.5d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).build());
        database.add(new NutrientData.Builder(ZINC, Y18, Y50, 9.8d, 9.8d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(ZINC, Y50, Y60, 9.8d, 9.8d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(ZINC, Y60, MAX, 14d, 14d, MILLI_GRAM, BASIC).bioAvailability(LOW).gender(FEMALE).notPregnantAndLactate().build());

        database.add(new NutrientData.Builder(SELENIUM, ZERO, M6, 0.85d, 0.85d, MICRO_GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(SELENIUM, M6, M12, 0.91d, 0.91d, MICRO_GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(SELENIUM, Y1, Y3, 1.13d, 1.13d, MICRO_GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(SELENIUM, Y3, Y6, 0.92d, 0.92d, MICRO_GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(SELENIUM, Y6, Y9, 0.68d, 0.68d, MICRO_GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(SELENIUM, Y9, Y18, 0.5d, 0.5d, MICRO_GRAM_PER_KILO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(SELENIUM, Y18, Y60, 0.42d, 0.42d, MICRO_GRAM_PER_KILO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(SELENIUM, Y60, MAX, 0.41d, 0.41d, MICRO_GRAM_PER_KILO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(SELENIUM, Y9, Y18, 0.42d, 0.42d, MICRO_GRAM_PER_KILO_GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(SELENIUM, null, null, 26d, 26d, MICRO_GRAM, BASIC).gender(FEMALE).pregnancy(1, 3).build());
        database.add(new NutrientData.Builder(SELENIUM, null, null, 28d, 28d, MICRO_GRAM, BASIC).gender(FEMALE).pregnancy(4, 6).build());
        database.add(new NutrientData.Builder(SELENIUM, null, null, 30d, 30d, MICRO_GRAM, BASIC).gender(FEMALE).pregnancy(7, 9).build());
        database.add(new NutrientData.Builder(SELENIUM, null, null, 35d, 35d, MICRO_GRAM, BASIC).gender(FEMALE).lactation(1, 6).build());
        database.add(new NutrientData.Builder(SELENIUM, null, null, 42d, 42d, MICRO_GRAM, BASIC).gender(FEMALE).lactation(7, 12).build());
        database.add(new NutrientData.Builder(SELENIUM, Y18, Y60, 0.37d, 0.37d, MICRO_GRAM_PER_KILO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(SELENIUM, Y60, MAX, 0.37d, 0.37d, MICRO_GRAM_PER_KILO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(SELENIUM, null, null, 1.25d, 1.25d, DOUBLE_SD, FACTOR).build());
        // Huyen's requirement selen * 2
        database.add(new NutrientData.Builder(SELENIUM, null, null, 2d, 2d, DOUBLE_SD, FACTOR).build());
    }
}
