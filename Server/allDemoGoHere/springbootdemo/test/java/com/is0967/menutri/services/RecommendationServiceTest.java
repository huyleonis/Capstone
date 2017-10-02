package com.is0967.menutri.services;

import com.is0967.menutri.config.Config;
import com.is0967.menutri.dtos.BodyData;
import com.is0967.menutri.dtos.Recommendation;
import com.is0967.menutri.entities.Body;
import com.is0967.menutri.entities.BodyCondition;
import com.is0967.menutri.entities.Condition;
import com.is0967.menutri.entities.Formula;
import com.is0967.menutri.rda.calculator.RdaCalculatorService;
import com.is0967.menutri.rda.data.ActivityLevel;
import com.is0967.menutri.rda.data.BioAvailability;
import com.is0967.menutri.rda.data.BodyData.Builder;
import com.is0967.menutri.rda.data.BodyDataBuilder.Sample;
import com.is0967.menutri.rda.data.Gender;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.data.RdaResult;
import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by phuctran93 on 4/6/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ContextConfiguration(classes = Config.class)
public class RecommendationServiceTest
{

    public static final int INITIAL_CAPACITY = 100;
    public static final int FORMULA_CAPACITY = 100 * 100;
    private static final Logger logger = Logger.getLogger(RecommendationServiceTest.class);
    private static final Map<Long, NutrientType> oldNutrientData;
    private static final List<BodyCondition> BODY_CONDITIONS = new ArrayList<>();

    static {
        oldNutrientData = new HashMap<>();
        oldNutrientData.put(1L, NutrientType.WATER);
        oldNutrientData.put(2L, NutrientType.ENERGY);
        oldNutrientData.put(3L, NutrientType.PROTEIN);
        oldNutrientData.put(4L, NutrientType.FAT);
        oldNutrientData.put(5L, NutrientType.CARBOHYDRATE_AND_FIBER);
        oldNutrientData.put(6L, NutrientType.NON_STARCH_AND_FIBER);
        oldNutrientData.put(7L, NutrientType.CALCIUM);
        oldNutrientData.put(8L, NutrientType.IRON);
        oldNutrientData.put(9L, NutrientType.MAGNESIUM);
        oldNutrientData.put(11L, NutrientType.PHOSPHORUS);
        oldNutrientData.put(12L, NutrientType.POTASSIUM);
        oldNutrientData.put(13L, NutrientType.SODIUM);
        oldNutrientData.put(14L, NutrientType.ZINC);
        oldNutrientData.put(16L, NutrientType.SELENIUM);
        oldNutrientData.put(18L, NutrientType.VITAMIN_C);
        oldNutrientData.put(19L, NutrientType.THIAMIN);
        oldNutrientData.put(20L, NutrientType.RIBOFLAVIN);
        oldNutrientData.put(21L, NutrientType.NIACIN);
        oldNutrientData.put(23L, NutrientType.VITAMIN_B6);
        oldNutrientData.put(26L, NutrientType.VITAMIN_A);
        oldNutrientData.put(27L, NutrientType.VITAMIN_D);
        oldNutrientData.put(28L, NutrientType.VITAMIN_E);
        oldNutrientData.put(29L, NutrientType.VITAMIN_K);
        oldNutrientData.put(42L, NutrientType.FOLATE);
        oldNutrientData.put(40L, NutrientType.VITAMIN_B12);
        oldNutrientData.put(30L, NutrientType.HISTIDINE);
        oldNutrientData.put(31L, NutrientType.ISOLEUCINE);
        oldNutrientData.put(32L, NutrientType.LEUCINE);
        oldNutrientData.put(33L, NutrientType.LYSINE);
        oldNutrientData.put(34L, NutrientType.METHIONINE);
        oldNutrientData.put(35L, NutrientType.CYSTEINE);
        oldNutrientData.put(36L, NutrientType.THREONINE);
        oldNutrientData.put(37L, NutrientType.VALINE);
        oldNutrientData.put(41L, NutrientType.TRYPTOPHAN);
//        oldNutrientData.put(1L, NutrientType.WATER);

        Condition healthy = new Condition();
        healthy.setId(1);
        BodyCondition bc = new BodyCondition();
        bc.setCondition(healthy);
        BODY_CONDITIONS.add(bc);

        Condition menstruation = new Condition();
        menstruation.setId(4);
        BodyCondition bc2 = new BodyCondition();
        bc2.setCondition(menstruation);
        BODY_CONDITIONS.add(bc2);
    }

