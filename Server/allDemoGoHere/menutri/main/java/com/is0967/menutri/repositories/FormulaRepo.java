package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by phuctran93 on 4/4/2017.
 */
public interface FormulaRepo extends JpaRepository<Formula, Long>, FormulaRepoCustom
{

    Iterable<Formula> findByBody_Id(long bodyId);

    Iterable<Formula> findByBody_IdIn(Iterable<Long> bodyIdList);

    @Modifying
    @Query("UPDATE formulas AS f SET f.body.id = ?1 WHERE f.body.id IN ?2")
    void updateBody(long newBodyId, Iterable<Long> oldBodyId);

//    @Modifying
//    @Query("SELECT formulas FROM formulas AS f WHERE "
//            + "f.body.ageFrom IS NULL OR f.body.ageFrom < ?1.ageFrom")
//    Iterable<Formula> smartMatches(Body body, Iterable<BodyCondition> conditions, Nutrient nutrient);
}
