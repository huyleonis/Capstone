package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by phuctran93 on 4/7/2017.
 */
public interface ConditionRepo extends JpaRepository<Condition, Long>
{

    Condition findByName(String name);
}
