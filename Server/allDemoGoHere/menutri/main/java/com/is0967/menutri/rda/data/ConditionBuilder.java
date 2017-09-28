package com.is0967.menutri.rda.data;

import com.is0967.menutri.entities.Condition;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by phuctran93 on 4/9/2017.
 */
public class ConditionBuilder
{

    public static Queue<Condition> random(int quantity)
    {
        Queue<Condition> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < quantity; i++) {
            Condition c = new Condition();
            c.setName(NameGenerator.generateName());
            queue.add(c);
        }
        return queue;
    }

}
