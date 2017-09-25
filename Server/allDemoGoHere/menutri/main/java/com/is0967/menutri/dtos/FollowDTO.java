package com.is0967.menutri.dtos;

import com.is0967.menutri.entities.Follow;

public class FollowDTO {
   String firstName, lastName;
   Long createDate;

   public static FollowDTO convertFromEntity(Follow follow) {
      FollowDTO dto = new FollowDTO();
      if(follow.getCreateDate()!=null) dto.setCreateDate(follow.getCreateDate());
      if(follow.getUser().getFirstName()!=null) dto.setFirstName(follow.getUser().getFirstName());
      if(follow.getUser().getLastName()!=null) dto.setLastName(follow.getUser().getLastName());
      return dto;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public Long getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Long createDate) {
      this.createDate = createDate;
   }
}
