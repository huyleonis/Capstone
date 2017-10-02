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
public class ChildrenAdolescentEnergyCalculator extends BaseNutrientRdaCalculator
{

    private static final int[] boyEcg = {14, 11, 12, 11, 11, 12, 14, 16, 19, 22, 25, 29, 33, 33, 30, 24, 15};
    private static final int[] girlEcg = {14, 12, 11, 10, 10, 13, 17, 21, 23, 25, 25, 26, 24, 19, 12, 5, 0};

    public ChildrenAdolescentEnergyCalculator(NutrientDatabase database)
    {
        super(database);
    }

    @Override
    protected RdaResult calculate(BodyData bodyData, NutrientType nutrientType, RdaResult rootNutrient)
    {
        if (bodyData.isFrom(Age.Y18)) throw new IllegalArgumentException("BodyData is older than 18.");

        double weight = bodyData.getWeight();
        double weight2 = Math.pow(weight, 2);
        // Total Energy Expenditure (kcal/day)
        double tee = bodyData.isMale()
                ? 310.2 + 63.3 * weight - 0.263 * weight2
                : 263.4 + 65.3 * weight - 0.454 * weight2;
        int age = bodyData.getAge().getYears();
        double ecg = bodyData.isMale()
                ? boyEcg[age - 1]
                : girlEcg[age - 1];
//        double rda = Age.lessThan(bodyData.getAge(), Age.Y2)
//                ? tee - (0.07 * tee)
//                : tee;
        double rda = Math.round(tee + ecg);
        RdaResult result = new RdaResult(NutrientType.ENERGY, rda, rda, MeasurementUnit.KILO_CALORIE);

        return result;
    }

}
