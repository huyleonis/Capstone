package com.is0967.menutri.entities;

import com.is0967.menutri.entities.Body.ActivityLevel;
import com.is0967.menutri.entities.Body.BioAvailability;
import com.is0967.menutri.entities.Body.Gender;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * Created by phuctran93 on 4/3/2017.
 */
@Entity(name = "formulas")
public class Formula
{

    @Id
    @GeneratedValue(generator = "formulas_id_seq", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "formulas_id_seq", sequenceName = "formulas_id_seq")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "body_id")
    private Body body;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "nutrient_id")
    private Nutrient nutrient;

    private Integer ageFrom;
    private Integer ageToUnder;
    private Gender gender;
    private Double heightFrom;
    private Double heightToUnder;
    private Double weightFrom;
    private Double weightToUnder;
    private Integer monthOfPregnancyFrom;
    private Integer monthOfPregnancyTo;
    private Integer monthOfLactationFrom;
    private Integer monthOfLactationTo;
    private Boolean healthy;
    private Boolean menstruation;
    @Enumerated(EnumType.ORDINAL)
    private ActivityLevel physicalActivityLevel;
    @Enumerated(EnumType.ORDINAL)
    private BioAvailability bioAvailability;

    private String rdaValue;
    private String rdaDelta;
    @Enumerated(EnumType.ORDINAL)
    private RdaType rdaType;
    private String description;
    private String reference;

    @Override
    public String toString()
    {
        String body = this.body != null ? this.body.getId() + " - " + this.body.getName() : null;
        String nutrient = this.nutrient != null ? this.nutrient.getId() + " - " + this.nutrient.getName() : null;
        return "Formula{" +
                "id=" + id +
                ", body=" + body +
                ", nutrient=" + nutrient +
                ", ageFrom=" + ageFrom +
                ", ageToUnder=" + ageToUnder +
                ", gender=" + gender +
                ", heightFrom=" + heightFrom +
                ", heightToUnder=" + heightToUnder +
                ", weightFrom=" + weightFrom +
                ", weightToUnder=" + weightToUnder +
                ", monthOfPregnancyFrom=" + monthOfPregnancyFrom +
                ", monthOfPregnancyTo=" + monthOfPregnancyTo +
                ", monthOfLactationFrom=" + monthOfLactationFrom +
                ", monthOfLactationTo=" + monthOfLactationTo +
                ", healthy=" + healthy +
                ", menstruation=" + menstruation +
                ", physicalActivityLevel=" + physicalActivityLevel +
                ", bioAvailability=" + bioAvailability +
                ", rdaValue='" + rdaValue + '\'' +
                ", rdaDelta='" + rdaDelta + '\'' +
                ", rdaType=" + rdaType +
                ", description='" + description + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Formula)) return false;

        Formula formula = (Formula) o;

        if (body != null ? !body.equals(formula.body) : formula.body != null) return false;
        if (nutrient != null ? !nutrient.equals(formula.nutrient) : formula.nutrient != null) return false;
        if (ageFrom != null ? !ageFrom.equals(formula.ageFrom) : formula.ageFrom != null) return false;
        if (ageToUnder != null ? !ageToUnder.equals(formula.ageToUnder) : formula.ageToUnder != null) return false;
        if (gender != formula.gender) return false;
        if (heightFrom != null ? !heightFrom.equals(formula.heightFrom) : formula.heightFrom != null) return false;
        if (heightToUnder != null ? !heightToUnder.equals(formula.heightToUnder) : formula.heightToUnder != null) {
            return false;
        }
        if (weightFrom != null ? !weightFrom.equals(formula.weightFrom) : formula.weightFrom != null) return false;
        if (weightToUnder != null ? !weightToUnder.equals(formula.weightToUnder) : formula.weightToUnder != null) {
            return false;
        }
        if (monthOfPregnancyFrom != null ? !monthOfPregnancyFrom.equals(formula.monthOfPregnancyFrom)
                : formula.monthOfPregnancyFrom != null) { return false; }
        if (monthOfPregnancyTo != null ? !monthOfPregnancyTo.equals(formula.monthOfPregnancyTo)
                : formula.monthOfPregnancyTo != null) { return false; }
        if (monthOfLactationFrom != null ? !monthOfLactationFrom.equals(formula.monthOfLactationFrom)
                : formula.monthOfLactationFrom != null) { return false; }
        if (monthOfLactationTo != null ? !monthOfLactationTo.equals(formula.monthOfLactationTo)
                : formula.monthOfLactationTo != null) { return false; }
        if (healthy != null ? !healthy.equals(formula.healthy) : formula.healthy != null) return false;
        if (menstruation != null ? !menstruation.equals(formula.menstruation) : formula.menstruation != null) {
            return false;
        }
        if (physicalActivityLevel != formula.physicalActivityLevel) return false;
        if (bioAvailability != formula.bioAvailability) return false;
        if (rdaValue != null ? !rdaValue.equals(formula.rdaValue) : formula.rdaValue != null) return false;
        if (rdaDelta != null ? !rdaDelta.equals(formula.rdaDelta) : formula.rdaDelta != null) return false;
        return rdaType == formula.rdaType;
    }

    @Override
    public int hashCode()
    {
        int result = body != null ? body.hashCode() : 0;
        result = 31 * result + (nutrient != null ? nutrient.hashCode() : 0);
        result = 31 * result + (ageFrom != null ? ageFrom.hashCode() : 0);
        result = 31 * result + (ageToUnder != null ? ageToUnder.hashCode() : -1);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (heightFrom != null ? heightFrom.hashCode() : 0);
        result = 31 * result + (heightToUnder != null ? heightToUnder.hashCode() : -1);
        result = 31 * result + (weightFrom != null ? weightFrom.hashCode() : 0);
        result = 31 * result + (weightToUnder != null ? weightToUnder.hashCode() : -1);
        result = 31 * result + (monthOfPregnancyFrom != null ? monthOfPregnancyFrom.hashCode() : 0);
        result = 31 * result + (monthOfPregnancyTo != null ? monthOfPregnancyTo.hashCode() : 0);
        result = 31 * result + (monthOfLactationFrom != null ? monthOfLactationFrom.hashCode() : 0);
        result = 31 * result + (monthOfLactationTo != null ? monthOfLactationTo.hashCode() : 0);
        result = 31 * result + (healthy != null ? healthy.hashCode() : -1);
        result = 31 * result + (menstruation != null ? menstruation.hashCode() : -1);
        result = 31 * result + (physicalActivityLevel != null ? physicalActivityLevel.hashCode() : -1);
        result = 31 * result + (bioAvailability != null ? bioAvailability.hashCode() : -1);
        result = 31 * result + (rdaValue != null ? rdaValue.hashCode() : -1);
        result = 31 * result + (rdaDelta != null ? rdaDelta.hashCode() : -1);
        result = 31 * result + (rdaType != null ? rdaType.hashCode() : -1);
        return result;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Body getBody()
    {
        return body;
    }

    public void setBody(Body body)
    {
        this.body = body;
    }

    public Nutrient getNutrient()
    {
        return nutrient;
    }

    public void setNutrient(Nutrient nutrient)
    {
        this.nutrient = nutrient;
    }

    public Integer getAgeFrom()
    {
        return ageFrom;
    }

    public void setAgeFrom(Integer ageFrom)
    {
        this.ageFrom = ageFrom;
    }

    public Integer getAgeToUnder()
    {
        return ageToUnder;
    }

    public void setAgeToUnder(Integer ageTo)
    {
        this.ageToUnder = ageTo;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public Double getHeightFrom()
    {
        return heightFrom;
    }

    public void setHeightFrom(Double heightFrom)
    {
        this.heightFrom = heightFrom;
    }

    public Double getHeightToUnder()
    {
        return heightToUnder;
    }

    public void setHeightToUnder(Double heightTo)
    {
        this.heightToUnder = heightTo;
    }

    public Double getWeightFrom()
    {
        return weightFrom;
    }

    public void setWeightFrom(Double weightFrom)
    {
        this.weightFrom = weightFrom;
    }

    public Double getWeightToUnder()
    {
        return weightToUnder;
    }

    public void setWeightToUnder(Double weightTo)
    {
        this.weightToUnder = weightTo;
    }

    public Integer getMonthOfPregnancyFrom()
    {
        return monthOfPregnancyFrom;
    }

    public void setMonthOfPregnancyFrom(Integer monthOfPregnancyFrom)
    {
        this.monthOfPregnancyFrom = monthOfPregnancyFrom;
    }

    public Integer getMonthOfPregnancyTo()
    {
        return monthOfPregnancyTo;
    }

    public void setMonthOfPregnancyTo(Integer monthOfPregnancyTo)
    {
        this.monthOfPregnancyTo = monthOfPregnancyTo;
    }

    public Integer getMonthOfLactationFrom()
    {
        return monthOfLactationFrom;
    }

    public void setMonthOfLactationFrom(Integer monthOfLactationFrom)
    {
        this.monthOfLactationFrom = monthOfLactationFrom;
    }

    public Integer getMonthOfLactationTo()
    {
        return monthOfLactationTo;
    }

    public void setMonthOfLactationTo(Integer monthOfLactationTo)
    {
        this.monthOfLactationTo = monthOfLactationTo;
    }

    public Boolean getHealthy()
    {
        return healthy;
    }

    public void setHealthy(Boolean healthy)
    {
        this.healthy = healthy;
    }

    public Boolean getMenstruation()
    {
        return menstruation;
    }

    public void setMenstruation(Boolean menstruation)
    {
        this.menstruation = menstruation;
    }

    public ActivityLevel getPhysicalActivityLevel()
    {
        return physicalActivityLevel;
    }

    public void setPhysicalActivityLevel(ActivityLevel physicalActivityLevel)
    {
        this.physicalActivityLevel = physicalActivityLevel;
    }

    public BioAvailability getBioAvailability()
    {
        return bioAvailability;
    }

    public void setBioAvailability(BioAvailability bioAvailability)
    {
        this.bioAvailability = bioAvailability;
    }

    public String getRdaValue()
    {
        return rdaValue;
    }

    public void setRdaValue(String rdaValue)
    {
        this.rdaValue = rdaValue;
    }

    public String getRdaDelta()
    {
        return rdaDelta;
    }

    public void setRdaDelta(String rdaDelta)
    {
        this.rdaDelta = rdaDelta;
    }

    public RdaType getRdaType()
    {
        return rdaType;
    }

    public void setRdaType(RdaType rdaType)
    {
        this.rdaType = rdaType;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }

    /**
     * Created by phuctran93 on 3/3/2017.
     */
    public enum RdaType
    {
        BASIC, EXTRA, FACTOR, CALCULATED, UPPER_LIMIT
    }
}
