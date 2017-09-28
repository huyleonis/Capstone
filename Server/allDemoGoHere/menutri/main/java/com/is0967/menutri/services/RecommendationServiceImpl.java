package com.is0967.menutri.services;

import com.is0967.menutri.dtos.BodyData;
import com.is0967.menutri.dtos.Recommendation;
import com.is0967.menutri.entities.Body;
import com.is0967.menutri.entities.Formula;
import com.is0967.menutri.entities.Formula.RdaType;
import com.is0967.menutri.entities.Nutrient;
import com.is0967.menutri.rda.expression.ExpressionTree;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by phuctran93 on 4/4/2017.
 */
@Service
@Transactional
public class RecommendationServiceImpl extends AbstractServiceImpl implements RecommendationService
{

    private static final Logger _logger = Logger.getLogger(RecommendationServiceImpl.class);

    private static Recommendation recommend(Nutrient nutrient, Iterable<Recommendation> evaluatedList)
    {
        Recommendation result;

        double basicValue = 1;
        double basicDelta = 1;
        double extraValue = 0;
        double extraDelta = 0;
        double factorValue = 1;
        double factorDelta = 1;
        Integer upperLimit = null;

        // recommendation = (basic + extra(s)) * factor
        for (Recommendation r : evaluatedList) {
            if (r == null) return null;
            _logger.trace(r);
            if (r.getType() == RdaType.CALCULATED) {
                basicValue = r.getValue();
                basicDelta = r.getDelta();
                factorValue = 1;
                factorDelta = 1;
                break;

            } else if (r.getType() == RdaType.BASIC) {
                basicValue *= r.getValue();
                basicDelta *= r.getDelta();

            } else if (r.getType() == RdaType.EXTRA) {
                extraValue += r.getValue();
                extraDelta += r.getDelta();

            } else if (r.getType() == RdaType.FACTOR) {
                factorValue *= r.getValue();
                factorDelta *= r.getDelta();

            } else if (r.getType() == RdaType.UPPER_LIMIT) {
                if (upperLimit != null && r.getUpperLimit() != null) {
                    upperLimit = Math.min(upperLimit, r.getUpperLimit()); // Increase the upper limit may harm other conditions/diseases.
                } else if (r.getUpperLimit() != null) {
                    upperLimit = r.getUpperLimit();
                }
            } else {
                _logger.error("Unknown Formula's type: " + r);
            }
        }
        double rdaValue = (basicValue + extraValue) * factorValue;
        double rdaDelta = (basicDelta + extraDelta) * factorDelta;

        result = new Recommendation();
        result.setId(nutrient.getId());
        result.setNutrient(nutrient);
        result.setValue(BigDecimal.valueOf(rdaValue).doubleValue());
        result.setDelta(BigDecimal.valueOf(rdaDelta).doubleValue());
        result.setUpperLimit(upperLimit);
        result.setType(RdaType.CALCULATED);
        return result;
    }

    private static List<Recommendation> evaluateFormula(Iterable<Formula> formulaList, Map<String, Double> cache)
    {
        List<Recommendation> result = new LinkedList<>();

        for (Formula f : formulaList) {
            _logger.trace(f);
            Recommendation recommendation = new Recommendation();
            recommendation.setId(f.getId());
            recommendation.setNutrient(f.getNutrient());
            recommendation.setType(f.getRdaType());

            ExpressionTree valueTree = new ExpressionTree.ExpressionTreeBuilder(f.getRdaValue(), 0).build();
            ExpressionTree deltaTree = new ExpressionTree.ExpressionTreeBuilder(f.getRdaDelta(), 0).build();
            if (valueTree.isExecutable(cache) && deltaTree.isExecutable(cache)) {
                double value = valueTree.execute(cache);
                double delta = deltaTree.execute(cache);
                if (f.getRdaType() == RdaType.UPPER_LIMIT
                        && f.getRdaValue() != null) { // double value will be zero in case of f.getRdaValue() == null.
                    recommendation.setUpperLimit((int) value);
                } else {
                    recommendation.setValue(value);
                    recommendation.setDelta(delta);
                }
                result.add(recommendation);
            } else {
                return null;
            }
        }
        return result;
    }

    private static Formula convert(BodyData bodyData)
    {
        int age = (int) bodyData.getAge().toTotalMonths();
        Formula entity = new Formula();
        entity.setAgeFrom(age);
        entity.setAgeToUnder(age);
        entity.setGender(bodyData.getGender());
        entity.setHeightFrom(bodyData.getHeight());
        entity.setHeightToUnder(bodyData.getHeight());
        entity.setWeightFrom(bodyData.getWeight());
        entity.setWeightToUnder(bodyData.getWeight());
        entity.setMonthOfPregnancyFrom(bodyData.getMonthOfPregnancy());
        entity.setMonthOfPregnancyTo(bodyData.getMonthOfPregnancy());
        entity.setMonthOfLactationFrom(bodyData.getMonthOfLactation());
        entity.setMonthOfLactationTo(bodyData.getMonthOfLactation());
        entity.setHealthy(bodyData.getHealthy());
        entity.setMenstruation(bodyData.getMenstruation());
        entity.setPhysicalActivityLevel(bodyData.getActivityLevel());
        entity.setBioAvailability(bodyData.getBioAvailability());
        return entity;
    }

