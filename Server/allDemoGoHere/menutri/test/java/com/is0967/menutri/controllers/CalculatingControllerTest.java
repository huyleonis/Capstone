package com.is0967.menutri.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.Period;
import org.junit.Test;

public class CalculatingControllerTest
{

    @Test
    public void test()
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Period age = gson.fromJson("{years: 10, months: 12}", Period.class);
        String ageJson = gson.toJson(age);
        System.out.println(ageJson);
    }

}