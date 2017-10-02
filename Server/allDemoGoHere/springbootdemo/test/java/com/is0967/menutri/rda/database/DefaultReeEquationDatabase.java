package com.is0967.menutri.rda.database;

import static com.is0967.menutri.rda.data.BodyData.Age;

import com.is0967.menutri.rda.data.Gender;
import com.is0967.menutri.rda.data.ReeEquationCoefficient;
import com.is0967.menutri.rda.database.populater.ReePopulaterFactory;
import com.is0967.menutri.rda.database.spi.ReeEquationDatabase;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by phuctran93 on 3/11/2017.
 */
public class DefaultReeEquationDatabase implements ReeEquationDatabase
{

    private final List<ReeEquationCoefficient> database = new ArrayList<>();

    public DefaultReeEquationDatabase()
    {
        ReePopulaterFactory.VREP.populate(this.database);
    }

    @Override
    public ReeEquationCoefficient getCoefficient(Gender gender, Period age)
    {
        return database.stream()
                .filter(c -> Age.lessEqual(c.getMinAge(), age) && Age.lessThan(age, c.getMaxAge())
                        && (gender == null || c.getGender() == gender))
                .findFirst()
                .get();
    }

    @Override
    public ReeEquationCoefficient getCoefficient(Predicate<ReeEquationCoefficient> filter)
    {
        return database.stream()
                .filter(filter)
                .findFirst()
                .get();
    }
}
