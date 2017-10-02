package com.is0967.menutri.dtos;

import com.is0967.menutri.entities.Body.ActivityLevel;
import com.is0967.menutri.entities.Body.BioAvailability;
import com.is0967.menutri.entities.Body.Gender;
import com.is0967.menutri.entities.BodyCondition;
import com.is0967.menutri.entities.Condition;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phuctran93 on 4/10/2017.
 */
public class BodyData
{

    private Period age;
    private Gender gender;
    private Double height;
    private Double weight;
    private Integer monthOfPregnancy;
    private Integer monthOfLactation;
    private Boolean menstruation;
    private Boolean healthy;
    private Boolean hiv;
    private Boolean aids;
    private ActivityLevel activityLevel;
    private BioAvailability bioAvailability;

    public BodyData()
    {
        this.healthy = false;
        this.menstruation = false;
        this.activityLevel = ActivityLevel.MODERATE;
        this.bioAvailability = BioAvailability.INTERMEDIATE;
    }

    public List<BodyCondition> getBodyCondition()
    {
        List<BodyCondition> bodyConditionList = new ArrayList<>();
        if (hiv != null) {
            Condition condition = new Condition();
            condition.setId(1);
            BodyCondition bodyCondition = new BodyCondition();
            bodyCondition.setCondition(condition);
            bodyConditionList.add(bodyCondition);
        }
        if (aids != null) {
            Condition condition = new Condition();
            condition.setId(2);
            BodyCondition bodyCondition = new BodyCondition();
            bodyCondition.setCondition(condition);
            bodyConditionList.add(bodyCondition);
        }
        return bodyConditionList;
    }

    @Override
    public String toString()
    {
        return "BodyData{" +
                "\n    age=" + age +
                "\n    gender=" + gender +
                "\n    height=" + height +
                "\n    weight=" + weight +
                "\n    monthOfPregnancy=" + monthOfPregnancy +
                "\n    monthOfLactation=" + monthOfLactation +
                "\n    menstruation=" + menstruation +
                "\n    healthy=" + healthy +
                "\n    hiv=" + hiv +
                "\n    aids=" + aids +
                "\n    activityLevel=" + activityLevel +
                "\n    bioAvailability=" + bioAvailability +
                "\n}";
    }

    public Period getAge()
    {
        return age;
    }

    public void setAge(Period age)
    {
        this.age = age;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public Double getHeight()
    {
        return height;
    }

    public void setHeight(Double height)
    {
        this.height = height;
    }

    public Double getWeight()
    {
        return weight;
    }

    public void setWeight(Double weight)
    {
        this.weight = weight;
    }

    public Integer getMonthOfPregnancy()
    {
        return monthOfPregnancy;
    }

    public void setMonthOfPregnancy(Integer monthOfPregnancy)
    {
        this.monthOfPregnancy = monthOfPregnancy;
    }

    public Integer getMonthOfLactation()
    {
        return monthOfLactation;
    }

    public void setMonthOfLactation(Integer monthOfLactation)
    {
        this.monthOfLactation = monthOfLactation;
    }

    public Boolean getMenstruation()
    {
        return menstruation;
    }

    public void setMenstruation(Boolean menstruation)
    {
        this.menstruation = menstruation;
    }

    public Boolean getHealthy()
    {
        return healthy;
    }

    public void setHealthy(Boolean healthy)
    {
        this.healthy = healthy;
    }

    public Boolean getHiv()
    {
        return hiv;
    }

    public void setHiv(Boolean hiv)
    {
        this.hiv = hiv;
    }

    public Boolean getAids()
    {
        return aids;
    }

    public void setAids(Boolean aids)
    {
        this.aids = aids;
    }

    public ActivityLevel getActivityLevel()
    {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel)
    {
        this.activityLevel = activityLevel;
    }

    public BioAvailability getBioAvailability()
    {
        return bioAvailability;
    }

    public void setBioAvailability(BioAvailability bioAvailability)
    {
        this.bioAvailability = bioAvailability;
    }
}
