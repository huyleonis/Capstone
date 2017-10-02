package com.is0967.menutri.rda.calculator;

import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.BodyData.Age;
import com.is0967.menutri.rda.data.MeasurementUnit;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.data.RdaResult;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;

/**
 * Created by phuctran93 on 3/31/2017.
 */
public class BabyEnergyCalculator extends BaseNutrientRdaCalculator
{

    private static final int[] energyCostOfGrowthBoy = {211, 183, 139, 53, 45, 36, 17, 16, 14, 21, 21, 22};
    private static final int[] energyCostOfGrowtGirl = {178, 161, 134, 68, 57, 47, 20, 17, 15, 18, 15, 14};

    public BabyEnergyCalculator(NutrientDatabase database)
    {
        super(database);
    }

    @Override
    protected RdaResult calculate(BodyData bodyData, NutrientType nutrientType, RdaResult rootNutrient)
    {
        if (bodyData.isFrom(Age.Y18)) throw new IllegalArgumentException("BodyData is older than 18.");

        double weight = bodyData.getWeight();
        double ecg = (bodyData.isMale()
                ? energyCostOfGrowthBoy[bodyData.getAge().getMonths()]
                : energyCostOfGrowtGirl[bodyData.getAge().getMonths()]);
        // Total Energy Expenditure (kcal/day)
        double tee = -99.4 + 88.6 * weight;
        double rda = Math.round(tee + ecg);
        RdaResult result = new RdaResult(NutrientType.ENERGY, rda, rda, MeasurementUnit.KILO_CALORIE);

        return result;
    }
}
