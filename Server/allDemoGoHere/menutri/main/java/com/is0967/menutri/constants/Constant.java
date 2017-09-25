package com.is0967.menutri.constants;

public class Constant {
   public class Code {
      public static final int LOGIN_SUCCESS = 1000;
      public static final int LOGIN_FAIL = 1001;
      public static final int FOLLOW_SUCCESS = 1100;
      public static final int FOLLOW_FAIL = 1101;
      public static final int UNFOLLOW_SUCCESS = 1110;
      public static final int UNFOLLOW_FAIL = 1111;
      public static final int LIKE_SUCCESS = 1200;
      public static final int LIKE_FAIL = 1201;
      public static final int LIKE_ALREADY = 1210;
      public static final int LIKE_NOT_YET = 1211;
      public static final int CALCULATE_SUCCESS = 1300;
      public static final int CALCULATE_FAIL = 1301;
      public static final int SEARCH_SUCCESS = 1300;
      public static final int SEARCH_FAIL = 1301;
      public static final int NOTIFICATION_READ_SUCCESS = 1400;


      public static final int USER_CREATE_SUCCESS = 2000;
      public static final int USER_CREATE_FAIL = 2001;
      public static final int USER_READ_SUCCESS = 2010;
      public static final int USER_READ_FAIL = 2011;
      public static final int USER_UPDATE_SUCCESS = 2020;
      public static final int USER_UPDATE_FAIL = 2021;
      public static final int USERNAME_AVAILABLE = 2100;
      public static final int USERNAME_NOT_AVAILABLE = 2101;
      public static final int USER_CERTIFY_SUCCESS = 2200;
      public static final int USER_CERTIFY_FAIL = 2201;
      public static final int USER_SET_FEATURED_SUCCESS = 2200;
      public static final int USER_SET_FEATURED_FAIL = 2201;


      public static final int POST_CREATE_SUCCESS = 3000;
      public static final int POST_CREATE_FAIL = 3001;
      public static final int POST_READ_SUCCESS = 3010;
      public static final int POST_READ_FAIL = 3011;
      public static final int POST_UPDATE_SUCCESS = 3020;
      public static final int POST_UPDATE_FAIL = 3021;
      public static final int POST_DELETE_SUCCESS = 3030;
      public static final int POST_DELETE_FAIL = 3031;
      public static final int POST_SAVE_SUCCESS = 3040;
      public static final int POST_SAVE_FAIL = 3041;
      public static final int POST_UNSAVE_SUCCESS = 3050;
      public static final int POST_UNSAVE_FAIL = 3051;
      public static final int POST_REPORT_SUCCESS = 3060;
      public static final int POST_REPORT_FAIL = 3061;
      public static final int POST_SET_FEATURED_SUCCESS = 3200;
      public static final int POST_SET_FEATURED_FAIL = 3201;
      // anhtb
      public static final int DISH_CREATE_SUCCESS = 4000;
      public static final int DISH_CREATE_FAIL = 4001;
      public static final int DISH_READ_SUCCESS = 4010;
      public static final int DISH_READ_FAIL = 4011;
      public static final int DISH_UPDATE_SUCCESS = 4020;
      public static final int DISH_UPDATE_FAIL = 4021;
      public static final int DISH_DELETE_SUCCESS = 4030;
      public static final int DISH_DELETE_FAIL = 4031;
      public static final int DISH_CATEGORY_READ_SUCCESS = 4110;
      public static final int DISH_CATEGORY_READ_FAIL = 4111;
      public static final int DISH_NUTRITION_READ_SUCCESS = 4210;
      public static final int DISH_NUTRITION_READ_FAIL = 4211;
      public static final int DISH_TAG_READ_SUCCESS = 4310;
      public static final int DISH_TAG_READ_FAIL = 4311;
      // end anhtb

      public static final int MENU_CREATE_SUCCESS = 5000;
      public static final int MENU_CREATE_FAIL = 5001;
      public static final int MENU_READ_SUCCESS = 5010;
      public static final int MENU_READ_FAIL = 5011;
      public static final int MENU_UPDATE_SUCCESS = 5020;
      public static final int MENU_UPDATE_FAIL = 5021;
      public static final int MENU_DELETE_SUCCESS = 5030;
      public static final int MENU_DELETE_FAIL = 5031;

