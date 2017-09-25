package com.is0967.menutri.rda.database.filter;

import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.data.RdaType;
import java.util.function.Predicate;

/**
 * Created by phuctran93 on 3/4/2017.
 */
public class NutrientFilter implements Predicate<NutrientData>
{

    private BodyData bodyData;
    private NutrientType nutrientType;
    private RdaType rdaType;

    public static NutrientFilter byBodyData(BodyData bodyData)
    {
        return new NutrientFilter().bodyData(bodyData);
    }

    public static NutrientFilter byNutrientType(NutrientType nutrientType)
    {
        return new NutrientFilter().nutrientType(nutrientType);
    }

    public static Predicate<NutrientData> byRdaType(RdaType rdaType)
    {
        return (n -> n.getRdaType() == rdaType);
    }

    public static NutrientFilter byRdaUnitType(RdaType rdaType)
    {
        return new NutrientFilter().rdaType(rdaType);
    }

    @Override
    public boolean test(NutrientData n)
    {
        return ((
                bodyData == null || ((
                        n.getMinAge() == null || bodyData.isFrom(n.getMinAge()))
                        && (n.getMaxAge() == null || bodyData.isUnder(n.getMaxAge()))
                        && (n.getGender() == null || n.getGender() == bodyData.getGender())
                        && (n.getActivityLevel() == null || n.getActivityLevel() == bodyData.getActivityLevel())
                        && (n.getMonthOfPregnancy() == null
                        || bodyData.isPregnant(n.getMonthOfPregnancy()[0], n.getMonthOfPregnancy()[1]))
                        && (n.getMonthOfLactation() == null
                        || bodyData.isLactating(n.getMonthOfLactation()[0], n.getMonthOfLactation()[1]))
                        && (n.isHealthy() == null || n.isHealthy() == bodyData.isHealthy())
                        && (n.isMenstruating() == null || n.isMenstruating() == bodyData.isMenstruating())
                        && (n.isHiv() == null || n.isHiv() == bodyData.isHiv())
                        && (n.isAids() == null || n.isAids() == bodyData.isAids()))
                        && (n.getBioAvailability() == null || n.getBioAvailability() == bodyData.getBioAvailability())
        )
                && (nutrientType == null || n.getNutrientType() == nutrientType)
                && (rdaType == null || rdaType == n.getRdaType())
        );
    }

    public NutrientFilter bodyData(BodyData bodyData)
    {
        this.bodyData = bodyData;
        return this;
    }

    public NutrientFilter nutrientType(NutrientType nutrientType)
    {
        this.nutrientType = nutrientType;
        return this;
    }

    public BodyData getBodyData()
    {
        return bodyData;
    }

    public NutrientType getNutrientType()
    {
        return nutrientType;
    }

    public NutrientFilter rdaType(RdaType rdaType)
    {
        this.rdaType = rdaType;
        return this;
    }
}
