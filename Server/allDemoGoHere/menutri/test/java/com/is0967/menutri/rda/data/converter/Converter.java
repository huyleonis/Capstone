package com.is0967.menutri.rda.data.converter;

import com.is0967.menutri.rda.data.BodyData;
import com.is0967.menutri.rda.data.NutrientData;
import com.is0967.menutri.rda.data.RdaResult;

/**
 * Created by phuctran93 on 3/12/2017.
 */
public class Converter
{

    public static BodyDataConverter get(BodyData bodyData)
    {
        BodyDataConverter converter = new BodyDataConverter(bodyData);
        return converter;
    }

    public static NutrientDataConverter get(NutrientData nutrientData)
    {
        NutrientDataConverter converter = new NutrientDataConverter(nutrientData);
        return converter;
    }

    public static RdaResultConverter get(RdaResult rdaResult)
    {
        RdaResultConverter converter = new RdaResultConverter(rdaResult);
        return converter;
    }
}
