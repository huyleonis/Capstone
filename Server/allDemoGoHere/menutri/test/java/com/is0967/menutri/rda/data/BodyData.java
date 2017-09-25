package com.is0967.menutri.rda.data;

import java.time.Period;
import java.util.Objects;

public class BodyData
{

    /* Required Data */
    private final Period age;
    private final Gender gender;
    private final double height;
    private transient final MeasurementUnit heightUnit;
    private final double weight;
    private transient final MeasurementUnit weightUnit;
    /* Pregnancy */
    private final int monthOfPregnancy;
    /* Lactation -- số tháng cho bú*/
    private final int monthOfLactation;
    private final boolean healthy;
    private final boolean menstruating;
    private final boolean hiv;
    private final boolean aids;
    /* Activity Level */
    private final ActivityLevel activityLevel;

    //giá trị sinh học món ăn
    private final BioAvailability bioAvailability;

    public BodyData()
    {
        heightUnit = MeasurementUnit.CENTIMETRE;
        weightUnit = MeasurementUnit.KILO_GRAM;
        age = null;
        gender = null;
        height = 0;
        weight = 0;
        monthOfPregnancy = 0;
        monthOfLactation = 0;
        healthy = false;
        menstruating = false;
        hiv = false;
        activityLevel = null;
        bioAvailability = null;
        aids = false;
    }

    private BodyData(Builder builder)
    {
        this.age = builder.age;
        this.gender = builder.gender;
        this.height = builder.height;
        this.weight = builder.weight;
        this.monthOfPregnancy = builder.monthOfPregnancy;
        this.monthOfLactation = builder.monthOfLactation;
        this.menstruating = builder.menstruating;
        this.healthy = builder.healthy;
        this.activityLevel = builder.activityLevel;
        this.hiv = builder.hiv;
        this.aids = builder.aids;
        this.bioAvailability = builder.bioAvailability;
        heightUnit = MeasurementUnit.CENTIMETRE;
        weightUnit = MeasurementUnit.KILO_GRAM;
    }

    public Period getAge()
    {
        return age;
    }

    public boolean isFrom(Period age)
    {
        return Age.lessEqual(age, this.age);
    }

    public boolean isUnder(Period age)
    {
        return Age.lessThan(this.age, age);
    }

    public Gender getGender()
    {
        return gender;
    }

    public boolean isMale()
    {
        return this.gender == Gender.MALE;
    }

    public boolean isFemale()
    {
        return this.gender == Gender.FEMALE;
    }

    public double getHeight()
    {
        return height;
    }

    public MeasurementUnit getHeightUnit()
    {
        return heightUnit;
    }

    public double getWeight()
    {
        return weight;
    }

    public MeasurementUnit getWeightUnit()
    {
        return weightUnit;
    }

    public int getMonthOfPregnancy()
    {
        return monthOfPregnancy;
    }

    public int getMonthOfLactation()
    {
        return monthOfLactation;
    }

    public boolean isLactating(int fromMonth, int toMonth)
    {
        return (fromMonth <= this.monthOfLactation && this.monthOfLactation <= toMonth);
    }

    public boolean isPregnant(int fromMonth, int toMonth)
    {
        return (fromMonth <= this.monthOfPregnancy && this.monthOfPregnancy <= toMonth);
    }

    public boolean isHealthy()
    {
        return healthy;
    }

    public boolean isMenstruating()
    {
        return menstruating;
    }

    public ActivityLevel getActivityLevel()
    {
        return activityLevel;
    }

    public boolean isHiv()
    {
        return hiv;
    }

    public boolean isAids()
    {
        return aids;
    }

    public BioAvailability getBioAvailability()
    {
        return bioAvailability;
    }

    @Override
    public String toString()
    {
        return "BodyData{" +
                "\n    age=" + age +
                "\n    gender=" + gender +
                "\n    height=" + height +
                "\n    heightUnit=" + heightUnit +
                "\n    weight=" + weight +
                "\n    weightUnit=" + weightUnit +
                "\n    monthOfPregnancy=" + monthOfPregnancy +
                "\n    monthOfLactation=" + monthOfLactation +
                "\n    healthy=" + healthy +
                "\n    menstruating=" + menstruating +
                "\n    hiv=" + hiv +
                "\n    aids=" + aids +
                "\n    activityLevel=" + activityLevel +
                "\n    bioAvailability=" + bioAvailability +
                '}';
    }

    public static class Age
    {

