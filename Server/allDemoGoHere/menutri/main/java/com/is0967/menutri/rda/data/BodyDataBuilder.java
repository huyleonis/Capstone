package com.is0967.menutri.rda.data;

import static com.is0967.menutri.entities.Body.Gender.FEMALE;
import static com.is0967.menutri.entities.Body.Gender.MALE;

import com.is0967.menutri.entities.Body.ActivityLevel;
import com.is0967.menutri.entities.Body.BioAvailability;
import com.is0967.menutri.entities.Body.Gender;
import com.is0967.menutri.dtos.BodyData;
import java.time.Period;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by phuctran93 on 4/3/2017.
 */
public class BodyDataBuilder
{

    private BodyData bodyData = new BodyData();

    public BodyDataBuilder()
    {

    }

    public BodyDataBuilder(Integer age, Gender gender, Double weight, Integer monthOfPregnancy,
            Integer monthOfLactation,
            Boolean menstruation, Boolean healthy, Boolean hiv, Boolean aids, ActivityLevel pal, BioAvailability bio)
    {
        bodyData.setAge(Period.ofMonths(age));
        bodyData.setGender(gender);
        bodyData.setWeight(weight);
        bodyData.setMonthOfPregnancy(monthOfPregnancy);
        bodyData.setMonthOfLactation(monthOfLactation);
        bodyData.setMenstruation(menstruation);
        bodyData.setHealthy(healthy);
        bodyData.setHiv(hiv);
        bodyData.setAids(aids);
        bodyData.setActivityLevel(pal);
        bodyData.setBioAvailability(bio);

    }

    public BodyDataBuilder(Integer age, Gender gender, Double height, Double weight)
    {
        bodyData.setAge(Period.ofMonths(age).normalized());
        bodyData.setGender(gender);
        bodyData.setHeight(height);
        bodyData.setWeight(weight);
    }

    public BodyDataBuilder gender(Gender gender)
    {
        this.bodyData.setGender(gender);
        return this;
    }

    public BodyDataBuilder pregnancy(int fromMonth, int toMonth)
    {
        if (fromMonth > toMonth) {
            throw new IllegalArgumentException("Invalid month range.");
        }

        this.bodyData.setMonthOfPregnancy(toMonth);
        return this;
    }

    public BodyDataBuilder lactation(int fromMonth, int toMonth)
    {
        if (fromMonth > toMonth) {
            throw new IllegalArgumentException("Invalid month range.");
        }
        this.bodyData.setMonthOfLactation(toMonth);
        return this;
    }

    public BodyDataBuilder pal(ActivityLevel pal)
    {
        this.bodyData.setActivityLevel(pal);
        return this;
    }

    public BodyDataBuilder bio(BioAvailability bio)
    {
        this.bodyData.setBioAvailability(bio);
        return this;
    }

    public BodyData build()
    {
        return this.bodyData;
    }

    public static class Sample
    {