      public static final int INGREDIENT_CREATE_SUCCESS = 6000;
      public static final int INGREDIENT_CREATE_FAIL = 6001;
      public static final int INGREDIENT_UPDATE_SUCCESS = 6010;
      public static final int INGREDIENT_UPDATE_FAIL = 6011;
      public static final int INGREDIENT_READ_SUCCESS = 6020;
      public static final int INGREDIENT_READ_FAIL = 6021;
      public static final int INGREDIENT_DELETE_SUCCESS = 6030;
      public static final int INGREDIENT_DELETE_FAIL = 6031;
      public static final int INGREDIENT_UNIT_CREATE_SUCCESS = 6000;
      public static final int INGREDIENT_UNIT_CREATE_FAIL = 6001;
      public static final int INGREDIENT_UNIT_READ_SUCCESS = 6120;
      public static final int INGREDIENT_UNIT_READ_FAIL = 6121;

      public static final int COMMENT_CREATE_SUCCESS = 7000;
      public static final int COMMENT_CREATE_FAIL = 7001;
      public static final int COMMENT_READ_SUCCESS = 7010;
      public static final int COMMENT_READ_FAIL = 7011;
      public static final int COMMENT_UPDATE_SUCCESS = 7020;
      public static final int COMMENT_UPDATE_FAIL = 7021;
      public static final int COMMENT_DELETE_SUCCESS = 7030;
      public static final int COMMENT_DELETE_FAIL = 7031;

      public static final int NUTRITIONAL_NEED_CREATE_SUCCESS = 8000;
      public static final int NUTRITIONAL_NEED_CREATE_FAIL = 8001;
      public static final int NUTRITIONAL_NEED_READ_SUCCESS = 8010;
      public static final int NUTRITIONAL_NEED_READ_FAIL = 8011;
      public static final int NUTRITIONAL_NEED_UPDATE_SUCCESS = 8020;
      public static final int NUTRITIONAL_NEED_UPDATE_FAIL = 8021;
      public static final int NUTRITIONAL_NEED_DELETE_SUCCESS = 8030;
      public static final int NUTRITIONAL_NEED_DELETE_FAIL = 8031;

      public static final int NUTRIENT_READ_SUCCESS = 9010;
      public static final int NUTRIENT_READ_FAIL = 9011;
   }

   public class JsonName {
      public static final String NUTRIENT_LIST = "nutrientlist";
      public static final String DISH_NUTRIENT_LIST = "dishnutrientlist";

      public static final String DISH = "dish";
      public static final String DISH_LIST = "dishlist";
      public static final String DISH_TAG_LIST = "dishtaglist";

      public static final String DISH_CATEGORY = "dishcategory";
      public static final String DISH_CATEGORY_LIST = "dishcategorylist";

      public static final String INGREDIENT = "ingredient";
      public static final String INGREDIENT_LIST = "ingredientlist";
      public static final String NUM_INGREDIENT = "numOfIngredient";
      public static final String INGREDIENT_UNIT = "ingredientunit";
      public static final String INGREDIENT_UNIT_LIST = "ingredientunitlist";


      public static final String MENU = "menu";
      public static final String MENU_LIST = "menulist";

      public static final String NUTRITIONAL_NEED = "menu";
      public static final String NUTRITIONAL_NEED_LIST = "menulist";

      public static final String USER = "user";
      public static final String USER_LIST = "userlist";

      public static final String POST_ID = "postid";
      public static final String POST = "post";
      public static final String POST_LIST = "postlist";
      public static final String NUM_OF_POST = "numOfPost";

      public static final String COMMENT = "comment";

      public static final String LIKE = "like";
      public static final String NUM_OF_LIKE = "numOfLike";

      public static final String FOLLOWER = "follower";
      public static final String NUM_OF_FOLLOWER = "numOfFollower";

      public static final String FOLLOW = "follow";
      public static final String NUM_OF_FOLLOW = "numOfFollow";
      public static final String IS_FOLLOWED = "isFollowed";

      public static final String NOTIFICATION_LIST = "notificationlist";
      public static final String NUM_OF_UNCHECKED_NOTI = "numOfUncheckedNoti";

      public static final String CALCULATED_MAP = "calculatedMap";
      public static final String REMAINING_MAP = "remainingMap";
      public static final String UPPER_LIMIT_MAP = "upperLimitMap";

      public static final String SEARCH_RESULT = "searchResult";
   }
}