        public static final Period ZERO = Period.ZERO;
        //<editor-fold desc="Months">
        public static final Period M1 = Period.ofMonths(1);
        public static final Period M2 = Period.ofMonths(2);
        public static final Period M3 = Period.ofMonths(3);
        public static final Period M4 = Period.ofMonths(4);
        public static final Period M5 = Period.ofMonths(5);
        public static final Period M6 = Period.ofMonths(6);
        public static final Period M7 = Period.ofMonths(7);
        public static final Period M8 = Period.ofMonths(8);
        public static final Period M9 = Period.ofMonths(9);
        public static final Period M10 = Period.ofMonths(10);
        public static final Period M11 = Period.ofMonths(11);
        public static final Period M12 = Period.ofMonths(12);
        //</editor-fold>
        //<editor-fold desc="Years">
        public static final Period Y1 = Period.ofYears(1);
        public static final Period Y2 = Period.ofYears(2);
        public static final Period Y3 = Period.ofYears(3);
        public static final Period Y4 = Period.ofYears(4);
        public static final Period Y5 = Period.ofYears(5);
        public static final Period Y6 = Period.ofYears(6);
        public static final Period Y7 = Period.ofYears(7);
        public static final Period Y8 = Period.ofYears(8);
        public static final Period Y9 = Period.ofYears(9);
        public static final Period Y10 = Period.ofYears(10);
        public static final Period Y11 = Period.ofYears(11);
        public static final Period Y12 = Period.ofYears(12);
        public static final Period Y13 = Period.ofYears(13);
        public static final Period Y14 = Period.ofYears(14);
        public static final Period Y15 = Period.ofYears(15);
        public static final Period Y16 = Period.ofYears(16);
        public static final Period Y17 = Period.ofYears(17);
        public static final Period Y18 = Period.ofYears(18);
        public static final Period Y19 = Period.ofYears(19);
        public static final Period Y20 = Period.ofYears(20);
        public static final Period Y21 = Period.ofYears(21);
        public static final Period Y22 = Period.ofYears(22);
        public static final Period Y23 = Period.ofYears(23);
        public static final Period Y24 = Period.ofYears(24);
        public static final Period Y25 = Period.ofYears(25);
        public static final Period Y26 = Period.ofYears(26);
        public static final Period Y27 = Period.ofYears(27);
        public static final Period Y28 = Period.ofYears(28);
        public static final Period Y29 = Period.ofYears(29);
        public static final Period Y30 = Period.ofYears(30);
        public static final Period Y31 = Period.ofYears(31);
        public static final Period Y32 = Period.ofYears(32);
        public static final Period Y33 = Period.ofYears(33);
        public static final Period Y34 = Period.ofYears(34);
        public static final Period Y35 = Period.ofYears(35);
        public static final Period Y36 = Period.ofYears(36);
        public static final Period Y37 = Period.ofYears(37);
        public static final Period Y38 = Period.ofYears(38);
        public static final Period Y39 = Period.ofYears(39);
        public static final Period Y40 = Period.ofYears(40);
        public static final Period Y41 = Period.ofYears(41);
        public static final Period Y42 = Period.ofYears(42);
        public static final Period Y43 = Period.ofYears(43);
        public static final Period Y44 = Period.ofYears(44);
        public static final Period Y45 = Period.ofYears(45);
        public static final Period Y46 = Period.ofYears(46);
        public static final Period Y47 = Period.ofYears(47);
        public static final Period Y48 = Period.ofYears(48);
        public static final Period Y49 = Period.ofYears(49);
        public static final Period Y50 = Period.ofYears(50);
        public static final Period Y51 = Period.ofYears(51);
        public static final Period Y52 = Period.ofYears(52);
        public static final Period Y53 = Period.ofYears(53);
        public static final Period Y54 = Period.ofYears(54);
        public static final Period Y55 = Period.ofYears(55);
        public static final Period Y56 = Period.ofYears(56);
        public static final Period Y57 = Period.ofYears(57);
        public static final Period Y58 = Period.ofYears(58);
        public static final Period Y59 = Period.ofYears(59);
        public static final Period Y60 = Period.ofYears(60);
        public static final Period Y61 = Period.ofYears(61);
        public static final Period Y62 = Period.ofYears(62);
        public static final Period Y63 = Period.ofYears(63);
        public static final Period Y64 = Period.ofYears(64);
        public static final Period Y65 = Period.ofYears(65);
        public static final Period Y66 = Period.ofYears(66);
        public static final Period Y67 = Period.ofYears(67);
        public static final Period Y68 = Period.ofYears(68);
        public static final Period Y69 = Period.ofYears(69);
        public static final Period Y70 = Period.ofYears(70);
        public static final Period Y71 = Period.ofYears(71);
        public static final Period Y72 = Period.ofYears(72);
        public static final Period Y73 = Period.ofYears(73);
        public static final Period Y74 = Period.ofYears(74);
        public static final Period Y75 = Period.ofYears(75);
        public static final Period Y76 = Period.ofYears(76);
        public static final Period Y77 = Period.ofYears(77);
        public static final Period Y78 = Period.ofYears(78);
        public static final Period Y79 = Period.ofYears(79);
        public static final Period Y80 = Period.ofYears(80);
        public static final Period Y81 = Period.ofYears(81);
        public static final Period Y82 = Period.ofYears(82);
        public static final Period Y83 = Period.ofYears(83);
        public static final Period Y84 = Period.ofYears(84);
        public static final Period Y85 = Period.ofYears(85);
        public static final Period Y86 = Period.ofYears(86);
        public static final Period Y87 = Period.ofYears(87);
        public static final Period Y88 = Period.ofYears(88);
        public static final Period Y89 = Period.ofYears(89);
        public static final Period Y90 = Period.ofYears(90);
        public static final Period Y91 = Period.ofYears(91);
        public static final Period Y92 = Period.ofYears(92);
        public static final Period Y93 = Period.ofYears(93);
        public static final Period Y94 = Period.ofYears(94);
        public static final Period Y95 = Period.ofYears(95);
        public static final Period Y96 = Period.ofYears(96);
        public static final Period Y97 = Period.ofYears(97);
        public static final Period Y98 = Period.ofYears(98);
        public static final Period Y99 = Period.ofYears(99);
        //</editor-fold>
        public static final Period MAX = Period.ofYears(9999);

