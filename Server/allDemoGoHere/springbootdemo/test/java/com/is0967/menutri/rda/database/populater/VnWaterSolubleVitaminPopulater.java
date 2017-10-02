package com.is0967.menutri.rda.database.populater;

import static com.is0967.menutri.rda.data.ActivityLevel.HEAVY;
import static com.is0967.menutri.rda.data.ActivityLevel.LIGHT;
import static com.is0967.menutri.rda.data.ActivityLevel.MODERATE;
import static com.is0967.menutri.rda.data.BodyData.Age.M12;
import static com.is0967.menutri.rda.data.BodyData.Age.M6;
import static com.is0967.menutri.rda.data.BodyData.Age.MAX;
import static com.is0967.menutri.rda.data.BodyData.Age.Y1;
import static com.is0967.menutri.rda.data.BodyData.Age.Y12;
import static com.is0967.menutri.rda.data.BodyData.Age.Y15;
import static com.is0967.menutri.rda.data.BodyData.Age.Y18;
import static com.is0967.menutri.rda.data.BodyData.Age.Y3;
import static com.is0967.menutri.rda.data.BodyData.Age.Y30;
import static com.is0967.menutri.rda.data.BodyData.Age.Y50;
import static com.is0967.menutri.rda.data.BodyData.Age.Y6;
import static com.is0967.menutri.rda.data.BodyData.Age.Y60;
import static com.is0967.menutri.rda.data.BodyData.Age.Y9;
import static com.is0967.menutri.rda.data.BodyData.Age.ZERO;
import static com.is0967.menutri.rda.data.Gender.FEMALE;
import static com.is0967.menutri.rda.data.Gender.MALE;
import static com.is0967.menutri.rda.data.MeasurementUnit.DOUBLE_SD;
import static com.is0967.menutri.rda.data.MeasurementUnit.MICRO_GRAM;
import static com.is0967.menutri.rda.data.MeasurementUnit.MILLI_GRAM;
import static com.is0967.menutri.rda.data.NutrientType.FOLATE;
import static com.is0967.menutri.rda.data.NutrientType.NIACIN;
import static com.is0967.menutri.rda.data.NutrientType.RIBOFLAVIN;
import static com.is0967.menutri.rda.data.NutrientType.THIAMIN;
import static com.is0967.menutri.rda.data.NutrientType.VITAMIN_B12;
import static com.is0967.menutri.rda.data.NutrientType.VITAMIN_B6;
import static com.is0967.menutri.rda.data.NutrientType.VITAMIN_C;
import static com.is0967.menutri.rda.data.RdaType.BASIC;
import static com.is0967.menutri.rda.data.RdaType.EXTRA;
import static com.is0967.menutri.rda.data.RdaType.FACTOR;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.database.populater.spi.Populater;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;

/**
 * Created by phuctran93 on 3/10/2017.
 */
class VnWaterSolubleVitaminPopulater implements Populater
{

