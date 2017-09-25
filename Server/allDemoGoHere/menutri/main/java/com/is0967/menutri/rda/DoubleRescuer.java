package com.is0967.menutri.rda;

import java.math.BigDecimal;
import org.apache.log4j.Logger;

/**
 * Mutable version of BigDecimal
 * Created by phuctran93 on 4/8/2017.
 */
public class DoubleRescuer
{
    private static final Logger logger = Logger.getLogger(DoubleRescuer.class);
    private BigDecimal value;

    public DoubleRescuer(double val)
    {
        value = BigDecimal.valueOf(val);
    }

    public DoubleRescuer add(double val)
    {
        logger.trace("ADD " + val);
        value = value.add(BigDecimal.valueOf(val));
        return this;
    }

    public DoubleRescuer subtract(double val)
    {
        logger.trace("SUBTRACT " + val);
        value = value.subtract(BigDecimal.valueOf(val));
        return this;
    }

    public DoubleRescuer multiply(double val)
    {
        logger.trace("MULTIPLY " + val);
        value = value.multiply(BigDecimal.valueOf(val));
        return this;
    }

    public DoubleRescuer divide(double val)
    {
        logger.trace("DIVIDE " + val);
        value = value.divide(BigDecimal.valueOf(val), BigDecimal.ROUND_HALF_EVEN);
        return this;
    }

    public double doubleValue()
    {
        return value.doubleValue();
    }

    @Override
    public String toString()
    {
        return "DoubleRescuer{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof DoubleRescuer)) return false;

        DoubleRescuer that = (DoubleRescuer) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode()
    {
        return value.hashCode();
    }
}
