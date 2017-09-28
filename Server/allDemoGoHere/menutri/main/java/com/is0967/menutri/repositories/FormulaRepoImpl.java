package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.BodyCondition;
import com.is0967.menutri.entities.Formula;
import com.is0967.menutri.entities.Nutrient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

/**
 * Created by phuctran93 on 4/5/2017.
 */
class FormulaRepoImpl implements FormulaRepoCustom
{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Formula> find(Nutrient nutrient, Formula filter, Iterable<BodyCondition> bodyConditions)
    {
        Objects.requireNonNull(filter);

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<Formula> query = builder.createQuery(Formula.class);
        Root<Formula> formula = query.from(Formula.class);

        List<Predicate> predicateList = new ArrayList<>();

        if (nutrient != null) predicateList.add(builder.equal(formula.get("nutrient").get("id"), nutrient.getId()));

        //<editor-fold desc="match common body's info">
        // age: formula's ageFrom <= body's age < formula's ageTo
        predicateList.add(builder.or(formula.get("ageFrom").isNull(),
                                     builder.lessThanOrEqualTo(formula.get("ageFrom"), filter.getAgeFrom())));
        predicateList.add(builder.or(formula.get("ageToUnder").isNull(),
                                     builder.greaterThan(formula.get("ageToUnder"), filter.getAgeToUnder())));
        // gender
        predicateList.add(builder.or(formula.get("gender").isNull(),
                                     builder.equal(formula.get("gender"), filter.getGender())));
        // match height
        predicateList.add(builder.or(formula.get("heightFrom").isNull(),
                                     builder.lessThanOrEqualTo(formula.get("heightFrom"), filter.getHeightFrom())));
        predicateList.add(builder.or(formula.get("heightToUnder").isNull(),
                                     builder.greaterThan(formula.get("heightToUnder"), filter.getHeightToUnder())));
        // match weight
        predicateList.add(builder.or(formula.get("weightFrom").isNull(),
                                     builder.lessThanOrEqualTo(formula.get("weightFrom"), filter.getWeightFrom())));
        predicateList.add(builder.or(formula.get("weightToUnder").isNull(),
                                     builder.greaterThan(formula.get("weightToUnder"), filter.getWeightToUnder())));

        // Pregnancy, accept all formula(s) that: formula's MoP-from <= body's MoP <= formula's MoP-to
        // formula's MoP-from
        Predicate[] orConditionsMoPregnancyFrom = new Predicate[2];
        orConditionsMoPregnancyFrom[0] = formula.get("monthOfPregnancyFrom").isNull(); // 1. if formula's MoP-from is null, then accept all body's MoP
        if (filter.getMonthOfPregnancyFrom() == null || filter.getMonthOfPregnancyFrom() == 0) {
            orConditionsMoPregnancyFrom[1] = builder.equal(formula.get("monthOfPregnancyFrom"), 0); // 2. if body's MoP is null or zero, then accept formula(s) in step 1. plus formula(s) that have MoP-from equal zero.
        } else {
            orConditionsMoPregnancyFrom[1] = builder.lessThanOrEqualTo(formula.get("monthOfPregnancyFrom"), filter.getMonthOfPregnancyFrom()); // 3. Or if step 1. and 2. fail, accept only formula(s) that have MoP-from less than or equal body's MoP.
        }
        Predicate orEdMoPregnancyFrom = builder.or(orConditionsMoPregnancyFrom); // build or-ed conditions.
        predicateList.add(orEdMoPregnancyFrom);
        // formula's MoP-to
        Predicate[] orConditionsMoPregnancyTo = new Predicate[2];
        orConditionsMoPregnancyTo[0] = formula.get("monthOfPregnancyTo").isNull();
        if (filter.getMonthOfPregnancyTo() == null || filter.getMonthOfPregnancyTo() == 0) {
            orConditionsMoPregnancyTo[1] = builder.equal(formula.get("monthOfPregnancyTo"), 0);
        } else {
            orConditionsMoPregnancyTo[1] = builder.greaterThanOrEqualTo(formula.get("monthOfPregnancyTo"), filter.getMonthOfPregnancyTo());
        }
        Predicate orEdMoPregnancyTo = builder.or(orConditionsMoPregnancyTo);
        predicateList.add(orEdMoPregnancyTo);

        // Lactation, accept all formula(s) that: formula's MoL-from <= body's MoL <= formula's MoL-to
        // formula's MoL-from
        Predicate[] orConditionMoLactationFrom = new Predicate[2];
        orConditionMoLactationFrom[0] = formula.get("monthOfLactationFrom").isNull();
        if (filter.getMonthOfLactationFrom() == null || filter.getMonthOfLactationFrom() == 0) {
            orConditionMoLactationFrom[1] = builder.equal(formula.get("monthOfLactationFrom"), 0);
        } else {
            orConditionMoLactationFrom[1] = builder.lessThanOrEqualTo(formula.get("monthOfLactationFrom"), filter.getMonthOfLactationFrom());
        }
        Predicate orEdMoLactationFrom = builder.or(orConditionMoLactationFrom);
        predicateList.add(orEdMoLactationFrom);
        // formula's MoL-to
        Predicate[] orConditionsMoLactationTo = new Predicate[2];
        orConditionsMoLactationTo[0] = formula.get("monthOfLactationTo").isNull();
        if (filter.getMonthOfLactationTo() == null || filter.getMonthOfLactationTo() == 0) {
            orConditionsMoLactationTo[1] = builder.equal(formula.get("monthOfLactationTo"), 0);
        } else {
            orConditionsMoLactationTo[1] = builder.greaterThanOrEqualTo(formula.get("monthOfLactationTo"), filter.getMonthOfLactationTo());
        }
        Predicate orEdMoLactationTo = builder.or(orConditionsMoLactationTo);
        predicateList.add(orEdMoLactationTo);

        // healthy
        predicateList.add(builder.or(formula.get("healthy").isNull(),
                                     builder.equal(formula.get("healthy"), filter.getHealthy())));
        // menstruation
        predicateList.add(builder.or(formula.get("menstruation").isNull(),
                                     builder.equal(formula.get("menstruation"), filter.getMenstruation())));

        // Physical activity level
        predicateList.add(builder.or(formula.get("physicalActivityLevel").isNull(),
                                     builder.equal(formula.get("physicalActivityLevel"), filter.getPhysicalActivityLevel())));
        // Bio availability
        predicateList.add(builder.or(formula.get("bioAvailability").isNull(),
                                     builder.equal(formula.get("bioAvailability"), filter.getBioAvailability())));
        //</editor-fold>

        //<editor-fold desc="match body's conditions">
        Subquery<BodyCondition> bcQuery = query.subquery(BodyCondition.class);
        Root<BodyCondition> bcRoot = bcQuery.from(BodyCondition.class);
        List<Predicate> bcPredicationList = new ArrayList<>();
        bcPredicationList.add(builder.equal(bcRoot.get("body").get("id"), formula.get("body").get("id")));
        if (bodyConditions != null && bodyConditions.iterator().hasNext()) {
            for (BodyCondition bc : bodyConditions) {
                bcPredicationList.add(builder.notEqual(bcRoot.get("condition").get("id"), bc.getCondition().getId()));
            }
        }
        Predicate bcPredicate = builder.not(
                // TODO: performance's bottle neck, DBMS must select all the rows in table to find out it is existed or not
                builder.exists(bcQuery.select(bcRoot).where(
                        bcPredicationList.toArray(new Predicate[bcPredicationList.size()]))));

        predicateList.add(bcPredicate);
        //</editor-fold>

        return entityManager.createQuery(
                query.select(formula).where(predicateList.toArray(new Predicate[predicateList.size()]))
        ).getResultList();
    }
}
