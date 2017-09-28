package com.is0967.menutri.rda.data;

import com.is0967.menutri.entities.Body;
import com.is0967.menutri.entities.Formula;
import com.is0967.menutri.entities.Formula.RdaType;
import com.is0967.menutri.entities.Nutrient;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by phuctran93 on 4/9/2017.
 */
public class FormulaBuilder
{

    public static Queue<Formula> random(List<Body> body, List<Nutrient> nutrient, int quantity)
    {
        Queue<Formula> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < quantity; i++) {
            int bIndex = new Random().nextInt(body.size());
            int nIndex = new Random().nextInt(nutrient.size());
            Body b = body.get(bIndex);
            Nutrient n = nutrient.get(nIndex);
            Formula f = new Formula();
            f.setBody(b);
            f.setNutrient(n);
            f.setRdaValue("999");
            f.setRdaDelta("999");
            f.setRdaType(RdaType.BASIC);
            f.setReference("phuctran93");
        }
        return queue;
    }
}
