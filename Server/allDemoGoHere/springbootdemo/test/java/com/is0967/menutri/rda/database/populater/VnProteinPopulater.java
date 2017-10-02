package com.is0967.menutri.rda.database.populater;

import static com.is0967.menutri.rda.data.ActivityLevel.HEAVY;
import static com.is0967.menutri.rda.data.ActivityLevel.LIGHT;
import static com.is0967.menutri.rda.data.ActivityLevel.MODERATE;
import static com.is0967.menutri.rda.data.BodyData.Age.M1;
import static com.is0967.menutri.rda.data.BodyData.Age.M12;
import static com.is0967.menutri.rda.data.BodyData.Age.M2;
import static com.is0967.menutri.rda.data.BodyData.Age.M3;
import static com.is0967.menutri.rda.data.BodyData.Age.M4;
import static com.is0967.menutri.rda.data.BodyData.Age.M6;
import static com.is0967.menutri.rda.data.BodyData.Age.MAX;
import static com.is0967.menutri.rda.data.BodyData.Age.Y1;
import static com.is0967.menutri.rda.data.BodyData.Age.Y12;
import static com.is0967.menutri.rda.data.BodyData.Age.Y15;
import static com.is0967.menutri.rda.data.BodyData.Age.Y18;
import static com.is0967.menutri.rda.data.BodyData.Age.Y3;
import static com.is0967.menutri.rda.data.BodyData.Age.Y30;
import static com.is0967.menutri.rda.data.BodyData.Age.Y6;
import static com.is0967.menutri.rda.data.BodyData.Age.Y60;
import static com.is0967.menutri.rda.data.BodyData.Age.Y9;
import static com.is0967.menutri.rda.data.BodyData.Age.ZERO;
import static com.is0967.menutri.rda.data.Gender.FEMALE;
import static com.is0967.menutri.rda.data.Gender.MALE;
import static com.is0967.menutri.rda.data.MeasurementUnit.CALORIE;
import static com.is0967.menutri.rda.data.MeasurementUnit.GRAM;
import static com.is0967.menutri.rda.data.MeasurementUnit.GRAM_PER_KILO_GRAM;
import static com.is0967.menutri.rda.data.NutrientType.CYSTEINE;
import static com.is0967.menutri.rda.data.NutrientType.HISTIDINE;
import static com.is0967.menutri.rda.data.NutrientType.ISOLEUCINE;
import static com.is0967.menutri.rda.data.NutrientType.LEUCINE;
import static com.is0967.menutri.rda.data.NutrientType.LYSINE;
import static com.is0967.menutri.rda.data.NutrientType.METHIONINE;
import static com.is0967.menutri.rda.data.NutrientType.PROTEIN;
import static com.is0967.menutri.rda.data.NutrientType.THREONINE;
import static com.is0967.menutri.rda.data.NutrientType.TRYPTOPHAN;
import static com.is0967.menutri.rda.data.NutrientType.VALINE;
import static com.is0967.menutri.rda.data.RdaType.BASIC;
import static com.is0967.menutri.rda.data.RdaType.EXTRA;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.database.populater.spi.Populater;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;

/**
 * Created by phuctran93 on 3/1/2017.
 */
class VnProteinPopulater implements Populater
{

