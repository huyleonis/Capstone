package com.is0967.menutri.services;

import com.is0967.menutri.entities.Body;
import com.is0967.menutri.entities.BodyCondition;
import com.is0967.menutri.entities.Condition;
import com.is0967.menutri.entities.Formula;
import com.is0967.menutri.entities.Formula.RdaType;
import com.is0967.menutri.entities.Nutrient;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by phuctran93 on 4/7/2017.
 */
@Service
@Transactional
public class BodyServiceImpl extends AbstractServiceImpl implements BodyService
{

    private static final Logger logger = Logger.getLogger(BodyServiceImpl.class);

    @Override
    public Body save(Body body)
    {
        return bodyRepo.save(body);
    }

    @Override
    public Condition save(Condition condition)
    {
        return conditionRepo.save(condition);
    }

    @Override
    public Condition saveCondition(String name, String description)
    {
        Condition condition = conditionRepo.findByName(name);

        condition.setName(name);
        condition.setDescription(description);

        return conditionRepo.save(condition);
    }

    @Override
    public BodyCondition save(BodyCondition bodyCondition)
    {
        return bodyConditionRepo.save(bodyCondition);
    }

    @Override
    public BodyCondition saveBodyCondition(long bodyId, long conditionId, boolean being)
    {
        Body body = bodyRepo.findOne(bodyId);
        if (body == null) throw new EntityNotFoundException("No body has id = " + bodyId);

        Condition condition = conditionRepo.findOne(conditionId);
        if (condition == null) throw new EntityNotFoundException("No condition has id = " + conditionId);

        BodyCondition bodyCondition = bodyConditionRepo.findByBody_IdAndCondition_Id(bodyId, condition.getId());
        if (bodyCondition == null) bodyCondition = new BodyCondition();

        bodyCondition.setBody(body);
        bodyCondition.setCondition(condition);

        return bodyConditionRepo.save(bodyCondition);
    }

    @Override
    public Formula save(Formula formula)
    {
        return formulaRepo.save(formula);
    }

    @Override
    public Formula saveFormula(long bodyId, long nutrientId, String value, String delta, Integer upperLimit,
            RdaType rdaType, String description, String reference)
    {
        Body body = bodyRepo.findOne(bodyId);
        if (body == null) throw new EntityNotFoundException("No body has id = " + bodyId);

        Nutrient nutrient = nutrientRepo.findOne(nutrientId);
        if (nutrient == null) throw new EntityNotFoundException("No nutrient has id = " + nutrientId);

        Formula formula = new Formula();
        formula.setBody(body);
        formula.setNutrient(nutrient);
        formula.setRdaValue(value);
        formula.setDescription(delta);
        formula.setRdaType(rdaType);
        formula.setDescription(description);
        formula.setReference(reference);

        return formulaRepo.save(formula);
    }

    @Override
    public void deleteFormula(Formula formula)
    {
        formulaRepo.delete(formula);
    }

    @Override
    public void deleteFormula(Iterable<Formula> formulas)
    {
        formulaRepo.delete(formulas);
    }

    @Override
    public void deleteBodyCondition(BodyCondition bodyCondition)
    {
        bodyConditionRepo.delete(bodyCondition);
    }

    @Override
    public void deleteBodyCondition(Iterable<BodyCondition> bodyConditions)
    {
        bodyConditionRepo.delete(bodyConditions);
    }

    @Override
    public void deleteCondition(Condition condition)
    {
        conditionRepo.delete(condition);
    }

    @Override
    public void deleteCondition(Iterable<Condition> conditions)
    {
        conditionRepo.delete(conditions);
    }

    @Override
    public void deleteBody(Body body)
    {
        bodyRepo.delete(body);
    }

    @Override
    public void deleteBody(Iterable<Body> bodies)
    {
        bodyRepo.delete(bodies);
    }

    @Override
    public void removeBodyCondition(long bodyId, long conditionId)
    {
        bodyRepo.removeBodyCondition(bodyId, conditionId);
    }

    @Override
    public void clearConditionsForBody(long bodyId)
    {
        bodyRepo.removeAllConditionForBody(bodyId);
    }

    @Override
    public void removeUnusedBodyCondition(Iterable<Long> conditionIds)
    {
        bodyRepo.removalUnusedConditions(conditionIds);
    }

    @Override
    public Iterable<BodyCondition> findBodyCondition(long bodyId)
    {
        return bodyConditionRepo.findByBody_Id(bodyId);
    }

    @Override
    public Iterable<Body> findAllBodyHasAtLeastOneCondition()
    {
        return null;
    }

}