        public static final Period[] MONTHS = {
                ZERO, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12};

        public static final Period[] YEARS = {
                M12, Y1, Y2, Y3, Y4, Y5, Y6, Y7, Y8, Y9, Y10, Y11, Y12, Y13,
                Y14, Y15, Y16, Y17, Y18, Y19, Y20, Y21, Y22, Y23, Y24, Y25, Y26,
                Y27, Y28, Y29, Y30, Y31, Y32, Y33, Y34, Y35, Y36, Y37, Y38, Y39,
                Y40, Y41, Y42, Y43, Y44, Y45, Y46, Y47, Y48, Y49, Y50, Y51, Y52,
                Y53, Y54, Y55, Y56, Y57, Y58, Y59, Y60, Y61, Y62, Y63, Y64, Y65,
                Y66, Y67, Y68, Y69, Y70, Y71, Y72, Y73, Y74, Y75, Y76, Y77, Y78,
                Y79, Y80, Y81, Y82, Y83, Y84, Y85, Y86, Y87, Y88, Y89, Y90, Y91,
                Y92, Y93, Y94, Y95, Y96, Y97, Y98, Y99,};

        public static boolean lessThan(Period compareR, Period compareE)
        {
            return compare(compareR, compareE) < 0;
        }

        public static boolean lessEqual(Period compareR, Period compareE)
        {
            return compare(compareR, compareE) <= 0;
        }

        /**
         * @return positive if compareR > compareE, negative if compareR < compareE, otherwise zero.
         */
        static int compare(Period compareR, Period compareE)
        {
            int years = compareR.getYears() - compareE.getYears();
            int months = compareR.getMonths() - compareE.getMonths();
            int days = compareR.getDays() - compareE.getDays();
            return (years != 0) ? years
                    : (months != 0) ? months
                            : days;
        }

        public static String toString(Period age)
        {
            return (age.getYears() > 0) ? "Y" + age.getYears()
                    : (age.getMonths() > 0) ? "M" + age
                            .getMonths()
                            : "";
        }
    }

    public static class Weight
    {

        public static final int BABY_0_5 = 6;
        public static final int BABY_6_12 = 9;
        public static final int CHILD_1_3 = 12;
        public static final int CHILD_4_6 = 19;
        public static final int CHILD_7_9 = 25;
        public static final int BOY = 51;
        public static final int GIRL = 49;
        public static final int MAN = 54;
        public static final int MAN_ELDERLY = 58;
        public static final int WOMAN = 60;
        public static final int WOMAN_ELDERLY = 54;
    }

