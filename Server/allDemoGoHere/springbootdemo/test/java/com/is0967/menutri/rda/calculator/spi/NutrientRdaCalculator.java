package com.is0967.menutri.rda.calculator.spi;

import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.data.RdaResult;

/**
 * Created by phuctran93 on 3/11/2017.
 */
public interface NutrientRdaCalculator
{

    /**
     * Calculate RDA for a specific nutrient. Some rda depend on other rda, e.g. rda for water is
     * depended on total energy requirement, so that it's is helpful to provide other rda result if
     * any.
     */
    RdaResult rda(BodyData bodyData, NutrientType nutrientType, RdaResult otherResult);
}
