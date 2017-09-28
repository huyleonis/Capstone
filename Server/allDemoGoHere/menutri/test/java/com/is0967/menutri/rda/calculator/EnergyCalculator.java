package com.is0967.menutri.rda.calculator;

import static com.is0967.menutri.rda.data.MeasurementUnit.UnitType;
import static com.is0967.menutri.rda.data.NutrientType.ENERGY;

import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.MeasurementUnit;
import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.data.RdaResult;
import com.is0967.menutri.rda.data.ReeEquationCoefficient;
import com.is0967.menutri.rda.data.converter.Converter;
import com.is0967.menutri.rda.database.filter.NutrientFilter;
import com.is0967.menutri.rda.database.filter.ReeEquationFilter;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;
import com.is0967.menutri.rda.database.spi.ReeEquationDatabase;
import java.util.List;

/**
 * Created by phuctran93 on 3/7/2017.
 */
class EnergyCalculator extends BaseNutrientRdaCalculator
{

    private final ReeEquationDatabase reeEquationDatabase;

    public EnergyCalculator(NutrientDatabase database, ReeEquationDatabase reeEquationDatabase)
    {
        super(database);
        this.reeEquationDatabase = reeEquationDatabase;
    }

    @Override
    public RdaResult calculate(BodyData bodyData, NutrientType nutrientType, RdaResult rootNutrient)
    {
        if (nutrientType != ENERGY) { throw new IllegalArgumentException("require nutrientType: ENERGY"); }

        ReeEquationCoefficient reeEquation = reeEquationDatabase.getCoefficient(ReeEquationFilter.byBodyData(bodyData));
        double ree = calculateRestingEnergyExpenditure(reeEquation, bodyData);

        double rdaMin = 0;
        double rdaMax = 0;
        double factorMin = 1;
        double factorMax = 1;
        MeasurementUnit rdaUnit = nutrientType.getDefaultMeasurementUnit();
        List<NutrientData> nutrientDataList = database.find(
                new NutrientFilter().bodyData(bodyData).nutrientType(nutrientType)
        );
        for (NutrientData n : nutrientDataList) {
            if (n.getRdaUnit().getUnitType() == UnitType.ENERGY_UNIT) {
                /* Energy Unit: calorie, kilo-calorie, need to convert to basic unit */
                rdaMin += Converter.get(n).getRdaMin();
                rdaMax += Converter.get(n).getRdaMax();
            } else if (n.getRdaUnit().getUnitType() == UnitType.PURE_NUMBER) {
                factorMin = Converter.get(n).getRdaMin();
                factorMax = Converter.get(n).getRdaMax();
            }
        }

        rdaMin = (ree * factorMin) + rdaMin;
        rdaMax = (ree * factorMax) + rdaMax;

        /* Convert to default unit */

        rdaMin = rdaUnit.fromBasicUnit(rdaMin);
        rdaMax = rdaUnit.fromBasicUnit(rdaMax);

        RdaResult result = new RdaResult(nutrientType, rdaMin, rdaMax, rdaUnit);
        return result;
    }

    private double calculateRestingEnergyExpenditure(ReeEquationCoefficient reeEquation, BodyData bodyData)
    {
        double kcalValue = reeEquation.getCoefficient() * bodyData.getWeight() + reeEquation.getConstantTerm();
        double calValue = kcalValue * 1000;
        return calValue;
    }
}