    public static class Sample
    {

        public static final double HEIGHT = 100;
        public static final BodyData ZERO_BABY_BOY = new Builder(Age.ZERO, Gender.MALE, HEIGHT, 3d)
                .build();
        public static final BodyData M1_BABY_BOY = new Builder(Age.M1, Gender.MALE, HEIGHT, 3.5d).build();
        public static final BodyData M2_BABY_BOY = new Builder(Age.M2, Gender.MALE, HEIGHT, 4d).build();
        public static final BodyData M3_BABY_BOY = new Builder(Age.M3, Gender.MALE, HEIGHT, 4.5d).build();
        public static final BodyData M4_BABY_BOY = new Builder(Age.M4, Gender.MALE, HEIGHT, 5d).build();
        public static final BodyData M5_BABY_BOY = new Builder(Age.M5, Gender.MALE, HEIGHT, 5.5d).build();
        public static final BodyData M6_BABY_BOY = new Builder(Age.M6, Gender.MALE, HEIGHT, 6d).build();
        public static final BodyData M7_BABY_BOY = new Builder(Age.M7, Gender.MALE, HEIGHT, 6.5d).build();
        public static final BodyData M8_BABY_BOY = new Builder(Age.M8, Gender.MALE, HEIGHT, 7d).build();
        public static final BodyData M9_BABY_BOY = new Builder(Age.M9, Gender.MALE, HEIGHT, 7.5d).build();
        public static final BodyData M10_BABY_BOY = new Builder(Age.M10, Gender.MALE, HEIGHT, 8d).build();
        public static final BodyData M11_BABY_BOY = new Builder(Age.M11, Gender.MALE, HEIGHT, 8.5d)
                .build();
        public static final BodyData M12_BABY_BOY = new Builder(Age.M11.withDays(15), Gender.MALE, HEIGHT,
                                                                9d)
                .build(); // TODO: find a way to convert a 12 months baby to one year old child
        public static final BodyData ZERO_BABY_GIRL = new Builder(Age.ZERO, Gender.FEMALE, HEIGHT, 3d)
                .build();
        public static final BodyData M1_BABY_GIRL = new Builder(Age.M1, Gender.FEMALE, HEIGHT, 3.5d)
                .build();
        public static final BodyData M2_BABY_GIRL = new Builder(Age.M2, Gender.FEMALE, HEIGHT, 4d).build();
        public static final BodyData M3_BABY_GIRL = new Builder(Age.M3, Gender.FEMALE, HEIGHT, 4.5d)
                .build();
        public static final BodyData M4_BABY_GIRL = new Builder(Age.M4, Gender.FEMALE, HEIGHT, 5d).build();
        public static final BodyData M5_BABY_GIRL = new Builder(Age.M5, Gender.FEMALE, HEIGHT, 5.5d)
                .build();
        public static final BodyData M6_BABY_GIRL = new Builder(Age.M6, Gender.FEMALE, HEIGHT, 6d).build();
        public static final BodyData M7_BABY_GIRL = new Builder(Age.M7, Gender.FEMALE, HEIGHT, 6.5d)
                .build();
        public static final BodyData M8_BABY_GIRL = new Builder(Age.M8, Gender.FEMALE, HEIGHT, 7d).build();
        public static final BodyData M9_BABY_GIRL = new Builder(Age.M9, Gender.FEMALE, HEIGHT, 7.5d)
                .build();
        public static final BodyData M10_BABY_GIRL = new Builder(Age.M10, Gender.FEMALE, HEIGHT, 8d)
                .build();
        public static final BodyData M11_BABY_GIRL = new Builder(Age.M11, Gender.FEMALE, HEIGHT, 8.5d)
                .build();
        public static final BodyData M12_BABY_GIRL = new Builder(Age.M11.withDays(15), Gender.FEMALE,
                                                                 HEIGHT, 9d)
                .build(); // TODO: find a way to convert a 12 months baby to one year old child
        public static final BodyData Y1_LITTLE_BOY = new Builder(Age.Y1, Gender.MALE, HEIGHT, 9d).build();
        public static final BodyData Y2_LITTLE_BOY = new Builder(Age.Y2, Gender.MALE, HEIGHT, 12d).build();
        public static final BodyData Y3_LITTLE_BOY = new Builder(Age.Y3, Gender.MALE, HEIGHT, 14d).build();
        public static final BodyData Y4_LITTLE_BOY = new Builder(Age.Y4, Gender.MALE, HEIGHT, 16d).build();
        public static final BodyData Y5_LITTLE_BOY = new Builder(Age.Y5, Gender.MALE, HEIGHT, 18d).build();
        public static final BodyData Y6_LITTLE_BOY = new Builder(Age.Y6, Gender.MALE, HEIGHT, 20d).build();
        public static final BodyData Y7_LITTLE_BOY = new Builder(Age.Y7, Gender.MALE, HEIGHT, 22d).build();
        public static final BodyData Y8_LITTLE_BOY = new Builder(Age.Y8, Gender.MALE, HEIGHT, 26d).build();
        public static final BodyData Y9_LITTLE_BOY = new Builder(Age.Y9, Gender.MALE, HEIGHT, 28d).build();
        public static final BodyData Y1_LITTLE_GIRL = new Builder(Age.Y1, Gender.FEMALE, HEIGHT, 9d)
                .build();
        public static final BodyData Y2_LITTLE_GIRL = new Builder(Age.Y2, Gender.FEMALE, HEIGHT, 12d)
                .build();
        public static final BodyData Y3_LITTLE_GIRL = new Builder(Age.Y3, Gender.FEMALE, HEIGHT, 14d)
                .build();
        public static final BodyData Y4_LITTLE_GIRL = new Builder(Age.Y4, Gender.FEMALE, HEIGHT, 16d)
                .build();
        public static final BodyData Y5_LITTLE_GIRL = new Builder(Age.Y5, Gender.FEMALE, HEIGHT, 18d)
                .build();
        public static final BodyData Y6_LITTLE_GIRL = new Builder(Age.Y6, Gender.FEMALE, HEIGHT, 20d)
                .build();
        public static final BodyData Y7_LITTLE_GIRL = new Builder(Age.Y7, Gender.FEMALE, HEIGHT, 22d)
                .build();
        public static final BodyData Y8_LITTLE_GIRL = new Builder(Age.Y8, Gender.FEMALE, HEIGHT, 26d)
                .build();
        public static final BodyData Y9_LITTLE_GIRL = new Builder(Age.Y9, Gender.FEMALE, HEIGHT, 28d)
                .build();
        public static final BodyData Y10_BOY = new Builder(Age.Y10, Gender.MALE, HEIGHT, 30d).build();
        public static final BodyData Y11_BOY = new Builder(Age.Y11, Gender.MALE, HEIGHT, 32d).build();
        public static final BodyData Y12_BOY = new Builder(Age.Y12, Gender.MALE, HEIGHT, 34d).build();
        public static final BodyData Y13_BOY = new Builder(Age.Y13, Gender.MALE, HEIGHT, 39d).build();
        public static final BodyData Y14_BOY = new Builder(Age.Y14, Gender.MALE, HEIGHT, 44d).build();
        public static final BodyData Y15_BOY = new Builder(Age.Y15, Gender.MALE, HEIGHT, 47d).build();
        public static final BodyData Y16_BOY = new Builder(Age.Y16, Gender.MALE, HEIGHT, 50d).build();
        public static final BodyData Y17_BOY = new Builder(Age.Y17, Gender.MALE, HEIGHT, 53d).build();
        public static final BodyData Y18_BOY = new Builder(Age.Y18, Gender.MALE, HEIGHT, 56d).build();
        public static final BodyData Y10_GIRL = new Builder(Age.Y10, Gender.FEMALE, HEIGHT, 31d).build();
        public static final BodyData Y11_GIRL = new Builder(Age.Y11, Gender.FEMALE, HEIGHT, 33d).build();
        public static final BodyData Y12_GIRL = new Builder(Age.Y12, Gender.FEMALE, HEIGHT, 36d).build();
        public static final BodyData Y13_GIRL = new Builder(Age.Y13, Gender.FEMALE, HEIGHT, 39d).build();
        public static final BodyData Y14_GIRL = new Builder(Age.Y14, Gender.FEMALE, HEIGHT, 42d).build();
        public static final BodyData Y15_GIRL = new Builder(Age.Y15, Gender.FEMALE, HEIGHT, 45d).build();
        public static final BodyData Y16_GIRL = new Builder(Age.Y16, Gender.FEMALE, HEIGHT, 47d).build();
        public static final BodyData Y17_GIRL = new Builder(Age.Y17, Gender.FEMALE, HEIGHT, 48d).build();
        public static final BodyData Y18_GIRL = new Builder(Age.Y18, Gender.FEMALE, HEIGHT, 49d).build();