        public static final BodyData FEMALE_0 = new BodyDataBuilder(0, FEMALE, 53.6872d, 4.1873d).build();
        public static final BodyData FEMALE_1 = new BodyDataBuilder(1, FEMALE, 57.0673d, 5.1282d).build();
        public static final BodyData FEMALE_2 = new BodyDataBuilder(2, FEMALE, 59.8029d, 5.8458d).build();
        public static final BodyData FEMALE_3 = new BodyDataBuilder(3, FEMALE, 62.0899d, 6.4237d).build();
        public static final BodyData FEMALE_4 = new BodyDataBuilder(4, FEMALE, 64.0301d, 6.8985d).build();
        public static final BodyData FEMALE_5 = new BodyDataBuilder(5, FEMALE, 65.7311d, 7.297d).build();
        public static final BodyData FEMALE_6 = new BodyDataBuilder(6, FEMALE, 67.2873d, 7.6422d).build();
        public static final BodyData FEMALE_7 = new BodyDataBuilder(7, FEMALE, 68.7498d, 7.9487d).build();
        public static final BodyData FEMALE_8 = new BodyDataBuilder(8, FEMALE, 70.1435d, 8.2254d).build();
        public static final BodyData FEMALE_9 = new BodyDataBuilder(9, FEMALE, 71.4818d, 8.48d).build();
        public static final BodyData FEMALE_10 = new BodyDataBuilder(10, FEMALE, 72.771d, 8.7192d).build();
        public static final BodyData FEMALE_11 = new BodyDataBuilder(11, FEMALE, 74.015d, 8.9481d).build();
        public static final BodyData FEMALE_1_12 = new BodyDataBuilder(
                1 * 12, FEMALE, 85.7153d, 11.4775d).build();
        public static final BodyData FEMALE_2_12 = new BodyDataBuilder(
                2 * 12, FEMALE, 95.0515d, 13.8503d).build();
        public static final BodyData FEMALE_3_12 = new BodyDataBuilder(
                3 * 12, FEMALE, 102.7312d, 16.0697d).build();
        public static final BodyData FEMALE_4_12 = new BodyDataBuilder(
                4 * 12, FEMALE, 109.4233d, 18.2193d).build();
        public static final BodyData FEMALE_5_12 = new BodyDataBuilder(
                5 * 12, FEMALE, 115.1244d, 20.1639d).build();
        public static final BodyData FEMALE_6_12 = new BodyDataBuilder(
                6 * 12, FEMALE, 120.8105d, 22.374d).build();
        public static final BodyData FEMALE_7_12 = new BodyDataBuilder(
                7 * 12, FEMALE, 126.5558d, 25.0262d).build();
        public static final BodyData FEMALE_8_12 = new BodyDataBuilder(
                8 * 12, FEMALE, 132.4944d, 28.204d).build();
        public static final BodyData FEMALE_9_12 = new BodyDataBuilder(
                9 * 12, FEMALE, 138.6363d, 31.8578d).build();
        public static final BodyData FEMALE_10_12 = new BodyDataBuilder(
                10 * 12, FEMALE, 144.9929d, 32d).build();
        public static final BodyData FEMALE_11_12 = new BodyDataBuilder(
                11 * 12, FEMALE, 151.2327d, 34d).build();
        public static final BodyData FEMALE_12_12 = new BodyDataBuilder(
                12 * 12, FEMALE, 156.3748d, 39d).build();
        public static final BodyData FEMALE_13_12 = new BodyDataBuilder(
                13 * 12, FEMALE, 159.789d, 44d).build();
        public static final BodyData FEMALE_14_12 = new BodyDataBuilder(
                14 * 12, FEMALE, 161.6692d, 47d).build();
        public static final BodyData FEMALE_15_12 = new BodyDataBuilder(
                15 * 12, FEMALE, 162.5156d, 50d).build();
        public static final BodyData FEMALE_16_12 = new BodyDataBuilder(
                16 * 12, FEMALE, 162.8545d, 53d).build();
        public static final BodyData FEMALE_17_12 = new BodyDataBuilder(
                17 * 12, FEMALE, 163.0595d, 56d).build();

        public static final BodyData MALE_0 = new BodyDataBuilder(0, MALE, 54.7244d, 4.4709d).build();
        public static final BodyData MALE_1 = new BodyDataBuilder(1, MALE, 58.4249d, 5.5675d).build();
        public static final BodyData MALE_2 = new BodyDataBuilder(2, MALE, 61.4292d, 6.3762d).build();
        public static final BodyData MALE_3 = new BodyDataBuilder(3, MALE, 63.886d, 7.0023d).build();
        public static final BodyData MALE_4 = new BodyDataBuilder(4, MALE, 65.9026d, 7.5105d).build();
        public static final BodyData MALE_5 = new BodyDataBuilder(5, MALE, 67.6236d, 7.934d).build();
        public static final BodyData MALE_6 = new BodyDataBuilder(6, MALE, 69.1645d, 8.297d).build();
        public static final BodyData MALE_7 = new BodyDataBuilder(7, MALE, 70.5994d, 8.6151d).build();
        public static final BodyData MALE_8 = new BodyDataBuilder(8, MALE, 71.9687d, 8.9014d).build();
        public static final BodyData MALE_9 = new BodyDataBuilder(9, MALE, 73.2812d, 9.1649d).build();
        public static final BodyData MALE_10 = new BodyDataBuilder(10, MALE, 74.5388d, 9.4122d).build();
        public static final BodyData MALE_11 = new BodyDataBuilder(11, MALE, 75.7488d, 9.6479d).build();
        public static final BodyData MALE_1_12 = new BodyDataBuilder(
                1 * 12, MALE, 87.8161d, 12.1515d).build();
        public static final BodyData MALE_2_12 = new BodyDataBuilder(
                2 * 12, MALE, 96.0835d, 14.3429d).build();
        public static final BodyData MALE_3_12 = new BodyDataBuilder(
                3 * 12, MALE, 103.3273d, 16.3489d).build();
        public static final BodyData MALE_4_12 = new BodyDataBuilder(
                4 * 12, MALE, 109.9638d, 18.3366d).build();
        public static final BodyData MALE_5_12 = new BodyDataBuilder(
                5 * 12, MALE, 115.9509d, 20.5137d).build();
        public static final BodyData MALE_6_12 = new BodyDataBuilder(
                6 * 12, MALE, 121.7338d, 22.8915d).build();
        public static final BodyData MALE_7_12 = new BodyDataBuilder(
                7 * 12, MALE, 127.2651d, 25.4163d).build();
        public static final BodyData MALE_8_12 = new BodyDataBuilder(
                8 * 12, MALE, 132.5652d, 28.1092d).build();
        public static final BodyData MALE_9_12 = new BodyDataBuilder(
                9 * 12, MALE, 137.7795d, 31.1586d).build();
        public static final BodyData MALE_10_12 = new BodyDataBuilder(
                10 * 12, MALE, 143.1126d, 32d).build();
        public static final BodyData MALE_11_12 = new BodyDataBuilder(
                11 * 12, MALE, 149.0807d, 34d).build();
        public static final BodyData MALE_12_12 = new BodyDataBuilder(
                12 * 12, MALE, 156.0426d, 39d).build();
        public static final BodyData MALE_13_12 = new BodyDataBuilder(
                13 * 12, MALE, 163.1816d, 44d).build();
        public static final BodyData MALE_14_12 = new BodyDataBuilder(
                14 * 12, MALE, 168.958d, 47d).build();
        public static final BodyData MALE_15_12 = new BodyDataBuilder(
                15 * 12, MALE, 172.8967d, 50d).build();
        public static final BodyData MALE_16_12 = new BodyDataBuilder(
                16 * 12, MALE, 175.1609d, 53d).build();
        public static final BodyData MALE_17_12 = new BodyDataBuilder(
                17 * 12, MALE, 176.1449d, 56d).build();

