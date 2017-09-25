package com.is0967.menutri.rda.calculator;

import static com.is0967.menutri.rda.data.MeasurementUnit.GRAM;
import static com.is0967.menutri.rda.data.MeasurementUnit.GRAM_PER_KILO_CALORIE;
import static com.is0967.menutri.rda.data.MeasurementUnit.GRAM_PER_KILO_GRAM;
import static com.is0967.menutri.rda.data.MeasurementUnit.KILO_CALORIE;
import static com.is0967.menutri.rda.data.MeasurementUnit.KILO_GRAM;
import static com.is0967.menutri.rda.data.MeasurementUnit.PERCENT_OF_TOTAL_ENERGY;
import static com.is0967.menutri.rda.data.MeasurementUnit.UnitType;
import static com.is0967.menutri.rda.data.NutrientType.CARBOHYDRATE_AND_FIBER;
import static com.is0967.menutri.rda.data.NutrientType.ENERGY;
import static com.is0967.menutri.rda.data.NutrientType.FAT;
import static com.is0967.menutri.rda.data.NutrientType.PROTEIN;

import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.MeasurementUnit;
import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.data.RdaResult;
import com.is0967.menutri.rda.data.converter.Converter;
import com.is0967.menutri.rda.database.filter.NutrientFilter;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by phuctran93 on 3/7/2017.
 */
class MacroNutrientCalculator extends BaseNutrientRdaCalculator
{

    public MacroNutrientCalculator(NutrientDatabase database)
    {
        super(database);
    }

    @Override
    public RdaResult calculate(BodyData b, NutrientType nutrientType, RdaResult rootNutrient)
    {

        List<NutrientData> nutrientDataList = database.find(
                new NutrientFilter().bodyData(b).nutrientType(nutrientType)
        );

        double rdaMin = 0;
        double rdaMax = 0;
        MeasurementUnit rdaUnit = nutrientType.getDefaultMeasurementUnit();

        for (NutrientData n : nutrientDataList) {
            if (n.getRdaUnit() == GRAM_PER_KILO_GRAM) {
                // Protein
                rdaMin += multiply(Converter.get(n).getRdaMin(), Converter.get(b).getWeight(KILO_GRAM));
                rdaMax += multiply(Converter.get(n).getRdaMax(), Converter.get(b).getWeight(KILO_GRAM));
                rdaUnit = UnitType.MASS_UNIT.getBasicUnit();
            } else if (n.getRdaUnit() == PERCENT_OF_TOTAL_ENERGY) {
                // Carbohydrate, Protein, Fat
                RdaResult en = rootNutrient.getSubResult(ENERGY);
                if (en != null) {
                    if (nutrientType == CARBOHYDRATE_AND_FIBER) {
                        rdaMin += BigDecimal.valueOf(Converter.get(n).getRdaMin())
                                .multiply(BigDecimal.valueOf(Converter.get(en).getRdaMin()))
                                .divide(BigDecimal.valueOf(4_000), BigDecimal.ROUND_CEILING)
                                .doubleValue();
                        rdaMax += BigDecimal.valueOf(Converter.get(n).getRdaMax())
                                .multiply(BigDecimal.valueOf(Converter.get(en).getRdaMax()))
                                .divide(BigDecimal.valueOf(4_000), BigDecimal.ROUND_CEILING)
                                .doubleValue();
                    } else if (nutrientType == PROTEIN) {
                        rdaMin += BigDecimal.valueOf(Converter.get(n).getRdaMin())
                                .multiply(BigDecimal.valueOf(Converter.get(en).getRdaMin()))
                                .divide(BigDecimal.valueOf(4_000), BigDecimal.ROUND_CEILING)
                                .doubleValue();
                        rdaMax += BigDecimal.valueOf(Converter.get(n).getRdaMax())
                                .multiply(BigDecimal.valueOf(Converter.get(en).getRdaMax()))
                                .divide(BigDecimal.valueOf(4_000), BigDecimal.ROUND_CEILING)
                                .doubleValue();
                    } else if (nutrientType == FAT) {
                        rdaMin += BigDecimal.valueOf(Converter.get(n).getRdaMin())
                                .multiply(BigDecimal.valueOf(Converter.get(en).getRdaMin()))
                                .divide(BigDecimal.valueOf(9_000), BigDecimal.ROUND_CEILING)
                                .doubleValue();
                        rdaMax += BigDecimal.valueOf(Converter.get(n).getRdaMax())
                                .multiply(BigDecimal.valueOf(Converter.get(en).getRdaMax()))
                                .divide(BigDecimal.valueOf(9_000), BigDecimal.ROUND_CEILING)
                                .doubleValue();
                    }
                    rdaUnit = GRAM;
                } else {
                    rdaMin += Converter.get(n).getRdaMin();
                    rdaMax += Converter.get(n).getRdaMax();
                    rdaUnit = PERCENT_OF_TOTAL_ENERGY;
                }
            } else if (n.getRdaUnit() == GRAM_PER_KILO_CALORIE) {
                // Fiber
                RdaResult e = rootNutrient.getSubResult(ENERGY);
                if (e != null) {
                    rdaMin += multiply(Converter.get(n).getRdaMin(), Converter.get(e).getRdaMin(KILO_CALORIE));
                    rdaMax += multiply(Converter.get(n).getRdaMax(), Converter.get(e).getRdaMax(KILO_CALORIE));
                    rdaUnit = UnitType.MASS_UNIT.getBasicUnit();
                } else {
                    rdaMin += n.getRdaMin();
                    rdaMax += n.getRdaMax();
                    rdaUnit = GRAM_PER_KILO_CALORIE;
                }
            } else {
                // Gram
                rdaMin += Converter.get(n).getRdaMin();
                rdaMax += Converter.get(n).getRdaMax();
                rdaUnit = UnitType.MASS_UNIT.getBasicUnit();
            }
        }

        /* Convert to default unit */

        rdaMin = rdaUnit.fromBasicUnit(rdaMin);
        rdaMax = rdaUnit.fromBasicUnit(rdaMax);

        RdaResult result = new RdaResult(nutrientType, rdaMin, rdaMax, rdaUnit);

        return result;
    }
}
