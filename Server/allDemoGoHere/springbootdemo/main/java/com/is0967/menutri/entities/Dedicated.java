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
 * Created by phuctran93 on 4/17/2017.
 */
@Entity(name = "dedicated")
public class Dedicated
{
    @Id
    @GeneratedValue(generator = "dedicated_id_seq", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "dedicated_id_seq", sequenceName = "dedicated_id_seq")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "body_id")
    private Body body;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "nutrient_id")
    private Nutrient nutrient;

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
}
