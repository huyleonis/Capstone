package com.is0967.menutri.rda.database.populater.spi;

import com.is0967.menutri.rda.data.ReeEquationCoefficient;
import java.util.List;

/**
 * Created by phuctran93 on 3/11/2017.
 */
public interface ReePopulater
{

    void populate(List<ReeEquationCoefficient> database);
}
