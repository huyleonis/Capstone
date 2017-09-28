package com.is0967.menutri.rda.database;

import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.database.filter.NutrientFilter;
import com.is0967.menutri.rda.database.spi.NutrientDatabase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * Created by phuctran93 on 3/27/2017.
 */
public enum NutrientDatabaseService implements NutrientDatabase
{
    INSTANCE;
    private ServiceLoader<NutrientDatabase> loader;

    NutrientDatabaseService()
    {
        this.loader = ServiceLoader.load(NutrientDatabase.class);
    }

    @Override
    public void add(NutrientData nutrientData)
    {
        try {
            Iterator<NutrientDatabase> iterator = loader.iterator();
            while (iterator.hasNext()) {
                NutrientDatabase database = iterator.next();
                database.add(nutrientData);
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();
        }
    }

    @Override
    public List<NutrientData> find(NutrientFilter filter)
    {
        List<NutrientData> result = new ArrayList<>();
        try {
            Iterator<NutrientDatabase> iterator = loader.iterator();
            while (result.isEmpty() && iterator.hasNext()) {
                NutrientDatabase database = iterator.next();
                result = database.find(filter);
            }
        } catch (ServiceConfigurationError serviceError) {
            result = new ArrayList<>();
            serviceError.printStackTrace();
        }
        return result;
    }

    @Override
    public NutrientData findFirstBasic(NutrientFilter filter)
    {
        NutrientData result = null;
        try {
            Iterator<NutrientDatabase> iterator = loader.iterator();
            while (result == null && iterator.hasNext()) {
                NutrientDatabase database = iterator.next();
                result = database.findFirstBasic(filter);
            }
        } catch (ServiceConfigurationError serviceError) {
            result = null;
            serviceError.printStackTrace();
        }
        return result;
    }

    @Override
    public NutrientData findFirstExtra(NutrientFilter filter)
    {
        NutrientData result = null;
        try {
            Iterator<NutrientDatabase> iterator = loader.iterator();
            while (result == null && iterator.hasNext()) {
                NutrientDatabase database = iterator.next();
                result = database.findFirstExtra(filter);
            }
        } catch (ServiceConfigurationError serviceError) {
            result = null;
            serviceError.printStackTrace();
        }
        return result;
    }

    @Override
    public List<NutrientData> findBasic(NutrientFilter filter)
    {
        List<NutrientData> result = new ArrayList<>();
        try {
            Iterator<NutrientDatabase> iterator = loader.iterator();
            while (result.isEmpty() && iterator.hasNext()) {
                NutrientDatabase database = iterator.next();
                result = database.findBasic(filter);
            }
        } catch (ServiceConfigurationError serviceError) {
            result = new ArrayList<>();
            serviceError.printStackTrace();
        }
        return result;
    }

    @Override
    public List<NutrientData> findExtra(NutrientFilter filter)
    {
        List<NutrientData> result = new ArrayList<>();
        try {
            Iterator<NutrientDatabase> iterator = loader.iterator();
            while (result.isEmpty() && iterator.hasNext()) {
                NutrientDatabase database = iterator.next();
                result = database.findExtra(filter);
            }
        } catch (ServiceConfigurationError serviceError) {
            result = new ArrayList<>();
            serviceError.printStackTrace();
        }
        return result;
    }

    @Override
    public NutrientData findCalculatedData(NutrientFilter filter)
    {
        NutrientData result = null;
        try {
            Iterator<NutrientDatabase> iterator = loader.iterator();
            while (result == null && iterator.hasNext()) {
                NutrientDatabase database = iterator.next();
                result = database.findCalculatedData(filter);
            }
        } catch (ServiceConfigurationError serviceError) {
            result = null;
            serviceError.printStackTrace();
        }
        return result;
    }
}
