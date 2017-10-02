package com.is0967.menutri.rda.database.spi;

import com.is0967.menutri.rda.data.Gender;
import com.is0967.menutri.rda.data.ReeEquationCoefficient;
import java.time.Period;
import java.util.function.Predicate;

/**
 * Created by phuctran93 on 3/2/2017.
 */
public interface ReeEquationDatabase
{

    ReeEquationCoefficient getCoefficient(Gender gender, Period age);

    ReeEquationCoefficient getCoefficient(Predicate<ReeEquationCoefficient> filter);
}
