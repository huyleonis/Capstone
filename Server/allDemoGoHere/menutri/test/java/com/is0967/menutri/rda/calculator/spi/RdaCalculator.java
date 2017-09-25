package com.is0967.menutri.rda.calculator.spi;

import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.RdaResult;

/**
 * Created by phuctran93 on 3/1/2017.
 */

public interface RdaCalculator
{

    /**
     * Calculate rda of all nutrients for a specific body.
     */
    RdaResult rda(BodyData bodyData);
}
