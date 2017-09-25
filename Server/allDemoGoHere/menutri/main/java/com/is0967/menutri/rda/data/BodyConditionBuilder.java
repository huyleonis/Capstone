package com.is0967.menutri.rda.data;

import com.is0967.menutri.entities.Body;
import com.is0967.menutri.entities.BodyCondition;
import com.is0967.menutri.entities.Condition;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by phuctran93 on 4/9/2017.
 */
public class BodyConditionBuilder
{

    public static Queue<BodyCondition> random(Body body, Condition condition, int quantity)
    {
        Queue<BodyCondition> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < quantity; i++) {
            BodyCondition bc = new BodyCondition();
            bc.setBody(body);
            bc.setCondition(condition);
        }
        return queue;
    }
}
