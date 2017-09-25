package com.is0967.menutri.rda.calculator;

import static com.is0967.menutri.rda.data.NutrientType.NUTRIENT;

import com.is0967.menutri.rda.calculator.spi.NutrientRdaCalculator;
import com.is0967.menutri.rda.calculator.spi.RdaCalculator;
import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.data.RdaResult;

/**
 * Created by phuctran93 on 3/10/2017.
 */
public class DefaultRdaCalculator implements RdaCalculator
{

    private final NutrientRdaCalculator calculator;

    public DefaultRdaCalculator()
    {
        this.calculator = new DefaultNutrientRdaCalculator();
    }

    @Override
    public RdaResult rda(BodyData bodyData)
    {
        NutrientType nutrientType = NUTRIENT;
        RdaResult rootNutrient = new RdaResult(nutrientType, null, null, null);
        for (NutrientType n : nutrientType.children()) {
            calculate(bodyData, n, rootNutrient);
        }

        return rootNutrient;
    }

    private void calculate(BodyData bodyData, NutrientType nutrientType, RdaResult rootNutrient)
    {
        calculator.rda(bodyData, nutrientType, rootNutrient);
        for (NutrientType n : nutrientType.children()) {
            calculate(bodyData, n, rootNutrient);
        }
    }
}
