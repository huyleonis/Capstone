package com.is0967.menutri.services;


import com.is0967.menutri.repositories.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by NBL.Huyen on 15-02-17.
 */
public class AbstractServiceImpl {
   protected final Logger logger = Logger.getLogger(this.getClass());

   @Autowired
   protected IngredientRepo ingredientRepo;

   @Autowired
   protected IngredientCategoryDetailRepo ingredientCategoryDetailRepo;

   @Autowired
   protected UserRepo userRepo;

   @Autowired
   protected DishRepo dishRepo;

   @Autowired
   protected DishCategoryRepo dishCategoryRepo;

   @Autowired
   protected NutrientRepo nutrientRepo;

   @Autowired
   protected PostRepo postRepo;

   @Autowired
   protected MenuRepo menuRepo;

   @Autowired
   protected NutritionalNeedRepo nutritionalNeedRepo;

   @Autowired
   protected NutritionalNeedDetailRepo nutritionalNeedDetailRepo;

   @Autowired
   protected MenuTagRepo menuTagRepo;

   @Autowired
   protected MenuDetailRepo menuDetailRepo;

   @Autowired
   protected LikeRepo likeRepo;

   @Autowired
   protected CommentRepo commentRepo;

   @Autowired
   protected FollowRepo followRepo;

   @Autowired
   protected IngredientDetailRepo ingredientDetailRepo;

   @Autowired
   protected DishDetailRepo dishDetailRepo;

   @Autowired
   protected DishCategoryDetailRepo dishCategoryDetailRepo;

   @Autowired
   protected DishTagRepo dishTagRepo;

   @Autowired
   protected DishTagDetailRepo dishTagDetailRepo;

   @Autowired
   protected MenuTagDetailRepo menuTagDetailRepo;

   @Autowired
   protected SavedPostRepo savedPostRepo;

   @Autowired
   protected IngredientUnitRepo ingredientUnitRepo;

   @Autowired
   protected ReportedPostRepo reportedPostRepo;

   @Autowired
   protected FormulaRepo formulaRepo;

   @Autowired
   protected BodyRepo bodyRepo;

   @Autowired
   protected ConditionRepo conditionRepo;

   @Autowired
   protected BodyConditionRepo bodyConditionRepo;
}
