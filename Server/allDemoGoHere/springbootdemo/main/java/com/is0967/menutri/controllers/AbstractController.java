package com.is0967.menutri.controllers;

import com.is0967.menutri.services.CalculatingService;
import com.is0967.menutri.services.DishService;
import com.is0967.menutri.services.IngredientService;
import com.is0967.menutri.services.MenuService;
import com.is0967.menutri.services.NutrientService;
import com.is0967.menutri.services.NutritionalNeedService;
import com.is0967.menutri.services.RecommendationService;
import com.is0967.menutri.services.SearchService;
import com.is0967.menutri.services.SocialNetworkService;
import com.is0967.menutri.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by NBL.Huyen on 15-02-17.
 */
public class AbstractController
{

    /**
     * Class này để inject các service, repo
     * Các controller khác đều extends class này, để giảm thiểu code
     */
    protected final Logger logger = Logger.getLogger(this.getClass());

    /* @Autowired dùng để inject service vào class*/
    @Autowired
    protected IngredientService ingredientService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected DishService dishService;

    @Autowired
    protected NutrientService nutrientService;

    @Autowired
    protected NutritionalNeedService nutritionalNeedService;

    @Autowired
    protected SocialNetworkService socialNetworkService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    protected CalculatingService calculatingService;

    @Autowired
    protected RecommendationService recommendationService;

    @Autowired
    protected SearchService searchService;
}
