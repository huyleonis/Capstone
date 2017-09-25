package com.is0967.menutri.rda.database;

import static com.is0967.menutri.rda.data.RdaType.BASIC;
import static com.is0967.menutri.rda.data.RdaType.CALCULATED;
import static com.is0967.menutri.rda.data.RdaType.EXTRA;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.data.NutrientType;
import com.is0967.menutri.rda.database.filter.NutrientFilter;
import com.is0967.menutri.rda.database.populater.PopulaterFactory;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by phuctran93 on 3/6/2017.
 */
public class DefaultNutrientDatabase implements NutrientDatabase
{

    private final HashMap<NutrientType, List<NutrientData>> DATABASE = new HashMap<>();

    public DefaultNutrientDatabase()
    {
        PopulaterFactory.VPOP.populate(this);
    }

    private List<NutrientData> getList(NutrientType nutrientType)
    {
        List<NutrientData> subDb = DATABASE.get(nutrientType);
        if (subDb == null) {
            subDb = new ArrayList<>();
            DATABASE.put(nutrientType, subDb);
        }
        return subDb;
    }

    @Override
    public void add(NutrientData nutrientData)
    {
        getList(nutrientData.getNutrientType()).add(nutrientData);
    }

    @Override
    public List<NutrientData> find(NutrientFilter filter)
    {
        return getList(filter.getNutrientType()).stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public NutrientData findFirstBasic(NutrientFilter filter)
    {
        return getList(filter.getNutrientType()).stream()
                .filter(filter.and(NutrientFilter.byRdaType(BASIC)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public NutrientData findFirstExtra(NutrientFilter filter)
    {
        return getList(filter.getNutrientType()).stream()
                .filter(filter.and(NutrientFilter.byRdaType(EXTRA)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<NutrientData> findBasic(NutrientFilter filter)
    {
        return getList(filter.getNutrientType()).stream()
                .filter(filter.and(NutrientFilter.byRdaType(BASIC)))
                .collect(Collectors.toList());
    }

    @Override
    public List<NutrientData> findExtra(NutrientFilter filter)
    {
        return getList(filter.getNutrientType()).stream()
                .filter(filter.and(NutrientFilter.byRdaType(EXTRA)))
                .collect(Collectors.toList());
    }

    @Override
    public NutrientData findCalculatedData(NutrientFilter filter)
    {
        return getList(filter.getNutrientType()).stream()
                .filter(filter.and(NutrientFilter.byRdaType(CALCULATED)))
                .findFirst()
                .orElse(null);
    }
}
