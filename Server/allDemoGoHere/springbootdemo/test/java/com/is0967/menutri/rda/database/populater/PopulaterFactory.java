package com.is0967.menutri.rda.database.populater;

import com.is0967.menutri.rda.database.populater.spi.Populater;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;

/**
 * Created by phuctran93 on 3/7/2017.
 */
public enum PopulaterFactory implements Populater
{
    VPOP {
        @Override
        public void populate(NutrientDatabase database)
        {
            new VnEnergyPopulater().populate(database);
            new VnCarbAndFiberPopulater().populate(database);
            new VnProteinPopulater().populate(database);
            new VnFatPopulater().populate(database);
            new VnMineralPopulater().populate(database);
            new VnTraceElementPopulater().populate(database);
            new VnElectrolytePopulater().populate(database);
            new VnFatSoluableVitaminPopulater().populate(database);
            new VnWaterSolubleVitaminPopulater().populate(database);
            new VnWaterPopulater().populate(database);
        }
    }
}
