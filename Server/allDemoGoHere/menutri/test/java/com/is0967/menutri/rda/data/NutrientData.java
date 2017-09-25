package com.is0967.menutri.rda.data;

import static com.is0967.menutri.rda.data.RdaType.CALCULATED;

import java.time.Period;
import java.util.Arrays;

/**
 * Created by phuctran93 on 3/1/2017.
 */
public class NutrientData
{

    private final String name;
    /* Required Data */
    private final NutrientType nutrientType;
    private final Period minAge;
    private final Period maxAge;
    private final Double rdaMin;
    private final Double rdaMax;
    private final MeasurementUnit rdaUnit;
    private final RdaType rdaType;

    private final BioAvailability bioAvailability;
    private final Double limitMin;
    private final Double limitMax;
    private final MeasurementUnit limitUnit;

    private final Gender gender;
    private final ActivityLevel activityLevel;
    private final int[] monthOfPregnancy;
    private final int[] monthOfLactation;

    private final Boolean menstruating;
    private final Boolean healthy;
    private final Boolean hiv;
    private final Boolean aids;

    private NutrientData(Builder builder)
    {
        this.name = builder.name;
        this.nutrientType = builder.nutrientType;
        this.minAge = builder.minAge;
        this.maxAge = builder.maxAge;
        this.rdaMin = builder.rdaMin;
        this.rdaMax = builder.rdaMax;
        this.rdaUnit = builder.rdaUnit;
        this.rdaType = builder.rdaType;

        this.bioAvailability = builder.bioAvailability;
        this.limitMin = builder.limitMin;
        this.limitMax = builder.limitMax;
        this.limitUnit = builder.limitUnit;

        this.gender = builder.gender;
        this.activityLevel = builder.activityLevel;
        this.monthOfPregnancy = builder.monthOfPregnancy;
        this.monthOfLactation = builder.monthOfLactation;

        this.menstruating = builder.menstruating;
        this.healthy = builder.healthy;
        this.hiv = builder.hiv;
        this.aids = builder.aids;
    }

    public String getName()
    {
        return name != null ? name : "";
    }

    public NutrientType getNutrientType()
    {
        return nutrientType;
    }

    public Period getMinAge()
    {
        return minAge;
    }

