package com.is0967.menutri.rda.database.filter;

import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.ReeEquationCoefficient;
import java.util.function.Predicate;

/**
 * Created by phuctran93 on 3/4/2017.
 */
public class ReeEquationFilter
{

    public static Predicate<ReeEquationCoefficient> byBodyData(BodyData bodyData)
    {
        return (e ->
                (e.getGender() == null || e.getGender() == bodyData.getGender())
                        && (e.getMinAge() == null || BodyData.Age.lessEqual(e.getMinAge(), bodyData.getAge()))
                        && (e.getMaxAge() == null || BodyData.Age.lessThan(bodyData.getAge(), e.getMaxAge())));
    }
}
