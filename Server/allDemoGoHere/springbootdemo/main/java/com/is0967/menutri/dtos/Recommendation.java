package com.is0967.menutri.dtos;

import com.is0967.menutri.entities.Formula.RdaType;
import com.is0967.menutri.entities.Nutrient;

/**
 * Created by phuctran93 on 4/4/2017.
 */
public class Recommendation
{

    private long id;
    private Nutrient nutrient;
    private double value;
    private double delta;
    private RdaType type;
    private Integer upperLimit;

    @Override
    public String toString()
    {
        return "Recommendation{" +
                "\n    id=" + id +
                "\n    nutrient=" + nutrient +
                "\n    value=" + value +
                "\n    delta=" + delta +
                "\n    type=" + type +
                "\n    upperLimit=" + upperLimit +
                "\n}";
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Nutrient getNutrient()
    {
        return nutrient;
    }

    public void setNutrient(Nutrient nutrient)
    {
        this.nutrient = nutrient;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    public double getDelta()
    {
        return delta;
    }

    public void setDelta(double delta)
    {
        this.delta = delta;
    }

    public RdaType getType()
    {
        return type;
    }

    public void setType(RdaType type)
    {
        this.type = type;
    }

    public Integer getUpperLimit()
    {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit)
    {
        this.upperLimit = upperLimit;
    }
}
