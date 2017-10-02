package com.is0967.menutri.rda.data;

import java.time.Period;

/**
 * Created by phuctran93 on 3/1/2017.
 */
public class ReeEquationCoefficient
{

    private final double coefficient;
    private final double constantTerm;
    private final Period minAge;
    private final Period maxAge;
    private final Gender gender;

    public ReeEquationCoefficient(Period minAge, Period maxAge, double coefficient,
            double constantTerm, Gender gender)
    {
        this.coefficient = coefficient;
        this.constantTerm = constantTerm;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.gender = gender;
    }

    public double getCoefficient()
    {
        return coefficient;
    }

    public double getConstantTerm()
    {
        return constantTerm;
    }

    public Period getMinAge()
    {
        return minAge;
    }

    public Period getMaxAge()
    {
        return maxAge;
    }

    public Gender getGender()
    {
        return gender;
    }
}