        public static final BodyData FEMALE_18_12 = new BodyDataBuilder(
                18 * 12, FEMALE, 163.0595d, 56d).build();
        public static final BodyData FEMALE_19_12 = new BodyDataBuilder(
                19 * 12, FEMALE, 163.1548d, 58d).build();
        public static final BodyData FEMALE_25_12 = new BodyDataBuilder(
                25 * 12, FEMALE, 152.61d, 47.1d).build();

        public static final BodyData FEMALE_35_12 = new BodyDataBuilder(
                35 * 12, FEMALE, 152.35d, 48.58d).build();
        public static final BodyData FEMALE_45_12 = new BodyDataBuilder(
                45 * 12, FEMALE, 151.76d, 58.85d).build();
        public static final BodyData FEMALE_55_12 = new BodyDataBuilder(
                55 * 12, FEMALE, 149.55d, 47.03d).build();

        public static final BodyData FEMALE_65_12 = new BodyDataBuilder(
                65 * 12, FEMALE, 149.55d, 47.03d).build();

        public static final BodyData MALE_18_12 = new BodyDataBuilder(
                18 * 12, MALE, 176.1449d, 56d).build();
        public static final BodyData MALE_19_12 = new BodyDataBuilder(
                19 * 12, MALE, 176.5432d, 59d).build();

        public static final BodyData MALE_25_12 = new BodyDataBuilder(
                25 * 12, MALE, 162.99d, 54.2d).build();
        public static final BodyData MALE_35_12 = new BodyDataBuilder(
                35 * 12, MALE, 162.4d, 54.44d).build();
        public static final BodyData MALE_45_12 = new BodyDataBuilder(
                45 * 12, MALE, 161.85d, 53.84d).build();

        public static final BodyData MALE_55_12 = new BodyDataBuilder(
                55 * 12, MALE, 160.12d, 52.19d).build();

        public static final BodyData MALE_65_12 = new BodyDataBuilder(
                65 * 12, MALE, 160.12d, 52.19d).build();

        // Pregnancy & Lactation
        public static final BodyData FEMALE_25_12_MOP_1 = new BodyDataBuilder(
                25 * 12, FEMALE, 152.61d, 47.1d).build();
        public static final BodyData FEMALE_25_12_MOP_4 = new BodyDataBuilder(
                25 * 12, FEMALE, 152.61d, 47.1d).build();
        public static final BodyData FEMALE_25_12_MOP_7 = new BodyDataBuilder(
                25 * 12, FEMALE, 152.61d, 47.1d).build();
        public static final BodyData FEMALE_25_12_MOL_1 = new BodyDataBuilder(
                25 * 12, FEMALE, 152.61d, 47.1d).build();
        public static final BodyData FEMALE_25_12_MOL_7 = new BodyDataBuilder(
                25 * 12, FEMALE, 152.61d, 47.1d).build();

