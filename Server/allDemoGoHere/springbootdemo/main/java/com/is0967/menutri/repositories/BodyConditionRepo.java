package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.BodyCondition;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by phuctran93 on 4/7/2017.
 */
public interface BodyConditionRepo extends JpaRepository<BodyCondition, Long>
{

    BodyCondition findByBody_IdAndCondition_Id(long bodyId, long conditionId);

    Iterable<BodyCondition> findByBody_Id(long bodyId);
}
