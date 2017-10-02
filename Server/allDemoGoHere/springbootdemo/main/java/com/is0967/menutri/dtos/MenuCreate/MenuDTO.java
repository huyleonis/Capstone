package com.is0967.menutri.dtos.MenuCreate;

import java.util.List;

public class MenuDTO extends AbstractMenuDTO {
   private List<DayMenuDTO> listDayMenu;
   private List<String> tags;

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(super.toString());
      sb.append("\nTags\t:");
      if (tags != null && !tags.isEmpty()) {
         sb.append(tags);
      }
      return sb.toString();
   }

   public List<String> getTags() {
      return tags;
   }

   public void setTags(List<String> tags) {
      this.tags = tags;
   }

   public List<DayMenuDTO> getListDayMenu() {
      return listDayMenu;
   }

   public void setListDayMenu(List<DayMenuDTO> listDayMenu) {
      this.listDayMenu = listDayMenu;
   }
}
