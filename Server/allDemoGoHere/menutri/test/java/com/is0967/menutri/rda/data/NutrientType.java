package com.is0967.menutri.rda.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phuctran93 on 3/3`/2017.
 */
public enum NutrientType
{
    //<editor-fold desc="Nutrients Tree">
    //@formatter:off
    NUTRIENT(0, "", null, null, true),
        ENERGY(2, "energy|năng.lượng", NUTRIENT, MeasurementUnit.KILO_CALORIE, false),
        MACRO_NUTRIENT(0, "", NUTRIENT, MeasurementUnit.PERCENT_OF_TOTAL_ENERGY, true),
            CARBOHYDRATE_AND_FIBER(5, "carbohydrate|glucid|đường.bột", MACRO_NUTRIENT, null, false),
                SUGARS(0, "sugar[s]?|đường", CARBOHYDRATE_AND_FIBER, null, false),
                    MONO_SACCHARIDES(0, "mono.?saccharides", SUGARS, null, false),
                    DI_SACCHARIDES(0, "di.?saccharides", SUGARS, null, false),
                    POLYOLS(0, "polyols", SUGARS, null, false),
                OLIGO_SACCHARIDES(0, "polyols", CARBOHYDRATE_AND_FIBER, null, false),
                    MALTO_OLIGO_SACCHARIDES(0, "malto.?oligo.?saccharides", OLIGO_SACCHARIDES, null, false),
                    OTHER_OLIGO_SACCHARIDES(0, "other.?oligo.?saccharides", OLIGO_SACCHARIDES, null, false),
                POLY_SACCHARIDES(0, "polysaccharides", CARBOHYDRATE_AND_FIBER, null, false),
                    STARCH(0, "starch|tinh.bột", POLY_SACCHARIDES, null, false),
                    NON_STARCH_AND_FIBER(6, "fiber|chất.xơ", POLY_SACCHARIDES, null, false),
            PROTEIN(3, "protein|protid[s]?|đạm", MACRO_NUTRIENT, MeasurementUnit.GRAM, false),
                HISTIDINE(30, "histidin[e]?", PROTEIN, MeasurementUnit.MILLI_GRAM, false),
                ISOLEUCINE(31, "isoleucin[e]?", PROTEIN, MeasurementUnit.MILLI_GRAM, false),
                LEUCINE(32, "leucin[e]?", PROTEIN, MeasurementUnit.MILLI_GRAM, false),
                LYSINE(33, "lysin[e]?", PROTEIN, MeasurementUnit.MILLI_GRAM, false),
                METHIONINE(34, "methionin[e]?", PROTEIN, MeasurementUnit.MILLI_GRAM, false),
                CYSTEINE(35, "cystein[e]?", PROTEIN, MeasurementUnit.MILLI_GRAM, false),
                THREONINE(36, "threonin[e]?", PROTEIN, MeasurementUnit.MILLI_GRAM, false),
                TRYPTOPHAN(41, "tryptophan", PROTEIN, MeasurementUnit.MILLI_GRAM, false),
                VALINE(37, "valin[e]?", PROTEIN, MeasurementUnit.MILLI_GRAM, false),
            FAT(4, "fat|lipid[s]?|chất.béo", MACRO_NUTRIENT, null, false),
                SATURATED_FAT(0, "saturated.fat|béo.(no|bão.hòa)", FAT, null, false),
                UNSATURATED_FAT(0, "unsaturated.fat|béo.(không|chưa).(no|bão.hòa)", FAT, null, false),
                    LINOLEIC_ACID(0, "linoleic.acid|acid.linoleic", UNSATURATED_FAT, null, false),
                    ALPHA_LINOLENIC_ACID(0, "alpha.linolenic.acid|acid.alpha.linolenic", UNSATURATED_FAT, null, false),
        ELEMENT(0, "", NUTRIENT, MeasurementUnit.MILLI_GRAM, true),
            MINERAL(0, "mineral", ELEMENT, null, true),
                CALCIUM(7, "calcium|calci|can.?xi", MINERAL, null, false),
                PHOSPHORUS(11, "phosphorus|phospho|phốt.pho", MINERAL, null, false),
                MAGNESIUM(9, "magnesium|ma.?giê", MINERAL, null, false),
            TRACE_ELEMENT(0, "trace element", ELEMENT, null, true),
                IRON(8, "iron|sắt", TRACE_ELEMENT, null, false),
                IODINE(0, "iodin[e]?|i.?ốt", TRACE_ELEMENT, MeasurementUnit.MICRO_GRAM, false),
                ZINC(14, "zinc|kẽm", TRACE_ELEMENT, null, false),
                SELENIUM(16, "selenium|selen", TRACE_ELEMENT, MeasurementUnit.MICRO_GRAM, false),
            ELECTROLYTE(0, "electrolyte", ELEMENT, null, true),
                SODIUM(13, "sodium|na.?tri", ELECTROLYTE, null, false),
                POTASSIUM(12, "potassium|ka.?li", ELECTROLYTE, null, false),
                CHLORIDE(0, "chloride|clo", ELECTROLYTE, null, false),
        VITAMIN(0, "vitamin", NUTRIENT, MeasurementUnit.MILLI_GRAM, true),
            FAT_SOLUBLE_VITAMIN(0, "fat-soluble vitamin", VITAMIN, null, true),
                VITAMIN_A(26, "vitamin.a|retinol", FAT_SOLUBLE_VITAMIN, MeasurementUnit.MICRO_GRAM, false),
                VITAMIN_D(27, "vitamin.d|calciferol", FAT_SOLUBLE_VITAMIN, MeasurementUnit.MICRO_GRAM, false),
                VITAMIN_E(28, "vitamin.e|alpha.tocopherol", FAT_SOLUBLE_VITAMIN, null, false),
                VITAMIN_K(29, "vitamin.k|phylloquinone", FAT_SOLUBLE_VITAMIN, MeasurementUnit.MICRO_GRAM, false),
            WATER_SOLUBLE_VITAMIN(0, "water-soluble vitamin", VITAMIN, null, true),
                VITAMIN_C(18, "vitamin.c|ascorbic.acid", WATER_SOLUBLE_VITAMIN, null, false),
                THIAMIN(19, "vitamin.b1|thiamin[e]?", WATER_SOLUBLE_VITAMIN, null, false),
                RIBOFLAVIN(20, "vitamin.b2|riboflavin", WATER_SOLUBLE_VITAMIN, null, false),
                NIACIN(21, "vitamin.b3|vitamin.pp|niacin", WATER_SOLUBLE_VITAMIN, null, false),
                VITAMIN_B6(23, "vitamin.b6|pyridoxin[e]?", WATER_SOLUBLE_VITAMIN, null, false),
                FOLATE(42, "vitamin.b9|folat[e]?", WATER_SOLUBLE_VITAMIN, MeasurementUnit.MICRO_GRAM, false),
                VITAMIN_B12(40, "vitamin.b12", WATER_SOLUBLE_VITAMIN, MeasurementUnit.MICRO_GRAM, false),
        WATER(1, "Water", NUTRIENT, MeasurementUnit.MILLI_LITRE, false);
    //@formatter:on
    //</editor-fold>

    private final long id;
    private final String regex;
    private final NutrientType parent;
    private final MeasurementUnit defaultMeasurementUnit;
    private final List<NutrientType> children = new ArrayList<>();
    private final boolean placeholder;

    NutrientType(long id, String regex, NutrientType parent,
            MeasurementUnit defaultMeasurementUnit,
            boolean placeholder)
    {
        this.id = id;
        this.regex = regex;
        this.parent = parent;
        this.defaultMeasurementUnit = defaultMeasurementUnit;
        this.placeholder = placeholder;
        if (parent != null) {
            this.parent.addChild(this);
        }
    }

    public boolean is(NutrientType other)
    {
        if (other == null) {
            return false;
        } else if (this == other) {
            return true;
        } else if (this.parent == null) {
            return false;
        } else {
            return this.parent.is(other);
        }
    }

    public void addChild(NutrientType child)
    {
        this.children.add(child);
    }

    public long getId()
    {
        return id;
    }

    public NutrientType[] children()
    {
        return this.children.toArray(new NutrientType[children.size()]);
    }

    public String getRegex()
    {
        return regex;
    }

    public NutrientType getParent()
    {
        return parent;
    }

    public MeasurementUnit getDefaultMeasurementUnit()
    {
        if (defaultMeasurementUnit != null) {
            return defaultMeasurementUnit;
        } else if (parent != null) {
            return this.parent.getDefaultMeasurementUnit();
        } else if (this == NUTRIENT) {
            return defaultMeasurementUnit; // NUTRIENT don't require measurement unit.
        } else {
            throw new Error(
                    "Require default measurement unit for nutrient type: "
                            + this); // programming error, don't need to catch.
        }
    }

    public boolean isPlaceholder()
    {
        return placeholder;
    }
}
