package com.is0967.menutri.rda.data.converter;

import com.is0967.menutri.rda.data.MeasurementUnit;
import com.is0967.menutri.rda.data.NutrientData;
import java.util.Objects;

/**
 * Created by phuctran93 on 3/12/2017.
 */
public class NutrientDataConverter
{
    /* to basic unit */

    private NutrientData nutrientData;

    public NutrientDataConverter(NutrientData nutrientData)
    {
        this.nutrientData = nutrientData;
    }

    public Double getRdaMin()
    {
        return nutrientData.getRdaUnit().toBasicUnit(nutrientData.getRdaMin());
    }

    public Double getRdaMax()
    {
        return nutrientData.getRdaUnit().toBasicUnit(nutrientData.getRdaMax());
    }

    public Double getLimitMin()
    {
        return nutrientData.getLimitUnit().toBasicUnit(nutrientData.getLimitMin());
    }

    public Double getLimitMax()
    {
        return nutrientData.getLimitUnit().toBasicUnit(nutrientData.getLimitMax());
    }

    /* to desired unit */

    public Double getRdaMin(MeasurementUnit unit)
    {
        Objects.requireNonNull(unit);
        return unit.fromUnit(nutrientData.getRdaMin(), nutrientData.getRdaUnit());
    }

    public Double getRdaMax(MeasurementUnit unit)
    {
        Objects.requireNonNull(unit);
        return unit.fromUnit(nutrientData.getRdaMax(), nutrientData.getRdaUnit());
    }

    public Double getLimitMin(MeasurementUnit unit)
    {
        Objects.requireNonNull(unit);
        return unit.fromUnit(nutrientData.getLimitMin(), nutrientData.getLimitUnit());
    }

    public Double getLimitMax(MeasurementUnit unit)
    {
        Objects.requireNonNull(unit);
        return unit.fromUnit(nutrientData.getLimitMax(), nutrientData.getLimitUnit());
    }
}