    @Override
    public void populate(NutrientDatabase database)
    {
        database.add(new NutrientData.Builder(PROTEIN, ZERO, M1, 2.46d, 2.46d, GRAM_PER_KILO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(PROTEIN, M1, M2, 1.93d, 1.93d, GRAM_PER_KILO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(PROTEIN, M2, M3, 1.74d, 1.74d, GRAM_PER_KILO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(PROTEIN, M3, M4, 1.49d, 1.49d, GRAM_PER_KILO_GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(PROTEIN, M4, M6, 12d, 12d, GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(PROTEIN, M6, M12, 21d, 25d, GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y1, Y3, 35d, 44d, GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y3, Y6, 44d, 55d, GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y6, Y9, 55d, 64d, GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y9, Y12, 63d, 74d, GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y12, Y15, 80d, 93d, GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y15, Y18, 89d, 104d, GRAM, BASIC).gender(MALE).build());
        database.add(new NutrientData.Builder(PROTEIN, ZERO, M1, 2.39d, 2.39d, GRAM_PER_KILO_GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(PROTEIN, M1, M2, 1.93d, 1.93d, GRAM_PER_KILO_GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(PROTEIN, M2, M3, 1.78d, 1.78d, GRAM_PER_KILO_GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(PROTEIN, M3, M4, 1.53d, 1.53d, GRAM_PER_KILO_GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(PROTEIN, M4, M6, 12d, 12d, GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(PROTEIN, M6, M12, 21d, 25d, GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y1, Y3, 35d, 44d, GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y3, Y6, 44d, 55d, GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y6, Y9, 55d, 64d, GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y9, Y12, 60d, 70d, GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y12, Y15, 66d, 77d, GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y15, Y18, 67d, 78d, GRAM, BASIC).gender(FEMALE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y18, Y30, 69d, 80d, CALORIE, BASIC).gender(MALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(PROTEIN, Y30, Y60, 66d, 77d, CALORIE, BASIC).gender(MALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(PROTEIN, Y60, MAX, 57d, 66d, CALORIE, BASIC).gender(MALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(PROTEIN, Y18, Y30, 81d, 94d, CALORIE, BASIC).gender(MALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y30, Y60, 81d, 94d, CALORIE, BASIC).gender(MALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y60, MAX, 66d, 77d, CALORIE, BASIC).gender(MALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y18, Y30, 96d, 112d, CALORIE, BASIC).gender(MALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(PROTEIN, Y30, Y60, 96d, 112d, CALORIE, BASIC).gender(MALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(PROTEIN, Y60, MAX, 81d, 94d, CALORIE, BASIC).gender(MALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(PROTEIN, Y18, Y30, 66d, 77d, CALORIE, BASIC).gender(FEMALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(PROTEIN, Y30, Y60, 63d, 73d, CALORIE, BASIC).gender(FEMALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(PROTEIN, Y60, MAX, 54d, 63d, CALORIE, BASIC).gender(FEMALE).activityLevel(LIGHT).build());
        database.add(new NutrientData.Builder(PROTEIN, Y18, Y30, 69d, 80d, CALORIE, BASIC).gender(FEMALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y30, Y60, 66d, 77d, CALORIE, BASIC).gender(FEMALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y60, MAX, 57d, 66d, CALORIE, BASIC).gender(FEMALE).activityLevel(MODERATE).build());
        database.add(new NutrientData.Builder(PROTEIN, Y18, Y30, 78d, 91d, CALORIE, BASIC).gender(FEMALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(PROTEIN, Y30, Y60, 75d, 87d, CALORIE, BASIC).gender(FEMALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(PROTEIN, Y60, MAX, 66d, 77d, CALORIE, BASIC).gender(FEMALE).activityLevel(HEAVY).build());
        database.add(new NutrientData.Builder(PROTEIN, null, null, 10d, 15d, GRAM, EXTRA).gender(FEMALE).pregnancy(1, 6).build());
        database.add(new NutrientData.Builder(PROTEIN, null, null, 12d, 18d, GRAM, EXTRA).gender(FEMALE).pregnancy(7, 9).build());
        database.add(new NutrientData.Builder(PROTEIN, null, null, 20d, 25d, GRAM, EXTRA).gender(FEMALE).lactation(1, 6).build());
        database.add(new NutrientData.Builder(PROTEIN, null, null, 16d, 19d, GRAM, EXTRA).gender(FEMALE).lactation(7, 12).build());
        // amino acid
        database.add(new NutrientData.Builder(HISTIDINE, Y18, MAX, 10d, 10d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(ISOLEUCINE, Y18, MAX, 20d, 20d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(LEUCINE, Y18, MAX, 39d, 39d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(LYSINE, Y18, MAX, 30d, 30d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(METHIONINE, Y18, MAX, 10.4d, 10.4d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CYSTEINE, Y18, MAX, 5d, 5d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(THREONINE, Y18, MAX, 15d, 15d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(TRYPTOPHAN, Y18, MAX, 4d, 4d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VALINE, Y18, MAX, 26d, 26d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(HISTIDINE, Y3, Y18, 11d, 12d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(ISOLEUCINE, Y3, Y18, 22d, 24d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(LEUCINE, Y3, Y18, 42.9d, 46.8d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(LYSINE, Y3, Y18, 33d, 36d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(METHIONINE, Y3, Y18, 11.44d, 12.48d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CYSTEINE, Y3, Y18, 5.5d, 6d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(THREONINE, Y3, Y18, 16.5d, 18d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(TRYPTOPHAN, Y3, Y18, 4.4d, 4.8d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VALINE, Y3, Y18, 28.6d, 31.2d, GRAM_PER_KILO_GRAM, BASIC).build());

        database.add(new NutrientData.Builder(HISTIDINE, Y1, Y3, 12d, 20d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(ISOLEUCINE, Y1, Y3, 24d, 40d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(LEUCINE, Y1, Y3, 46.8d, 78d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(LYSINE, Y1, Y3, 36d, 60d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(METHIONINE, Y1, Y3, 12.48d, 20.8d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CYSTEINE, Y1, Y3, 6d, 10d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(THREONINE, Y1, Y3, 18d, 30d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(TRYPTOPHAN, Y1, Y3, 4.8d, 8d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VALINE, Y1, Y3, 31.2d, 52d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(HISTIDINE, ZERO, Y1, 20d, 25d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(ISOLEUCINE, ZERO, Y1, 40d, 50d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(LEUCINE, ZERO, Y1, 78d, 97.5d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(LYSINE, ZERO, Y1, 60d, 75d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(METHIONINE, ZERO, Y1, 20.8d, 26d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(CYSTEINE, ZERO, Y1, 10d, 12.5d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(THREONINE, ZERO, Y1, 30d, 37.5d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(TRYPTOPHAN, ZERO, Y1, 8d, 10d, GRAM_PER_KILO_GRAM, BASIC).build());
        database.add(new NutrientData.Builder(VALINE, ZERO, Y1, 52d, 65d, GRAM_PER_KILO_GRAM, BASIC).build());
    }
}
