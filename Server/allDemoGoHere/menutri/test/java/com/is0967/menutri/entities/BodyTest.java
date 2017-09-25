package com.is0967.menutri.entities;

import static org.junit.Assert.assertEquals;

import com.is0967.menutri.config.Config;
import com.is0967.menutri.repositories.BodyRepo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by phuctran93 on 4/7/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ContextConfiguration(classes = Config.class)
public class BodyTest
{

    @Autowired
    private BodyRepo bodyRepo;

    @Test
    public void testHashcode()
    {
        List<Body> bodyList = bodyRepo.findAll();

        Map<Integer, Body> bodyMap = new HashMap<>();
        bodyList.forEach(b -> bodyMap.put(b.hashCode(), b));
        System.out.println("List size: " + bodyList.size());
        System.out.println("Map size : " + bodyMap.size());

        assertEquals(bodyList.size(), bodyMap.size());
    }
}