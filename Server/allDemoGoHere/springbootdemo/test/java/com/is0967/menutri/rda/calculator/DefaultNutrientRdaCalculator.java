package com.is0967.menutri.rda.calculator;

import static com.is0967.menutri.rda.data.NutrientType.ELEMENT;
import static com.is0967.menutri.rda.data.NutrientType.ENERGY;
import static com.is0967.menutri.rda.data.NutrientType.MACRO_NUTRIENT;
import static com.is0967.menutri.rda.data.NutrientType.VITAMIN;
import static com.is0967.menutri.rda.data.NutrientType.WATER;

import com.is0967.menutri.rda.calculator.spi.NutrientRdaCalculator;
import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.data.RdaResult;
import com.is0967.menutri.rda.database.NutrientDatabaseService;
import com.is0967.menutri.rda.database.ReeEquationDatabaseService;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;
import com.is0967.menutri.rda.database.spi.ReeEquationDatabase;

/**
 * Created by phuctran93 on 3/11/2017.
 */
public class DefaultNutrientRdaCalculator implements NutrientRdaCalculator
{

    private final NutrientDatabase database;
    private final ReeEquationDatabase reeEquationDatabase;

    private final NutrientRdaCalculator babyEnergy;
    private final NutrientRdaCalculator childrenAdolescentEnergy;
    private final NutrientRdaCalculator energyCalculator;
    private final NutrientRdaCalculator macroCalculator;
    private final NutrientRdaCalculator simpleCalculator;
    private final NutrientRdaCalculator waterCalculator;

    public DefaultNutrientRdaCalculator()
    {
        this.database = NutrientDatabaseService.INSTANCE;
        this.reeEquationDatabase = ReeEquationDatabaseService.INSTANCE;
        energyCalculator = new EnergyCalculator(database, reeEquationDatabase);
        macroCalculator = new MacroNutrientCalculator(database);
        simpleCalculator = new SimpleNutrientCalculator(database);
        waterCalculator = new WaterCalculator(database);
        babyEnergy = new BabyEnergyCalculator(database);
        childrenAdolescentEnergy = new ChildrenAdolescentEnergyCalculator(database);
    }

    @Override
    public RdaResult rda(BodyData bodyData, NutrientType nutrientType, RdaResult rootNutrient)
    {
        if (nutrientType.is(ENERGY)) {
            long ageInMonths = bodyData.getAge().toTotalMonths();
            if (ageInMonths < 12) {
                return babyEnergy.rda(bodyData, nutrientType, rootNutrient);
            } else if (ageInMonths < 12 * 18) {
                return childrenAdolescentEnergy.rda(bodyData, nutrientType, rootNutrient);
            } else { return energyCalculator.rda(bodyData, nutrientType, rootNutrient); }
        } else if (nutrientType.is(MACRO_NUTRIENT)) {
            return macroCalculator.rda(bodyData, nutrientType, rootNutrient);
        } else if (nutrientType.is(ELEMENT)) {
            return simpleCalculator.rda(bodyData, nutrientType, rootNutrient);
        } else if (nutrientType.is(VITAMIN)) {
            return simpleCalculator.rda(bodyData, nutrientType, rootNutrient);
        } else if (nutrientType.is(WATER)) { return waterCalculator.rda(bodyData, nutrientType, rootNutrient); } else {
            throw new UnsupportedOperationException("Cannot calculate nutrient requirement for type: " + nutrientType);
        }
    }
}