        public static final BodyData[] BABY_BOYS = {ZERO_BABY_BOY, M1_BABY_BOY,
                M2_BABY_BOY,
                M3_BABY_BOY, M4_BABY_BOY, M5_BABY_BOY, M6_BABY_BOY, M7_BABY_BOY,
                M8_BABY_BOY, M9_BABY_BOY,
                M10_BABY_BOY, M11_BABY_BOY, M12_BABY_BOY};
        public static final BodyData[] BABY_GIRLS = {ZERO_BABY_GIRL,
                M1_BABY_GIRL, M2_BABY_GIRL,
                M3_BABY_GIRL, M4_BABY_GIRL, M5_BABY_GIRL, M6_BABY_GIRL,
                M7_BABY_GIRL, M8_BABY_GIRL,
                M9_BABY_GIRL, M10_BABY_GIRL, M11_BABY_GIRL, M12_BABY_GIRL,};
        public static final BodyData[] LITTLE_BOYS = {Y1_LITTLE_BOY,
                Y2_LITTLE_BOY, Y3_LITTLE_BOY,
                Y4_LITTLE_BOY, Y5_LITTLE_BOY, Y6_LITTLE_BOY, Y7_LITTLE_BOY,
                Y8_LITTLE_BOY, Y9_LITTLE_BOY,};
        public static final BodyData[] LITTLE_GIRLS = {Y1_LITTLE_GIRL,
                Y2_LITTLE_GIRL, Y3_LITTLE_GIRL,
                Y4_LITTLE_GIRL, Y5_LITTLE_GIRL, Y6_LITTLE_GIRL, Y7_LITTLE_GIRL,
                Y8_LITTLE_GIRL,
                Y9_LITTLE_GIRL,};
        public static final BodyData[] BOYS = {Y10_BOY, Y11_BOY, Y12_BOY,
                Y13_BOY, Y14_BOY, Y15_BOY,
                Y16_BOY, Y17_BOY, Y18_BOY,};
        public static final BodyData[] GIRLS = {Y10_GIRL, Y11_GIRL, Y12_GIRL,
                Y13_GIRL, Y14_GIRL,
                Y15_GIRL, Y16_GIRL, Y17_GIRL, Y18_GIRL,};
    }

