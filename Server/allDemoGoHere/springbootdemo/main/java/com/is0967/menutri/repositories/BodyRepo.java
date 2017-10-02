package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.Body;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by phuctran93 on 4/4/2017.
 */
public interface BodyRepo extends JpaRepository<Body, Long>
{

    @Modifying
    @Query("DELETE FROM bodies AS b WHERE b.id IN ?1")
    int deleteById(Iterable<Long> ids);

    @Modifying
    @Query("DELETE FROM body_condition AS bc WHERE bc.body.id = ?1 AND bc.condition.id = ?2")
    void removeBodyCondition(long bodyId, long conditionId);

    @Modifying
    @Query("DELETE from body_condition AS bc WHERE bc.body.id = ?1")
    void removeAllConditionForBody(long bodyId);

    @Modifying
    @Query("DELETE FROM conditions AS c WHERE c.id IN ?1 AND NOT EXISTS (SELECT bc.id FROM body_condition AS bc WHERE bc.condition.id = c.id)")
    void removalUnusedConditions(Iterable<Long> conditionIds);
}
