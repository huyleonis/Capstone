package com.is0967.menutri.services;

import com.is0967.menutri.entities.Body;
import com.is0967.menutri.entities.BodyCondition;
import com.is0967.menutri.entities.Condition;
import com.is0967.menutri.entities.Formula;
import com.is0967.menutri.entities.Formula.RdaType;

/**
 * Created by phuctran93 on 4/7/2017.
 */
public interface BodyService
{

    Body save(Body body);

    Condition save(Condition condition);

    Condition saveCondition(String name, String description);

    BodyCondition save(BodyCondition bodyCondition);

    BodyCondition saveBodyCondition(long bodyId, long conditionId, boolean being);

    Formula save(Formula formula);

    Formula saveFormula(long bodyId, long nutrientId, String value, String delta, Integer upperLimit, RdaType rdaType,
            String description, String reference);

    void deleteFormula(Formula formula);

    void deleteFormula(Iterable<Formula> formulas);

    void deleteBodyCondition(BodyCondition bodyCondition);

    void deleteBodyCondition(Iterable<BodyCondition> bodyConditions);

    void deleteCondition(Condition condition);

    void deleteCondition(Iterable<Condition> conditions);

    void deleteBody(Body body);

    void deleteBody(Iterable<Body> bodies);

    void removeBodyCondition(long bodyId, long conditionId);

    void clearConditionsForBody(long bodyId);

    void removeUnusedBodyCondition(Iterable<Long> conditionIds);

    Iterable<BodyCondition> findBodyCondition(long bodyId);

    Iterable<Body> findAllBodyHasAtLeastOneCondition();
}
