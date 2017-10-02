package com.is0967.menutri.rda;

import com.is0967.menutri.dtos.BodyData;
import com.is0967.menutri.rda.data.BodyDataBuilder;
import org.junit.Test;

/**
 * Created by phuctran93 on 4/9/2017.
 */
public class RandomTest
{

    @Test
    public void random() throws Exception
    {
        BodyData random = null;
        int diff = 0;
        for (int i = 0; i < 10000; i++) {
            BodyData next = BodyDataBuilder.Sample.random();
            if (next != random) diff++;
            random = next;
        }
        System.out.println("Diff: " + diff);
    }

}