    private final List<Body> tmpBody = new ArrayList<>(INITIAL_CAPACITY);
    private final List<Condition> tmpCondition = new ArrayList<>(INITIAL_CAPACITY);
    private final List<BodyCondition> tmpBodyCondition = new ArrayList<>(INITIAL_CAPACITY);
    private final List<Formula> tmpFormula = new ArrayList<>(FORMULA_CAPACITY);
    @Rule
    public ErrorCollector collector = new ErrorCollector();
    @Autowired
    NutrientService nutrientService;
    @Autowired
    private RecommendationService service;
    @Autowired
    private BodyService bodyService;

    @BeforeClass
    @Transactional
    public static void populateDummyData()
    {
//        Queue<BodyData> bodies = BodyDataBuilder.Sample.random(INITIAL_CAPACITY);
//        bodies.forEach(b -> {
//            bodyService.save(b);
//            tmpBody.add(b);
//        });
//        Queue<Condition> conditions = ConditionBuilder.random(INITIAL_CAPACITY);
//        conditions.forEach(c -> {
//            bodyService.save(c);
//            tmpCondition.add(c);
//        });
//
//        for (int i = 0; i < INITIAL_CAPACITY; i++) {
//            int bodyIndex = new Random().nextInt(tmpBody.size());
//            int conditionIndex = new Random().nextInt(tmpCondition.size());
//            BodyCondition bc = bodyService.saveBodyCondition(
//                    tmpBody.get(bodyIndex).getId(),
//                    tmpCondition.get(conditionIndex).getId(),
//                    Math.random() > 0.5);
//            tmpBodyCondition.add(bc);
//        }
//
//        List<Nutrient> nutrientList = nutrientService.readAll();
//
//        Queue<Formula> formulas = FormulaBuilder.random(tmpBody, nutrientList, FORMULA_CAPACITY);
//
//        for (Formula f : formulas) {
//            bodyService.save(f);
//        }
    }

    @AfterClass
    @Transactional
    public static void restore()
    {
//        bodyService.deleteFormula(tmpFormula);
//        bodyService.deleteBodyCondition(tmpBodyCondition);
//        bodyService.deleteCondition(tmpCondition);
//        bodyService.deleteBody(tmpBody);
    }

    private static com.is0967.menutri.rda.data.BodyData convert(BodyData bodyData)
    {

        Period age = bodyData.getAge();
        Gender gender = convert(bodyData.getGender());
        ActivityLevel pal = convert(bodyData.getActivityLevel());
        BioAvailability bio = convert(bodyData.getBioAvailability());
        int mop = bodyData.getMonthOfPregnancy() != null
                ? bodyData.getMonthOfPregnancy()
                : 0;
        int mol = bodyData.getMonthOfLactation() != null
                ? bodyData.getMonthOfLactation()
                : 0;

        Builder builder = new com.is0967.menutri.rda.data.BodyData.Builder(age, gender, bodyData.getHeight(), bodyData.getWeight())
                .pregnancy(mop)
                .lactation(mol)
                .activityLevel(pal)
                .bioAvailability(bio);

        List<BodyCondition> bodyConditions = bodyData.getBodyCondition();
        if (bodyConditions != null) {
            for (BodyCondition bc : bodyConditions) {
                long condId = bc.getCondition().getId();
                if (condId == 1) {
                    builder.healthy(true);
                } else if (condId == 2) {
                    builder.hiv(true);
                } else if (condId == 3) {
                    builder.aids(true);
                } else if (condId == 4) {
                    builder.menstruating(true);
                }
            }
        }
        return builder.build();
    }

