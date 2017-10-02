package com.is0967.menutri.rda.data;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by phuctran93 on 3/10/2017.
 */
public class RdaResult
{

    private final NutrientType nutrientType;
    private final Double rdaMin;
    private final Double rdaMax;
    private final MeasurementUnit rdaUnit;

    private final SortedMap<NutrientType, RdaResult> children = new TreeMap<>();

    public RdaResult(NutrientType nutrientType)
    {
        this.nutrientType = nutrientType;
        this.rdaMin = null;
        this.rdaMax = null;
        this.rdaUnit = null;
    }

    public RdaResult(NutrientType nutrientType, Double rdaMin, Double rdaMax,
            MeasurementUnit rdaUnit)
    {
        this.nutrientType = nutrientType;
        this.rdaMin = rdaMin;
        this.rdaMax = rdaMax;
        this.rdaUnit = rdaUnit;
    }

    public void addSubResult(RdaResult result)
    {
        this.children.put(result.getNutrientType(), result);
    }

    public RdaResult[] getSubResult()
    {
        return children.values().toArray(new RdaResult[children.size()]);
    }

    public RdaResult getSubResult(NutrientType nutrientType)
    {
        if (nutrientType == this.nutrientType) {
            return this;
        }
        for (NutrientType childType : children.keySet()) {
            if (nutrientType.is(childType)) {
                return children.get(childType).getSubResult(nutrientType);
            }
        }
        return null;
    }

    public NutrientType getNutrientType()
    {
        return nutrientType;
    }

    public Double getRdaMin()
    {
        return rdaMin;
    }

    public Double getRdaMax()
    {
        return rdaMax;
    }

    public MeasurementUnit getRdaUnit()
    {
        return rdaUnit;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RdaResult{\n");
        toString(stringBuilder, 1);
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    private void toString(StringBuilder stringBuilder, int level)
    {
        String levelIndicator = new String(new char[level * 4]).replaceAll("\0", " ");
        stringBuilder.append(levelIndicator)
                .append(nutrientType).append(": ")
                .append("{").append(rdaMin).append("; ").append(rdaMax).append("} ")
                .append(this.nutrientType.getDefaultMeasurementUnit())
                .append("\n");
        level++;
        for (RdaResult subResult : this.children.values()) {
            subResult.toString(stringBuilder, level);
        }
    }
}
