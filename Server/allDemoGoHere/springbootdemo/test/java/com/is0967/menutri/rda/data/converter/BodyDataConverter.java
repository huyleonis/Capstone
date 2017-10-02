package com.is0967.menutri.rda.data.converter;

import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.MeasurementUnit;
import java.util.Objects;

/**
 * Created by phuctran93 on 3/12/2017.
 */
public class BodyDataConverter
{

    /* convert to basic unit */

    private BodyData bodyData;

    public BodyDataConverter(BodyData bodyData)
    {
        this.bodyData = bodyData;
    }

    public double getHeight()
    {
        return bodyData.getHeightUnit().toBasicUnit(bodyData.getHeight());
    }

    public double getWeight()
    {
        return bodyData.getWeightUnit().toBasicUnit(bodyData.getWeight());
    }

    /* convert to desired unit */

    public double getHeight(MeasurementUnit unit)
    {
        Objects.requireNonNull(unit);
        return unit.fromUnit(bodyData.getHeight(), bodyData.getHeightUnit());
    }

    public double getWeight(MeasurementUnit unit)
    {
        Objects.requireNonNull(unit);
        return unit.fromUnit(bodyData.getWeight(), bodyData.getWeightUnit());
    }
}