    private static BioAvailability convert(Body.BioAvailability bio)
    {
        if (bio == null) return null;
        switch (bio) {
            case HIGH:
                return BioAvailability.HIGH;
            case INTERMEDIATE:
                return BioAvailability.INTERMEDIATE;
            case LOW:
                return BioAvailability.LOW;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static ActivityLevel convert(Body.ActivityLevel pal)
    {
        if (pal == null) return null;
        switch (pal) {

            case LIGHT:
                return ActivityLevel.LIGHT;
            case MODERATE:
                return ActivityLevel.MODERATE;
            case HEAVY:
                return ActivityLevel.HEAVY;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static Gender convert(Body.Gender gender)
    {
        if (gender == null) return null;
        switch (gender) {
            case MALE:
                return Gender.MALE;
            case FEMALE:
                return Gender.FEMALE;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static Period convert(int ageInTotalMonths)
    {
        int year = ageInTotalMonths / 12;
        int month = ageInTotalMonths % 12;
        return Period.of(year, month, 0);
    }

    @Test
    public void testFEMALE_0() { test(Sample.FEMALE_0); }

    @Test
    public void testFEMALE_1() { test(Sample.FEMALE_1); }

    @Test
    public void testFEMALE_2() { test(Sample.FEMALE_2); }

    @Test
    public void testFEMALE_3() { test(Sample.FEMALE_3); }

    @Test
    public void testFEMALE_4() { test(Sample.FEMALE_4); }

    @Test
    public void testFEMALE_5() { test(Sample.FEMALE_5); }

    @Test
    public void testFEMALE_6() { test(Sample.FEMALE_6); }

    @Test
    public void testFEMALE_7() { test(Sample.FEMALE_7); }

    @Test
    public void testFEMALE_8() { test(Sample.FEMALE_8); }

    @Test
    public void testFEMALE_9() { test(Sample.FEMALE_9); }

    @Test
    public void testFEMALE_10() { test(Sample.FEMALE_10); }

    @Test
    public void testFEMALE_11() { test(Sample.FEMALE_11); }

    @Test
    public void testFEMALE_1_12() { test(Sample.FEMALE_1_12); }

    @Test
    public void testFEMALE_2_12() { test(Sample.FEMALE_2_12); }

    @Test
    public void testFEMALE_3_12() { test(Sample.FEMALE_3_12); }

    @Test
    public void testFEMALE_4_12() { test(Sample.FEMALE_4_12); }

    @Test
    public void testFEMALE_5_12() { test(Sample.FEMALE_5_12); }

    @Test
    public void testFEMALE_6_12() { test(Sample.FEMALE_6_12); }

    @Test
    public void testFEMALE_7_12() { test(Sample.FEMALE_7_12); }

    @Test
    public void testFEMALE_8_12() { test(Sample.FEMALE_8_12); }

    @Test
    public void testFEMALE_9_12() { test(Sample.FEMALE_9_12); }

    @Test
    public void testFEMALE_10_12() { test(Sample.FEMALE_10_12); }

    @Test
    public void testFEMALE_11_12() { test(Sample.FEMALE_11_12); }

    @Test
    public void testFEMALE_12_12() { test(Sample.FEMALE_12_12); }

    @Test
    public void testFEMALE_13_12() { test(Sample.FEMALE_13_12); }

    @Test
    public void testFEMALE_14_12() { test(Sample.FEMALE_14_12); }

    @Test
    public void testFEMALE_15_12() { test(Sample.FEMALE_15_12); }

    @Test
    public void testFEMALE_16_12() { test(Sample.FEMALE_16_12); }

    @Test
    public void testFEMALE_17_12() { test(Sample.FEMALE_17_12); }

    @Test
    public void testFEMALE_18_12() { test(Sample.FEMALE_18_12); }

    @Test
    public void testFEMALE_19_12() { test(Sample.FEMALE_19_12); }

    @Test
    public void testFEMALE_25_12() { test(Sample.FEMALE_25_12); }

    @Test
    public void testFEMALE_35_12() { test(Sample.FEMALE_35_12); }

    @Test
    public void testFEMALE_45_12() { test(Sample.FEMALE_45_12); }

    @Test
    public void testFEMALE_55_12() { test(Sample.FEMALE_55_12); }

    @Test
    public void testFEMALE_65_12() { test(Sample.FEMALE_65_12); }

    //
    @Test
    public void testFEMALE_25_12_MOP_1() { test(Sample.FEMALE_25_12_MOP_1); }

    //
    @Test
    public void testFEMALE_25_12_MOP_4() { test(Sample.FEMALE_25_12_MOP_4); }

    //
    @Test
    public void testFEMALE_25_12_MOP_7() { test(Sample.FEMALE_25_12_MOP_7); }

    //
    @Test
    public void testFEMALE_25_12_MOL_1() { test(Sample.FEMALE_25_12_MOL_1); }

    //
    @Test
    public void testFEMALE_25_12_MOL_7() { test(Sample.FEMALE_25_12_MOL_7); }

    @Test
    public void testMALE_0() { test(Sample.MALE_0); }

    @Test
    public void testMALE_1() { test(Sample.MALE_1); }

    @Test
    public void testMALE_2() { test(Sample.MALE_2); }

    @Test
    public void testMALE_3() { test(Sample.MALE_3); }

    @Test
    public void testMALE_4() { test(Sample.MALE_4); }

    @Test
    public void testMALE_5() { test(Sample.MALE_5); }

    @Test
    public void testMALE_6() { test(Sample.MALE_6); }

    @Test
    public void testMALE_7() { test(Sample.MALE_7); }

    @Test
    public void testMALE_8() { test(Sample.MALE_8); }

    @Test
    public void testMALE_9() { test(Sample.MALE_9); }

    @Test
    public void testMALE_10() { test(Sample.MALE_10); }

    @Test
    public void testMALE_11() { test(Sample.MALE_11); }

    @Test
    public void testMALE_1_12() { test(Sample.MALE_1_12); }

    @Test
    public void testMALE_2_12() { test(Sample.MALE_2_12); }

    @Test
    public void testMALE_3_12() { test(Sample.MALE_3_12); }

    @Test
    public void testMALE_4_12() { test(Sample.MALE_4_12); }

    @Test
    public void testMALE_5_12() { test(Sample.MALE_5_12); }

    @Test
    public void testMALE_6_12() { test(Sample.MALE_6_12); }

    @Test
    public void testMALE_7_12() { test(Sample.MALE_7_12); }

    @Test
    public void testMALE_8_12() { test(Sample.MALE_8_12); }

    @Test
    public void testMALE_9_12() { test(Sample.MALE_9_12); }

    @Test
    public void testMALE_10_12() { test(Sample.MALE_10_12); }

    @Test
    public void testMALE_11_12() { test(Sample.MALE_11_12); }

    @Test
    public void testMALE_12_12() { test(Sample.MALE_12_12); }

    @Test
    public void testMALE_13_12() { test(Sample.MALE_13_12); }

    @Test
    public void testMALE_14_12() { test(Sample.MALE_14_12); }

    @Test
    public void testMALE_15_12() { test(Sample.MALE_15_12); }

    @Test
    public void testMALE_16_12() { test(Sample.MALE_16_12); }

    @Test
    public void testMALE_17_12() { test(Sample.MALE_17_12); }

    @Test
    public void testMALE_18_12() { test(Sample.MALE_18_12); }

    @Test
    public void testMALE_19_12() { test(Sample.MALE_19_12); }

    @Test
    public void testMALE_25_12() { test(Sample.MALE_25_12); }

    @Test
    public void testMALE_35_12() { test(Sample.MALE_35_12); }

    @Test
    public void testMALE_45_12() { test(Sample.MALE_45_12); }

    @Test
    public void testMALE_55_12() { test(Sample.MALE_55_12); }

    @Test
    public void testMALE_65_12() { test(Sample.MALE_65_12); }

    // Energy: 558 Cal
    @Test
    public void testMALE_0_HIV() { test(Sample.MALE_0_HIV); }

    // Energy: 2297 Cal
    @Test
    public void testMALE_10_12_HIV() { test(Sample.MALE_10_12_HIV); }

    // Energy: 1160 Cal
    @Test
    public void testMALE_1_12_HIV() { test(Sample.MALE_1_12_HIV); }

    // Energy: 971 Cal/day
    @Test
    public void testMALE_11_AIDS() { test(Sample.MALE_11_AIDS); }

    // Energy: 2704 Cal
    @Test
    public void testFEMALE_18_12_AIDS() { test(Sample.FEMALE_18_12_AIDS); }

    // Energy: 3360 Cal
    @Test
    public void testMALE_35_12_AIDS() { test(Sample.MALE_35_12_AIDS); }

    // Energy: 2651 Cal
    @Test
    public void testMALE_65_12_AIDS() { test(Sample.MALE_65_12_AIDS); }

    // Energy: 698 Cal
    @Test
    public void testMALE_0_HIV_AIDS() { test(Sample.MALE_0_HIV_AIDS); }

    // Energy: 2871 Cal
    @Test
    public void testMALE_10_12_HIV_AIDS() { test(Sample.MALE_10_12_HIV_AIDS); }

    // Energy: 1450 Cal
    @Test
    public void testMALE_1_12_HIV_AIDS() { test(Sample.MALE_1_12_HIV_AIDS); }

    // Energy: 2916 Cal
    @Test
    public void testMALE_65_12_HIV_AIDS() { test(Sample.MALE_65_12_HIV_AIDS); }

    private void test(BodyData bodyData)
    {
        testCorrectness(bodyData);
    }

    private void testCorrectness(BodyData bodyData)
    {
        logger.debug(bodyData);
        // New implementation
        List<Recommendation> result = service.recommend(bodyData);
        Map<Long, Recommendation> resultMap = result.stream().collect(Collectors.toMap(Recommendation::getId, Function.identity()));

        // Old implementation
        com.is0967.menutri.rda.data.BodyData oldBodyData = convert(bodyData);
        RdaResult oldResult = RdaCalculatorService.INSTANCE.rda(oldBodyData);
        Map<Long, RdaResult> oldResultMap = flatten(oldResult);

        // Compare new vs. old
        for (RdaResult oldRda : oldResultMap.values()) {
            try {
                Recommendation r = resultMap.get(oldRda.getNutrientType().getId());
                Assert.assertNotNull("Missing RDA for " + oldRda.getNutrientType().name(), r);

                double rdaMin = BigDecimal.valueOf(r.getValue()).subtract(BigDecimal.valueOf(r.getDelta())).doubleValue();
                double rdaMax = BigDecimal.valueOf(r.getValue()).add(BigDecimal.valueOf(r.getDelta())).doubleValue();

                Assert.assertEquals(oldRda.getNutrientType().name(), oldRda.getRdaMin(), Double.valueOf(rdaMin), 1);
                Assert.assertEquals(oldRda.getNutrientType().name(), oldRda.getRdaMax(), Double.valueOf(rdaMax), 1);
            } catch (AssertionError e) {
                collector.addError(e);
            }
        }
    }

    @Test
    public void testUpperLimit()
    {
        for (BodyData bodyData :
                Sample.FEMALES) {
            List<Recommendation> rda = service.recommend(bodyData);
            Map<Long, Integer> result = rda.stream()
                    .filter(r -> r.getUpperLimit() != null)
                    .collect(Collectors.toMap(Recommendation::getId, Recommendation::getUpperLimit));
            System.out.println(result);
        }
    }

    // 1. 1m1s
    // 2. 0m60
    // 3. 1m1s
    // 1. old+new: 1m6s
    // after separate conditions from bodyGroup
    // 1. 1m28s
    // 2. 1m32s
    // 3. 1m29s
    // after optimize query
    // 1. 1m47s
    // 2. 1m28s
    // 3. 1m12s
    // after
    private void testNewPerformance(BodyData bodyData)
    {
        for (int i = 0; i < 100; i++) {
            List<Recommendation> result = service.recommend(bodyData);
            logger.debug(result);
        }
    }

    private void testInRamPerformance(BodyData bodyData)
    {
        for (int i = 0; i < 10_000; i++) {
            com.is0967.menutri.rda.data.BodyData oldBodyData = convert(bodyData);
            RdaResult oldResult = RdaCalculatorService.INSTANCE.rda(oldBodyData);
        }
    }

    private Map<Long, RdaResult> flatten(RdaResult oldResult)
    {
        Map<Long, RdaResult> flatMap = new HashMap<>();
        flatten(oldResult, flatMap);
        return flatMap;
    }

    private void flatten(RdaResult current, Map<Long, RdaResult> flatMap)
    {
        if (current.getNutrientType().getId() > 0) { flatMap.put(current.getNutrientType().getId(), current); }
        for (RdaResult n : current.getSubResult()) { flatten(n, flatMap); }
    }
}