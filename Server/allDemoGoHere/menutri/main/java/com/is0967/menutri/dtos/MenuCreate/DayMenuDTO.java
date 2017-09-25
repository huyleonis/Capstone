package com.is0967.menutri.dtos.MenuCreate;

import java.util.List;

public class DayMenuDTO extends AbstractMenuDTO {
   private List<MealMenuDTO> listMealMenu;

   public List<MealMenuDTO> getListMealMenu() {
      return listMealMenu;
   }

   public void setListMealMenu(List<MealMenuDTO> listMealMenu) {
      this.listMealMenu = listMealMenu;
   }

}