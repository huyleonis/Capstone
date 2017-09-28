package com.is0967.menutri.rda.calculator;

import com.is0967.menutri.rda.calculator.spi.RdaCalculator;
import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.RdaResult;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * Created by phuctran93 on 3/27/2017.
 */
public enum RdaCalculatorService implements RdaCalculator
{
    INSTANCE;

    private ServiceLoader<RdaCalculator> loader;

    RdaCalculatorService()
    {
        this.loader = ServiceLoader.load(RdaCalculator.class);
    }

    @Override
    public RdaResult rda(BodyData bodyData)
    {
        RdaResult result = null;
        try {
            Iterator<RdaCalculator> iterator = loader.iterator();
            while (result == null && iterator.hasNext()) {
                RdaCalculator calculator = iterator.next();
                result = calculator.rda(bodyData);
            }
        } catch (ServiceConfigurationError serviceError) {
            result = null;
            serviceError.printStackTrace();
        }
        return result;
    }
}