    @Override
    public void populate(NutrientDatabase database)
    {
        /* The order is matter */
        database.add(new NutrientData.Builder(VITAMIN_C, ZERO, M6, 25d, 25d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_C, M6, M12, 30d, 30d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_C, Y1, Y3, 30d, 30d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_C, Y3, Y6, 30d, 30d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_C, Y6, Y9, 35d, 35d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_C, Y9, Y18, 65d, 65d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_C, Y18, Y60, 70d, 70d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_C, Y60, MAX, 70d, 70d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_C, null, null, 80d, 80d, MILLI_GRAM, BASIC).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(VITAMIN_C, null, null, 95d, 95d, MILLI_GRAM, BASIC).gender(FEMALE).lactation(1, 12).build());
        database.add(new NutrientData.Builder(VITAMIN_C, Y9, Y18, 65d, 65d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_C, Y18, Y60, 70d, 70d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_C, Y60, MAX, 70d, 70d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        // Huyen's requirement vitamin c * 3
        database.add(new NutrientData.Builder(VITAMIN_C, null, null, 3d, 3d, DOUBLE_SD, FACTOR).build());

        database.add(new NutrientData.Builder(THIAMIN, ZERO, M6, 0.2d, 0.2d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(THIAMIN, M6, M12, 0.3d, 0.3d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(THIAMIN, Y1, Y3, 0.5d, 0.5d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(THIAMIN, Y3, Y6, 0.6d, 0.6d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(THIAMIN, Y6, Y9, 0.9d, 0.9d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(THIAMIN, Y9, Y18, 1.2d, 1.2d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(THIAMIN, Y9, Y18, 1.1d, 1.1d, MILLI_GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(THIAMIN, Y18, Y30, 1.15d, 1.15d, MILLI_GRAM, BASIC).gender(MALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(THIAMIN, Y30, Y60, 1.1d, 1.1d, MILLI_GRAM, BASIC).gender(MALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(THIAMIN, Y60, MAX, 0.95d, 0.95d, MILLI_GRAM, BASIC).gender(MALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(THIAMIN, Y18, Y30, 1.35d, 1.35d, MILLI_GRAM, BASIC).gender(MALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(THIAMIN, Y30, Y60, 1.35d, 1.35d, MILLI_GRAM, BASIC).gender(MALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(THIAMIN, Y60, MAX, 1.1d, 1.1d, MILLI_GRAM, BASIC).gender(MALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(THIAMIN, Y18, Y30, 1.65d, 1.65d, MILLI_GRAM, BASIC).gender(MALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(THIAMIN, Y30, Y60, 1.6d, 1.6d, MILLI_GRAM, BASIC).gender(MALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(THIAMIN, Y60, MAX, 1.35d, 1.35d, MILLI_GRAM, BASIC).gender(MALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(THIAMIN, Y18, Y30, 1.1d, 1.1d, MILLI_GRAM, BASIC).gender(FEMALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(THIAMIN, Y30, Y60, 1.05d, 1.05d, MILLI_GRAM, BASIC).gender(FEMALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(THIAMIN, Y60, MAX, 0.9d, 0.9d, MILLI_GRAM, BASIC).gender(FEMALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(THIAMIN, Y18, Y30, 1.15d, 1.15d, MILLI_GRAM, BASIC).gender(FEMALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(THIAMIN, Y30, Y60, 1.1d, 1.1d, MILLI_GRAM, BASIC).gender(FEMALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(THIAMIN, Y60, MAX, 0.95d, 0.95d, MILLI_GRAM, BASIC).gender(FEMALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(THIAMIN, Y18, Y30, 1.3d, 1.3d, MILLI_GRAM, BASIC).gender(FEMALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(THIAMIN, Y30, Y60, 1.25d, 1.25d, MILLI_GRAM, BASIC).gender(FEMALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(THIAMIN, Y60, MAX, 1.1d, 1.1d, MILLI_GRAM, BASIC).gender(FEMALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(THIAMIN, null, null, 0.18d, 0.18d, MILLI_GRAM, EXTRA).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(THIAMIN, null, null, 0.28d, 0.28d, MILLI_GRAM, EXTRA).gender(FEMALE).lactation(1, 12).build());

        database.add(new NutrientData.Builder(RIBOFLAVIN, ZERO, M6, 0.3d, 0.3d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(RIBOFLAVIN, M6, M12, 0.4d, 0.4d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(RIBOFLAVIN, Y1, Y3, 0.5d, 0.5d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(RIBOFLAVIN, Y3, Y6, 0.6d, 0.6d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(RIBOFLAVIN, Y6, Y9, 0.9d, 0.9d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(RIBOFLAVIN, Y9, Y18, 1.3d, 1.3d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(RIBOFLAVIN, Y18, Y60, 1.3d, 1.3d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(RIBOFLAVIN, Y60, MAX, 1.3d, 1.3d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(RIBOFLAVIN, null, null, 1.4d, 1.4d, MILLI_GRAM, BASIC).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(RIBOFLAVIN, null, null, 1.6d, 1.6d, MILLI_GRAM, BASIC).gender(FEMALE).lactation(1, 12).build());
        database.add(new NutrientData.Builder(RIBOFLAVIN, Y9, Y18, 1d, 1d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(RIBOFLAVIN, Y18, Y60, 1.1d, 1.1d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(RIBOFLAVIN, Y60, MAX, 1.1d, 1.1d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(NIACIN, ZERO, M6, 2d, 2d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(NIACIN, M6, M12, 4d, 4d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(NIACIN, Y1, Y3, 6d, 6d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(NIACIN, Y3, Y6, 8d, 8d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(NIACIN, Y6, Y9, 12d, 12d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(NIACIN, Y9, Y18, 16d, 16d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(NIACIN, Y18, Y60, 16d, 16d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(NIACIN, Y60, MAX, 16d, 16d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(NIACIN, null, null, 18d, 18d, MILLI_GRAM, BASIC).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(NIACIN, null, null, 17d, 17d, MILLI_GRAM, BASIC).gender(FEMALE).lactation(1, 12).build());
        database.add(new NutrientData.Builder(NIACIN, Y9, Y18, 16d, 16d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(NIACIN, Y18, Y60, 14d, 14d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(NIACIN, Y60, MAX, 14d, 14d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_B6, ZERO, M6, 0.1d, 0.1d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, M6, M12, 0.3d, 0.3d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y1, Y3, 0.5d, 0.5d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y3, Y6, 0.6d, 0.6d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y6, Y9, 1d, 1d, MILLI_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y9, Y12, 1.3d, 1.3d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y12, Y15, 1.3d, 1.3d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y15, Y18, 1.3d, 1.3d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y18, Y50, 1.3d, 1.3d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y50, MAX, 1.7d, 1.7d, MILLI_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, null, null, 1.9d, 1.9d, MILLI_GRAM, BASIC).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, null, null, 2d, 2d, MILLI_GRAM, BASIC).gender(FEMALE).lactation(1, 12).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y9, Y12, 1.2d, 1.2d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y12, Y15, 1.2d, 1.2d, MILLI_GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y15, Y18, 1.2d, 1.2d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y18, Y50, 1.3d, 1.3d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_B6, Y50, MAX, 1.5d, 1.5d, MILLI_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(FOLATE, ZERO, M6, 80d, 80d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(FOLATE, M6, M12, 80d, 80d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(FOLATE, Y1, Y3, 160d, 160d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(FOLATE, Y3, Y6, 200d, 200d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(FOLATE, Y6, Y9, 300d, 300d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(FOLATE, Y9, Y18, 400d, 400d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(FOLATE, Y18, Y60, 400d, 400d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(FOLATE, Y60, MAX, 400d, 400d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(FOLATE, null, null, 600d, 600d, MICRO_GRAM, BASIC).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(FOLATE, null, null, 500d, 500d, MICRO_GRAM, BASIC).gender(FEMALE).lactation(1, 12).build());
        database.add(new NutrientData.Builder(FOLATE, Y9, Y18, 400d, 400d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(FOLATE, Y18, Y60, 400d, 400d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(FOLATE, Y60, MAX, 400d, 400d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_B12, ZERO, M6, 0.3d, 0.3d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_B12, M6, M12, 0.4d, 0.4d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_B12, Y1, Y3, 0.9d, 0.9d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_B12, Y3, Y6, 1.2d, 1.2d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_B12, Y6, Y9, 1.8d, 1.8d, MICRO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VITAMIN_B12, Y9, Y18, 2.4d, 2.4d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_B12, Y18, MAX, 2.4d, 2.4d, MICRO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(VITAMIN_B12, null, null, 2.6d, 2.6d, MICRO_GRAM, BASIC).gender(FEMALE).pregnancy(1, 9).build());
        database.add(new NutrientData.Builder(VITAMIN_B12, null, null, 2.8d, 2.8d, MICRO_GRAM, BASIC).gender(FEMALE).lactation(1, 12).build());
        database.add(new NutrientData.Builder(VITAMIN_B12, Y9, Y18, 2.4d, 2.4d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
        database.add(new NutrientData.Builder(VITAMIN_B12, Y18, MAX, 2.4d, 2.4d, MICRO_GRAM, BASIC).gender(FEMALE).notPregnantAndLactate().build());
    }
}
