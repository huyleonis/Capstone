package com.is0967.menutri.services;

import java.util.HashMap;
import java.util.List;

/**
 * Created by NBL.Huyen on 01-03-17.
 */
public interface CalculatingService {
     HashMap<Long, Double> calculateByDishes(HashMap<Long, Double> requirement,
                                             List<Long> listDishIds);
}
