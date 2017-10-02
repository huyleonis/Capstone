package com.is0967.menutri.rda.database.populater;

import static com.is0967.menutri.rda.data.BodyData.Age.MAX;
import static com.is0967.menutri.rda.data.BodyData.Age.Y18;
import static com.is0967.menutri.rda.data.BodyData.Age.Y3;
import static com.is0967.menutri.rda.data.BodyData.Age.Y30;
import static com.is0967.menutri.rda.data.BodyData.Age.Y60;
import static com.is0967.menutri.rda.data.BodyData.Age.Y9;
import static com.is0967.menutri.rda.data.BodyData.Age.ZERO;
import static com.is0967.menutri.rda.data.Gender.FEMALE;
import static com.is0967.menutri.rda.data.Gender.MALE;

import com.is0967.menutri.rda.data.ReeEquationCoefficient;
import com.is0967.menutri.rda.database.populater.spi.ReePopulater;
import java.util.List;

/**
 * Created by phuctran93 on 2/28/2017.
 */
class VnReeEquationPopulater implements ReePopulater
{

    @Override
    public void populate(List<ReeEquationCoefficient> database)
    {
        database.add(new ReeEquationCoefficient(ZERO, Y3, 60.9d, -54d, MALE));
        database.add(new ReeEquationCoefficient(Y3, Y9, 22.7d, 495d, MALE));
        database.add(new ReeEquationCoefficient(Y9, Y18, 17.5d, 651d, MALE));
        database.add(new ReeEquationCoefficient(Y18, Y30, 15.3d, 679d, MALE));
        database.add(new ReeEquationCoefficient(Y30, Y60, 11.6d, 879d, MALE));
        database.add(new ReeEquationCoefficient(Y60, MAX, 13.5d, 487d, MALE));
        database.add(new ReeEquationCoefficient(ZERO, Y3, 61d, -51d, FEMALE));
        database.add(new ReeEquationCoefficient(Y3, Y9, 22.5d, 499d, FEMALE));
        database.add(new ReeEquationCoefficient(Y9, Y18, 12.2d, 746d, FEMALE));
        database.add(new ReeEquationCoefficient(Y18, Y30, 14.7d, 496d, FEMALE));
        database.add(new ReeEquationCoefficient(Y30, Y60, 8.7d, 829d, FEMALE));
        database.add(new ReeEquationCoefficient(Y60, MAX, 10.5d, 506d, FEMALE));
    }
}