    @Override
    public List<Recommendation> recommend(BodyData bodyData)
    {
        Objects.requireNonNull(bodyData);

        final Formula filter = this.convert(bodyData);

        Map<String, Double> variables = new HashMap<>();
        variables.put("cm", bodyData.getHeight());
        variables.put("kg", bodyData.getWeight());

        List<Formula> tempList = formulaRepo.find(null, filter, bodyData.getBodyCondition());
        Map<Boolean, Map<Nutrient, List<Formula>>> tempMap = tempList.stream()
                .collect(Collectors.partitioningBy(f -> f.getRdaType() != RdaType.UPPER_LIMIT,
                                                   Collectors.groupingBy(f -> f.getNutrient())));

        Map<Nutrient, List<Formula>> rdaByNutrient = tempMap.get(true);
        Map<Nutrient, List<Formula>> limitByNutrient = tempMap.get(false);

        Queue<Nutrient> pendingQueue = new LinkedList<>(rdaByNutrient.keySet());
        List<Recommendation> result = new ArrayList<>(rdaByNutrient.size());
        Map<Long, Recommendation> cache = new HashMap<>(rdaByNutrient.size());

        int latestSize;
        int currentSize;
        do {
            currentSize = pendingQueue.size();
            for (int i = 0; i < currentSize; i++) {

                // Calculate rda
                Nutrient nutrient = pendingQueue.remove();
                List<Formula> formulaList = rdaByNutrient.get(nutrient);

                Map<Body, List<Formula>> formulaByBody = formulaList.stream()
                        .collect(Collectors.groupingBy(f -> f.getBody()));

                List<Body> matchingBody = new ArrayList<>(formulaByBody.keySet());
                if (matchingBody.size() > 1) {
                    // Sort from the normal (no-condition) body to higher condition-coverage bodies.
                    matchingBody.sort(Comparator.comparingInt(b -> b.getBodyConditionList().size()));
                    Body bestMatch = matchingBody.get(matchingBody.size() - 1);

                    // Remove all lower condition-coverage bodies.
                    if (bestMatch.getDedicatedList().stream().anyMatch(d -> d.getNutrient().equals(nutrient))) {
                        matchingBody.subList(0, matchingBody.size() - 1).clear();
                    } else {
                        matchingBody.subList(1, matchingBody.size() - 1).clear();
                    }
                }

                Recommendation recommendation = null;
                for (Body body : matchingBody) {

                    List<Formula> evaluatingList = formulaByBody.get(body);

                    List<Recommendation> evaluatedList = evaluateFormula(evaluatingList, variables);

                    if (evaluatedList == null) {
                        // meet rda dependencies.
                        break;
                    }
                    if (recommendation != null) {
                        recommendation.setType(RdaType.BASIC);
                        evaluatedList.add(recommendation);
                    }

                    recommendation = recommend(nutrient, evaluatedList);
                }
                if (recommendation == null) {
                    // meet rda dependencies.
                    pendingQueue.add(nutrient);
                    continue;
                }
                result.add(recommendation);
                cache.put(nutrient.getId(), recommendation);

                if (nutrient.getKeyName() != null && !nutrient.getKeyName().isEmpty()) {
                    variables.put(nutrient.getKeyName(), recommendation.getValue());
                    variables.put(nutrient.getKeyName() + ".delta", recommendation.getDelta());
                }
            }
            latestSize = pendingQueue.size();
        } while (latestSize < currentSize);

        if (pendingQueue.isEmpty()) {

            // Calculate upper limit
            for (Entry<Nutrient, List<Formula>> entry : limitByNutrient.entrySet()) {
                Nutrient nutrient = entry.getKey();
                // Calculate upper limit
                List<Formula> upperLimitList = entry.getValue();
                if (upperLimitList == null) continue;
                Iterable<Recommendation> evaluatedUpperLimitList = evaluateFormula(upperLimitList, variables);
                if (evaluatedUpperLimitList == null) continue;
                Recommendation upperLimit = recommend(nutrient, evaluatedUpperLimitList);
                if (upperLimit == null || upperLimit.getUpperLimit() == null) continue;
                Recommendation rda = cache.get(nutrient.getId());
                if (rda == null) {
                    result.add(upperLimit);
                } else {
                    rda.setUpperLimit(upperLimit.getUpperLimit());
                }
            }

            _logger.trace("Calculate rda successful");
            _logger.trace("...body: " + bodyData);
            _logger.trace("...rda: " + result);
        } else {
            String cycle = "-> " + pendingQueue.stream()
                    .map(n -> n.getKeyName())
                    .reduce((n1, n2) -> n1 + " -> " + n2)
                    .get();
            _logger.warn("There is a cycle of dependencies of nutrients: " + cycle);
            _logger.warn("...when calculate rda for body: " + bodyData);
            result = Collections.EMPTY_LIST;
        }

        return result;
    }
}
