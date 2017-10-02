package com.is0967.menutri.rda.calculator;

import static com.is0967.menutri.rda.data.MeasurementUnit.UnitType;

import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.MeasurementUnit;
import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.data.RdaResult;
import com.is0967.menutri.rda.data.RdaType;
import com.is0967.menutri.rda.data.converter.Converter;
import com.is0967.menutri.rda.database.filter.NutrientFilter;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;
import java.util.List;

/**
 * Created by phuctran93 on 3/7/2017.
 */
class SimpleNutrientCalculator extends BaseNutrientRdaCalculator
{

    public SimpleNutrientCalculator(NutrientDatabase database)
    {
        super(database);
    }

    @Override
    public RdaResult calculate(BodyData b, NutrientType nutrientType, RdaResult rootNutrient)
    {

        double basicMin = 0;
        double basicMax = 0;
        double extraMin = 0;
        double extraMax = 0;
        double factorMin = 1;
        double factorMax = 1;
        MeasurementUnit rdaUnit = nutrientType.getDefaultMeasurementUnit();

        List<NutrientData> nutrientDataList = database.find(
                new NutrientFilter().bodyData(b).nutrientType(nutrientType)
        );

        for (NutrientData e : nutrientDataList) {
            double tmpMin;
            double tmpMax;
            if (e.getRdaUnit().getUnitType() == UnitType.MASS_UNIT
                    || e.getRdaUnit().getUnitType() == UnitType.VOLUME_UNIT) {
                tmpMin = Converter.get(e).getRdaMin();
                tmpMax = Converter.get(e).getRdaMax();
            } else if (e.getRdaUnit().getUnitType() == UnitType.PER_BODY_WEIGHT) {
                tmpMin = multiply(Converter.get(e).getRdaMin(), Converter.get(b).getWeight());
                tmpMax = multiply(Converter.get(e).getRdaMax(), Converter.get(b).getWeight());
            } else if (e.getRdaUnit().getUnitType() == UnitType.PURE_NUMBER) {
                tmpMin = e.getRdaMin();
                tmpMax = e.getRdaMax();
            } else {
                throw new UnsupportedOperationException();
            }
            if (e.getRdaType() == RdaType.BASIC) {
                basicMin += tmpMin;
                basicMax += tmpMax;
            } else if (e.getRdaType() == RdaType.EXTRA) {
                extraMin += tmpMin;
                extraMax += tmpMax;
            } else if (e.getRdaType() == RdaType.FACTOR) {
                factorMin *= tmpMin;
                factorMax *= tmpMax;
            }
        }

        double rdaMin = multiply(basicMin, factorMin) + extraMin;
        double rdaMax = multiply(basicMax, factorMax) + extraMax;

        /* Convert to default unit */

        rdaMin = rdaUnit.fromBasicUnit(rdaMin);
        rdaMax = rdaUnit.fromBasicUnit(rdaMax);

        RdaResult result = new RdaResult(nutrientType, rdaMin, rdaMax, rdaUnit);

        return result;
    }

}
