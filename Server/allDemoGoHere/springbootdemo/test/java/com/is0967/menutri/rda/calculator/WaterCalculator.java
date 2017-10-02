package com.is0967.menutri.rda.calculator;

import static com.is0967.menutri.rda.data.MeasurementUnit.KILO_CALORIE;
import static com.is0967.menutri.rda.data.MeasurementUnit.UnitType;

import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.MeasurementUnit;
import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.data.RdaResult;
import com.is0967.menutri.rda.data.converter.Converter;
import com.is0967.menutri.rda.database.filter.NutrientFilter;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;
import java.util.List;

/**
 * Created by phuctran93 on 3/7/2017.
 */
class WaterCalculator extends BaseNutrientRdaCalculator
{

    public WaterCalculator(NutrientDatabase database)
    {
        super(database);
    }

    @Override
    public RdaResult calculate(BodyData bodyData, NutrientType nutrientType, RdaResult rootNutrient)
    {

        double rdaMin = 0;
        double rdaMax = 0;
        MeasurementUnit rdaUnit = nutrientType.getDefaultMeasurementUnit();

        NutrientData basicData = database.findFirstBasic(
                new NutrientFilter().bodyData(bodyData).nutrientType(nutrientType)
        );

        List<NutrientData> nutrientDataList = database.findExtra(
                new NutrientFilter().bodyData(bodyData).nutrientType(nutrientType)
        );

        if (basicData != null) {
            nutrientDataList.add(basicData);
        }

        for (NutrientData ex : nutrientDataList) {
            if (ex.getRdaUnit().getUnitType() == UnitType.PER_KILO_CALORIE) {
                RdaResult en = rootNutrient.getSubResult(NutrientType.ENERGY);
                if (en != null) {
                    rdaMin += multiply(Converter.get(ex).getRdaMin(), Converter.get(en).getRdaMin(KILO_CALORIE));
                    rdaMax += multiply(Converter.get(ex).getRdaMax(), Converter.get(en).getRdaMax(KILO_CALORIE));
                }
            } else {
                rdaMin += Converter.get(ex).getRdaMin();
                rdaMax += Converter.get(ex).getRdaMax();
            }
        }

        /* Convert to default unit */

        rdaMin = rdaUnit.fromBasicUnit(rdaMin);
        rdaMax = rdaUnit.fromBasicUnit(rdaMax);

        RdaResult result = new RdaResult(nutrientType, rdaMin, rdaMax, rdaUnit);

        return result;
    }

}
