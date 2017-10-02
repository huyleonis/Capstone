package com.is0967.menutri.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 * Created by phuctran93 on 4/3/2017.
 */
@Entity(name = "bodies")
public class Body
{

    @Id
    @GeneratedValue(generator = "bodies_id_seq", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "bodies_id_seq", sequenceName = "bodies_id_seq")
    private Long id;
    private String name;
    private String description;
    private String reference;
    @OneToMany(mappedBy = "body")
    private List<BodyCondition> bodyConditionList;
    @OneToMany(mappedBy = "body")
    private List<Dedicated> dedicatedList;

    @Override
    public String toString()
    {
        return "Body{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Body)) return false;

        Body body = (Body) o;

        return name.equals(body.name);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public List<BodyCondition> getBodyConditionList()
    {
        return bodyConditionList;
    }

    public void setBodyConditionList(List<BodyCondition> bodyConditionList)
    {
        this.bodyConditionList = bodyConditionList;
    }

    public List<Dedicated> getDedicatedList()
    {
        return dedicatedList;
    }

    public void setDedicatedList(List<Dedicated> dedicatedList)
    {
        this.dedicatedList = dedicatedList;
    }

    /**
     * Created by phuctran93 on 2/28/2017.
     */
    public enum Gender
    {
        MALE, FEMALE
    }

    /**
     * Use for execute energy
     */
    public enum ActivityLevel
    {
        LIGHT, MODERATE, HEAVY
    }

    /**
     * Created by phuctran93 on 3/8/2017.
     */
    public enum BioAvailability
    {
        /**
         * Simple, monotonous diet based on cereals and plant sources,
         * with negligible amount of meat, fish or ascorbic acid.
         */
        LOW,

        /**
         * Diet containing mainly cereals, but includes some animal sources and
         * ascorbic acid.
         */
        INTERMEDIATE,

        /**
         * Diversified diet containing generous amount of meat, poultry, fish and/or
         * foods rich in ascorbic acid.
         */
        HIGH
    }
}
