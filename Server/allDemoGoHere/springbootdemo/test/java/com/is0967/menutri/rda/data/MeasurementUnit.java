package com.is0967.menutri.rda.data;

import static com.is0967.menutri.rda.data.MeasurementUnit.UnitType.ENERGY_UNIT;
import static com.is0967.menutri.rda.data.MeasurementUnit.UnitType.LENGTH_UNIT;
import static com.is0967.menutri.rda.data.MeasurementUnit.UnitType.MASS_UNIT;
import static com.is0967.menutri.rda.data.MeasurementUnit.UnitType.PER_BODY_WEIGHT;
import static com.is0967.menutri.rda.data.MeasurementUnit.UnitType.PER_KILO_CALORIE;
import static com.is0967.menutri.rda.data.MeasurementUnit.UnitType.PURE_NUMBER;
import static com.is0967.menutri.rda.data.MeasurementUnit.UnitType.VOLUME_UNIT;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by phuctran93 on 3/1/2017.
 */
public enum MeasurementUnit
{
    DOUBLE_SD("2 standard deviations", "2SD", 1, PURE_NUMBER),

    METRE("metre", "m", 1, LENGTH_UNIT),
    CENTIMETRE("centimetre", "cm", 0.01, LENGTH_UNIT),

    /**
     * 1 uno = 1 = 100%.
     */
    ACTIVITY_FACTOR("uno", "U", 1, PURE_NUMBER),

    /**
     * 1 centi-uno = 0.01 = 1%
     */
    PERCENT("percent", "%", 0.01, PURE_NUMBER),

    GRAM("gram", "g", 1, MASS_UNIT),
    /**
     * 1 gram/kilogram = 0.001 = 1 milli-uno
     */
    GRAM_PER_KILO_GRAM("gram/kilogram", "g/kg", 1, PER_BODY_WEIGHT),
    GRAM_PER_KILO_CALORIE("gram/kilo-calorie", "g/kcal", 1, PER_KILO_CALORIE),

    DECI_GRAM("deci-gram", "dg", 0.1, MASS_UNIT),
    CENTI_GRAM("centi-gram", "cg", 0.01, MASS_UNIT),
    MILLI_GRAM("milligram", "mg", 0.001, MASS_UNIT),

    MICRO_GRAM("micro-gram", "μg", 0.000_001, MASS_UNIT),
    /**
     * 1 micro-gram/kilogram = 1E-9 = 1 nano-uno
     */
    MICRO_GRAM_PER_KILO_GRAM("micro-gram/kilogram", "μg/kg", 0.000_000_001, PER_BODY_WEIGHT),

    NANO_GRAM("nano-gram", "ng", 0.000_000_001, MASS_UNIT),
    PICO_GRAM("pico-gram", "pg", 1E-12, MASS_UNIT),
    FEMTO_GRAM("femto-gram", "fg", 1E-15, MASS_UNIT),
    ATTO_GRAM("atto-gram", "ag", 1E-18, MASS_UNIT),
    ZEPTO_GRAM("zepto-gram", "zg", 1E-21, MASS_UNIT),
    YOCTO_GRAM("yocto-gram", "yg", 1E-24, MASS_UNIT),

    DECA_GRAM("deca-gram", "dag", 10, MASS_UNIT),
    HECTO_GRAM("hecto-gram", "hg", 100, MASS_UNIT),
    KILO_GRAM("kilogram", "kg", 1_000, MASS_UNIT),
    MEGA_GRAM("mega-gram", "Mg", 1_000_000, MASS_UNIT),
    GIGA_GRAM("giga-gram", "Gg", 1_000_000_000, MASS_UNIT),
    TERA_GRAM("tera-gram", "Tg", 1E+12, MASS_UNIT),
    PETA_GRAM("peta-gram", "Pg", 1E+5, MASS_UNIT),
    EXA_GRAM("exa-gram", "Eg", 1E+18, MASS_UNIT),
    ZETTA_GRAM("zetta-gram", "Zg", 1E+21, MASS_UNIT),
    YOTTA_GRAM("yotta-gram", "Yg", 1E+24, MASS_UNIT),

    CALORIE("calorie", "cal", 1, ENERGY_UNIT),
    KILO_CALORIE("kilo-calorie", "kcal", 1_000, ENERGY_UNIT),
    PERCENT_OF_TOTAL_ENERGY("percent of total energy", "%-Σ-cal", 0.01, PURE_NUMBER),

    LITRE("litre", "L", 1, VOLUME_UNIT),
    MILLI_LITRE("millilitre", "mL", 0.001, VOLUME_UNIT),
    MILLI_LITRE_PER_KILO_CALORIE("millilitre/kilo-calorie", "mL/kcal", 0.001, PER_KILO_CALORIE),;

    private final String name;
    private final String symbol;
    private final double factor;
    private final UnitType unitType;

    MeasurementUnit(String name, String symbol, double factor,
            UnitType unitType)
    {
        this.name = name;
        this.symbol = symbol;
        this.factor = factor;
        this.unitType = unitType;
    }

    public static Double toBasicUnit(Double value, MeasurementUnit unit)
    {
        if (value == null) return null;
        Objects.requireNonNull(unit, "value's unit is required.");

        return BigDecimal.valueOf(value)
                .multiply(BigDecimal.valueOf(unit.getFactor()))
                .doubleValue();
    }

    public Double toBasicUnit(Double value)
    {
        return toBasicUnit(value, this);
    }

    public String getName()
    {
        return name;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public double getFactor()
    {
        return factor;
    }

    public UnitType getUnitType()
    {
        return unitType;
    }

    public double fromUnit(double otherValue, MeasurementUnit otherUnit)
    {
        if (this.unitType != otherUnit.unitType) {
            String msg = String.format("Required: %s, Actual: %s", this.unitType, otherUnit.unitType);
            throw new IllegalArgumentException(msg);
        }

        return BigDecimal.valueOf(otherValue)
                .multiply(BigDecimal.valueOf(otherUnit.factor))
                .divide(BigDecimal.valueOf(this.factor))
                .doubleValue();
    }

    public double fromBasicUnit(double standardValue)
    {
        return BigDecimal.valueOf(standardValue)
                .divide(BigDecimal.valueOf(this.factor))
                .doubleValue();
    }

    public int energyFrom(int proteinGram, int fatGram, int carbohydrateGram)
    {
        if (this.unitType != ENERGY_UNIT) {
            throw new IllegalStateException("This is not an unit of energy.");
        }

        return BigDecimal
                .valueOf(proteinGram * 4_000 + fatGram * 9_000 + carbohydrateGram * 4_000)
                .divide(BigDecimal.valueOf(this.factor))
                .intValue();
    }

    public enum UnitType
    {
        VOLUME_UNIT,
        LENGTH_UNIT,
        MASS_UNIT,
        ENERGY_UNIT,
        PURE_NUMBER,
        PER_BODY_WEIGHT,
        PER_KILO_CALORIE,;

        /**
         * Length: metre,
         * Mass: gram,
         * Energy: calorie,
         * Pure number: uno,
         * Mixed: ?!
         *
         * @return MeasurementUnit that has the same UnitType and has factor equal 1.
         */
        public MeasurementUnit getBasicUnit()
        {
            for (MeasurementUnit measurementUnit : MeasurementUnit.values()) {
                if (measurementUnit.getUnitType() == this
                        && measurementUnit.getFactor() == 1) {
                    return measurementUnit;
                }
            }
            return null;
        }
    }
}
