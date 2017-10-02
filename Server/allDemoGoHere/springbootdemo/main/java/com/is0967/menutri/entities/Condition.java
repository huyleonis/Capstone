package com.is0967.menutri.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Created by phuctran93 on 4/7/2017.
 */
@Entity(name = "conditions")
public class Condition
{

    @Id
    @GeneratedValue(generator = "conditions_id_seq", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "conditions_id_seq", sequenceName = "conditions_id_seq")
    private long id;
    private String name;
    private String description;

    @Override
    public String toString()
    {
        return "Condition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Condition)) return false;

        Condition condition = (Condition) o;

        return name.equals(condition.name);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
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
}