    public Period getMaxAge()
    {
        return maxAge;
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

    public RdaType getRdaType()
    {
        return rdaType;
    }

    public Gender getGender()
    {
        return gender;
    }

    public ActivityLevel getActivityLevel()
    {
        return activityLevel;
    }

    public Double getLimitMin()
    {
        return limitMin;
    }

    public Double getLimitMax()
    {
        return limitMax;
    }

    public MeasurementUnit getLimitUnit()
    {
        return limitUnit;
    }

    public Boolean isMenstruating()
    {
        return menstruating;
    }

    public Boolean isHealthy()
    {
        return healthy;
    }

    public Boolean isHiv()
    {
        return hiv;
    }

    public Boolean isAids()
    {
        return aids;
    }

    public BioAvailability getBioAvailability()
    {
        return bioAvailability;
    }

    public int[] getMonthOfPregnancy()
    {
        return monthOfPregnancy == null
                ? null
                : monthOfPregnancy.clone();
    }

    public int[] getMonthOfLactation()
    {
        return monthOfLactation == null
                ? null
                : monthOfLactation.clone();
    }

    @Override
    public String toString()
    {
        return "NutrientData{" +
                "name='" + name + '\'' +
                "\n    nutrientType=" + nutrientType +
                "\n    minAge=" + minAge +
                "\n    maxAge=" + maxAge +
                "\n    rdaMin=" + rdaMin +
                "\n    rdaMax=" + rdaMax +
                "\n    rdaUnit=" + rdaUnit +
                "\n    rdaType=" + rdaType +
                "\n    bioAvailability=" + bioAvailability +
                "\n    limitMin=" + limitMin +
                "\n    limitMax=" + limitMax +
                "\n    limitUnit=" + limitUnit +
                "\n    gender=" + gender +
                "\n    activityLevel=" + activityLevel +
                "\n    monthOfPregnancy=" + Arrays.toString(monthOfPregnancy) +
                "\n    monthOfLactation=" + Arrays.toString(monthOfLactation) +
                "\n    menstruating=" + menstruating +
                "\n    healthy=" + healthy +
                "\n    hiv=" + hiv +
                "\n    aids=" + aids +
                '}';
    }

    public static class Builder
    {

        private final NutrientType nutrientType;
        private final Period minAge;
        private final Period maxAge;
        private final Double rdaMin;
        private final Double rdaMax;
        private final MeasurementUnit rdaUnit;
        private final RdaType rdaType;

        private String name;
        private BioAvailability bioAvailability;
        private Double limitMin;
        private Double limitMax;
        private MeasurementUnit limitUnit;

        private Gender gender;
        private ActivityLevel activityLevel;
        private int[] monthOfPregnancy;
        private int[] monthOfLactation;

        private Boolean healthy;
        private Boolean menstruating;
        private Boolean hiv;
        private Boolean aids;

        public Builder(NutrientType nutrientType,
                Period minAge,
                Period maxAge,
                Double rdaMin,
                Double rdaMax,
                MeasurementUnit rdaUnit,
                RdaType rdaType)
        {
            this.nutrientType = nutrientType;
            this.minAge = minAge;
            this.maxAge = maxAge;
            this.rdaMin = rdaMin;
            this.rdaMax = rdaMax;
            this.rdaUnit = rdaUnit;
            this.rdaType = rdaType;
        }

        public Builder(BodyData bodyData,
                NutrientType nutrientType,
                Double rdaMin,
                Double rdaMax,
                MeasurementUnit rdaUnit)
        {
            this.nutrientType = nutrientType;
            this.minAge = bodyData.getAge();
            this.maxAge = bodyData.getAge();
            this.rdaMin = rdaMin;
            this.rdaMax = rdaMax;
            this.rdaUnit = rdaUnit;
            this.rdaType = CALCULATED;

            this.gender = bodyData.getGender();
            this.activityLevel = bodyData.getActivityLevel();
            this.pregnancy(bodyData.getMonthOfPregnancy(), bodyData.getMonthOfPregnancy());
            this.lactation(bodyData.getMonthOfLactation(), bodyData.getMonthOfLactation());

            this.menstruating = bodyData.isMenstruating();
            this.healthy = bodyData.isHealthy();
            this.hiv = bodyData.isHiv();
            this.aids = bodyData.isAids();
        }

        public Builder name(String name)
        {
            this.name = name;
            return this;
        }

        public Builder limit(Double limitMin, Double limitMax)
        {
            return limit(limitMin, limitMax, this.rdaUnit);
        }

        public Builder limit(Double limitMin, Double limitMax, MeasurementUnit limitUnit)
        {
            if (limitMin == null && limitMax == null) {
                throw new IllegalArgumentException("At lest one limit value is required.");
            }

            this.limitMin = limitMin;
            this.limitMax = limitMax;
            this.limitUnit = limitUnit;
            return this;
        }

        public Builder gender(Gender gender)
        {
            this.gender = gender;
            return this;
        }

        public Builder activityLevel(ActivityLevel activityLevel)
        {
            this.activityLevel = activityLevel;
            return this;
        }

        public Builder notPregnantAndLactate()
        {
            this.monthOfPregnancy = new int[]{0, 0};
            this.monthOfLactation = new int[]{0, 0};
            return this;
        }

        public Builder pregnancy(int fromMonth, int toMonth)
        {
            if (fromMonth > toMonth) {
                throw new IllegalArgumentException("Invalid month range.");
            }

            this.monthOfPregnancy = new int[2];
            this.monthOfPregnancy[0] = fromMonth;
            this.monthOfPregnancy[1] = toMonth;
            return this;
        }

        public Builder lactation(int fromMonth, int toMonth)
        {
            if (fromMonth > toMonth) {
                throw new IllegalArgumentException("Invalid month range.");
            }

            this.monthOfLactation = new int[2];
            this.monthOfLactation[0] = fromMonth;
            this.monthOfLactation[1] = toMonth;
            return this;
        }

        public Builder healthy(Boolean healthy)
        {
            this.healthy = healthy;
            return this;
        }

        public Builder menstruating(Boolean menstruating)
        {
            this.menstruating = menstruating;
            return this;
        }

        public Builder hiv(Boolean hiv)
        {
            this.hiv = hiv;
            return this;
        }

        public Builder aids(Boolean aids)
        {
            this.aids = aids;
            return this;
        }

        public Builder bioAvailability(BioAvailability bioAvailability)
        {
            this.bioAvailability = bioAvailability;
            return this;
        }

        public NutrientData build()
        {
            return new NutrientData(this);
        }
    }

}
