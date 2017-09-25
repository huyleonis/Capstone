package com.is0967.menutri.rda.database;

import com.is0967.menutri.rda.data.Gender;
import com.is0967.menutri.rda.data.ReeEquationCoefficient;
import com.is0967.menutri.rda.database.spi.ReeEquationDatabase;
import java.time.Period;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.function.Predicate;

/**
 * Created by phuctran93 on 3/11/2017.
 */
public enum ReeEquationDatabaseService implements ReeEquationDatabase
{
    INSTANCE;
    private ServiceLoader<ReeEquationDatabase> loader;

    ReeEquationDatabaseService()
    {
        loader = ServiceLoader.load(ReeEquationDatabase.class);
    }

    @Override
    public ReeEquationCoefficient getCoefficient(Gender gender, Period age)
    {
        ReeEquationCoefficient result = null;
        try {
            Iterator<ReeEquationDatabase> iterator = loader.iterator();
            while (result == null && iterator.hasNext()) {
                ReeEquationDatabase database = iterator.next();
                result = database.getCoefficient(gender, age);
            }
        } catch (ServiceConfigurationError serviceError) {
            result = null;
            serviceError.printStackTrace();
        }
        return result;
    }

    @Override
    public ReeEquationCoefficient getCoefficient(Predicate<ReeEquationCoefficient> filter)
    {
        ReeEquationCoefficient result = null;
        try {
            Iterator<ReeEquationDatabase> iterator = loader.iterator();
            while (result == null && iterator.hasNext()) {
                ReeEquationDatabase database = iterator.next();
                result = database.getCoefficient(filter);
            }
        } catch (ServiceConfigurationError serviceError) {
            result = null;
            serviceError.printStackTrace();
        }
        return result;
    }
}