    public static class Builder
    {

        /* Required Data */
        private final Period age;
        private final Gender gender;
        private final double height;
        private final double weight;
        /* Pregnancy */
        private int monthOfPregnancy;
        /* Lactating */
        private int monthOfLactation;
        private boolean menstruating;
        private boolean healthy;
        private boolean hiv;
        private boolean aids;
        /* Activity Level */
        private ActivityLevel activityLevel;
        private BioAvailability bioAvailability;

        public Builder(Period age, Gender gender, double height, double weight)
        {
            Objects.requireNonNull(age);
            Objects.requireNonNull(gender);
            if (height <= 0) throw new IllegalArgumentException("Height is required.");
            if (weight <= 0) throw new IllegalArgumentException("Weight is required.");

            this.age = age;
            this.gender = gender;
            this.height = height;
            this.weight = weight;

            /* default values */
            activityLevel = ActivityLevel.MODERATE;
            bioAvailability = BioAvailability.INTERMEDIATE;
        }

        public Builder pregnancy(int monthOfPregnancy)
        {
            this.monthOfPregnancy = monthOfPregnancy;
            return this;
        }

        public Builder lactation(int monthOfLactation)
        {
            this.monthOfLactation = monthOfLactation;
            return this;
        }

        public Builder healthy(boolean healthy)
        {
            this.healthy = healthy;
            return this;
        }

        public Builder menstruating(boolean menstruating)
        {
            this.menstruating = menstruating;
            return this;
        }

        public Builder hiv(boolean hiv)
        {
            this.hiv = hiv;
            return this;
        }

        public Builder aids(boolean aids)
        {
            this.aids = aids;
            return this;
        }

        public Builder activityLevel(ActivityLevel activityLevel)
        {
            this.activityLevel = activityLevel;
            return this;
        }

        public Builder bioAvailability(BioAvailability bioAvailability)
        {
            this.bioAvailability = bioAvailability;
            return this;
        }

        public BodyData build()
        {
            return new BodyData(this);
        }
    }
}