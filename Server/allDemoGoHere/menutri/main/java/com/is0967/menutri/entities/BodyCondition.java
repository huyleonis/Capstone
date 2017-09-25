package com.is0967.menutri.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * Created by phuctran93 on 4/7/2017.
 */
@Entity(name = "body_condition")
public class BodyCondition
{

    @Id
    @GeneratedValue(generator = "body_condition_id_seq", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "body_condition_id_seq", sequenceName = "body_condition_id_seq")
    private long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "body_id")
    private Body body;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "condition_id")
    private Condition condition;

    @Override
    public String toString()
    {
        long bodyId = body != null ? body.getId() : 0;
        long conditionId = condition != null ? condition.getId() : 0;
        return "BodyCondition{" +
                "id=" + id +
                ", body=" + bodyId +
                ", condition=" + conditionId +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BodyCondition)) return false;

        BodyCondition that = (BodyCondition) o;

        if (!body.equals(that.body)) return false;
        return condition.equals(that.condition);
    }

    @Override
    public int hashCode()
    {
        int result = body.hashCode();
        result = 31 * result + condition.hashCode();
        return result;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
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

    public Condition getCondition()
    {
        return condition;
    }

    public void setCondition(Condition condition)
    {
        this.condition = condition;
    }

}
