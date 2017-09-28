package com.is0967.menutri.rda.calculator;

import com.is0967.menutri.rda.calculator.spi.NutrientRdaCalculator;
import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.data.RdaResult;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * Created by phuctran93 on 3/27/2017.
 */
public enum NutrientRdaCalculatorService implements NutrientRdaCalculator
{
    INSTANCE;
    private ServiceLoader<NutrientRdaCalculator> loader;

    NutrientRdaCalculatorService()
    {
        loader = ServiceLoader.load(NutrientRdaCalculator.class);
    }

    @Override
    public RdaResult rda(BodyData bodyData, NutrientType nutrientType, RdaResult otherResult)
    {
        RdaResult result = null;
        try {
            Iterator<NutrientRdaCalculator> iterator = loader.iterator();
            while (result == null && iterator.hasNext()) {
                NutrientRdaCalculator calculator = iterator.next();
                result = calculator.rda(bodyData, nutrientType, otherResult);
            }
        } catch (ServiceConfigurationError serviceError) {
            result = null;
            serviceError.printStackTrace();
        }
        return result;
    }
}