        public static final BodyData MALE_0_HIV = new BodyDataBuilder(0, MALE, 54.7244d, 4.4709d).build();
        public static final BodyData MALE_11_AIDS = new BodyDataBuilder(11, MALE, 75.7488d, 9.6479d).build();
        public static final BodyData MALE_10_12_HIV = new BodyDataBuilder(10 * 12, MALE, 143.1126d, 32d).build();
        public static final BodyData FEMALE_18_12_AIDS = new BodyDataBuilder(18 * 12, FEMALE, 163.0595d, 56d).build();
        public static final BodyData MALE_1_12_HIV = new BodyDataBuilder(1 * 12, MALE, 87.8161d, 12.1515d).build();
        public static final BodyData MALE_35_12_AIDS = new BodyDataBuilder(35 * 12, MALE, 162.4d, 54.44d).build();
        public static final BodyData MALE_65_12_AIDS = new BodyDataBuilder(65 * 12, MALE, 160.12d, 52.19d).build();

        public static final BodyData MALE_0_HIV_AIDS = new BodyDataBuilder(0, MALE, 54.7244d, 4.4709d).build();
        public static final BodyData MALE_10_12_HIV_AIDS = new BodyDataBuilder(10 * 12, MALE, 143.1126d, 32d).build();
        public static final BodyData MALE_1_12_HIV_AIDS = new BodyDataBuilder(1 * 12, MALE, 87.8161d, 12.1515d).build();
        public static final BodyData MALE_65_12_HIV_AIDS = new BodyDataBuilder(65 * 12, MALE, 160.12d, 52.19d).build();

        public static final BodyData[] FEMALES = {
                FEMALE_0, FEMALE_1, FEMALE_2, FEMALE_3, FEMALE_4, FEMALE_5, FEMALE_6, FEMALE_7, FEMALE_8, FEMALE_9,
                FEMALE_10, FEMALE_11, FEMALE_1_12, FEMALE_2_12, FEMALE_3_12, FEMALE_4_12, FEMALE_5_12, FEMALE_6_12,
                FEMALE_7_12, FEMALE_8_12, FEMALE_9_12, FEMALE_10_12, FEMALE_11_12, FEMALE_12_12, FEMALE_13_12,
                FEMALE_14_12, FEMALE_15_12, FEMALE_16_12, FEMALE_17_12, FEMALE_18_12, FEMALE_19_12, FEMALE_25_12,
                FEMALE_35_12, FEMALE_45_12, FEMALE_55_12, FEMALE_65_12
        };
        public static final BodyData[] MALES = {
                MALE_0, MALE_1, MALE_2, MALE_3, MALE_4, MALE_5, MALE_6, MALE_7, MALE_8, MALE_9, MALE_10, MALE_11,
                MALE_1_12, MALE_2_12, MALE_3_12, MALE_4_12, MALE_5_12, MALE_6_12, MALE_7_12, MALE_8_12, MALE_9_12,
                MALE_10_12, MALE_11_12, MALE_12_12, MALE_13_12, MALE_14_12, MALE_15_12, MALE_16_12, MALE_17_12,
                MALE_18_12, MALE_19_12, MALE_25_12, MALE_35_12, MALE_45_12, MALE_55_12, MALE_65_12
        };

        static {
            FEMALE_25_12_MOP_1.setMonthOfPregnancy(1);
            FEMALE_25_12_MOP_4.setMonthOfPregnancy(4);
            FEMALE_25_12_MOP_7.setMonthOfPregnancy(7);

            FEMALE_25_12_MOL_1.setMonthOfLactation(1);
            FEMALE_25_12_MOL_7.setMonthOfLactation(7);

            MALE_0_HIV.setHiv(true);
            MALE_11_AIDS.setAids(true);
            MALE_10_12_HIV.setHiv(true);
            FEMALE_18_12_AIDS.setAids(true);
            MALE_1_12_HIV.setHiv(true);
            MALE_35_12_AIDS.setAids(true);
            MALE_65_12_AIDS.setAids(true);

            MALE_0_HIV_AIDS.setHiv(true);
            MALE_10_12_HIV_AIDS.setHiv(true);
            MALE_1_12_HIV_AIDS.setHiv(true);
            MALE_65_12_HIV_AIDS.setHiv(true);

            MALE_0_HIV_AIDS.setAids(true);
            MALE_10_12_HIV_AIDS.setAids(true);
            MALE_1_12_HIV_AIDS.setAids(true);
            MALE_65_12_HIV_AIDS.setAids(true);

        }

        public static BodyData random()
        {
            int randomIndex = Math.abs(new Random().nextInt());
            System.out.println(randomIndex + " - " + randomIndex % MALES.length);
            if (randomIndex % 2 == 0) { return MALES[(randomIndex % MALES.length)]; } else {
                return FEMALES[(randomIndex % FEMALES.length)];
            }
        }

        public static Queue<BodyData> random(int quantity)
        {
            Queue<BodyData> queue = new ConcurrentLinkedQueue<>();
            for (int i = 0; i < quantity; i++) {
                queue.add(random());
            }
            return queue;
        }
    }
}
