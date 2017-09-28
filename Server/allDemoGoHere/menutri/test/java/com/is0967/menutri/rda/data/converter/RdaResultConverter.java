package com.is0967.menutri.rda.data.converter;

import com.is0967.menutri.rda.data.MeasurementUnit;
import com.is0967.menutri.rda.data.RdaResult;
import java.util.Objects;

/**
 * Created by phuctran93 on 3/27/2017.
 */
public class RdaResultConverter
{
    /* to basic unit */

    private RdaResult rdaResult;

    public RdaResultConverter(RdaResult rdaResult)
    {
        this.rdaResult = rdaResult;
    }

    public Double getRdaMin()
    {
        return rdaResult.getRdaUnit().toBasicUnit(rdaResult.getRdaMin());
    }

    public Double getRdaMax()
    {
        return rdaResult.getRdaUnit().toBasicUnit(rdaResult.getRdaMax());
    }

    /* to desired unit */

    public Double getRdaMin(MeasurementUnit unit)
    {
        Objects.requireNonNull(unit);
        return unit.fromUnit(rdaResult.getRdaMin(), rdaResult.getRdaUnit());
    }

    public Double getRdaMax(MeasurementUnit unit)
    {
        Objects.requireNonNull(unit);
        return unit.fromUnit(rdaResult.getRdaMax(), rdaResult.getRdaUnit());
    }
}
