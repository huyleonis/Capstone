package com.is0967.menutri.rda.database.populater;

import com.is0967.menutri.rda.data.ReeEquationCoefficient;
import com.is0967.menutri.rda.database.populater.spi.ReePopulater;
import java.util.List;

/**
 * Created by phuctran93 on 3/14/2017.
 */
public enum ReePopulaterFactory implements ReePopulater
{
    VREP {
        @Override
        public void populate(List<ReeEquationCoefficient> database)
        {
            new VnReeEquationPopulater().populate(database);
        }
    }
}
