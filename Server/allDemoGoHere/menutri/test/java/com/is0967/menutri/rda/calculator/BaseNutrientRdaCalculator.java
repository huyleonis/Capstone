package com.is0967.menutri.rda.calculator;

import com.is0967.menutri.rda.calculator.spi.NutrientRdaCalculator;
import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.data.RdaResult;
import com.is0967.menutri.rda.database.filter.NutrientFilter;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by phuctran93 on 3/6/2017.
 */
abstract class BaseNutrientRdaCalculator implements NutrientRdaCalculator
{

    protected final NutrientDatabase database;

    public BaseNutrientRdaCalculator(NutrientDatabase database)
    {
        this.database = database;
    }

    protected static double multiply(double value, double otherValue)
    {
        return BigDecimal.valueOf(value)
                .multiply(BigDecimal.valueOf(otherValue))
                .doubleValue();
    }

    @Override
    public RdaResult rda(BodyData bodyData, NutrientType nutrientType, RdaResult rootNutrient)
    {
        Objects.requireNonNull(bodyData);
        Objects.requireNonNull(nutrientType);
        Objects.requireNonNull(rootNutrient);

        RdaResult nutrient;

        if (nutrientType.isPlaceholder()) {
            nutrient = new RdaResult(nutrientType);
        } else {
            // Check if database has stored nutrient for specific body
            NutrientData calculatedData = database.findCalculatedData(
                    new NutrientFilter().bodyData(bodyData).nutrientType(nutrientType)
            );

            if (calculatedData != null) {
                // Database has calculated data, then convert from NutrientData to NutrientResult
                nutrient = new RdaResult(calculatedData.getNutrientType(), calculatedData.getRdaMin(), calculatedData.getRdaMax(), calculatedData.getRdaUnit());
            } else {
                // No calculated data found, then calculate them
                nutrient = calculate(bodyData, nutrientType, rootNutrient);
            }
        }

        // Add to result tree
        RdaResult parentNutrient = rootNutrient.getSubResult(nutrientType.getParent());
        if (parentNutrient != null) {
            parentNutrient.addSubResult(nutrient);
        }

        return nutrient;
    }

    protected abstract RdaResult calculate(BodyData bodyData, NutrientType nutrientType, RdaResult rootNutrient);
}
