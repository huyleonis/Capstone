package com.is0967.menutri.dtos;

import com.is0967.menutri.entities.Like;

/**
 * Created by NBL.Huyen on 04-04-17.
 */
public class LikeDTO {
   private String firstName, lastName;
   private Long postId, createDate;

   public static LikeDTO convertFromEntity(Like like)  {
      LikeDTO dto = new LikeDTO();
      String firstName = like.getUser().getFirstName();
      String lastName = like.getUser().getLastName();
      Long createDate = like.getCreateDate();
      Long postId = like.getPost().getId();

      dto.setCreateDate(createDate);
      dto.setFirstName(firstName);
      dto.setLastName(lastName);
      dto.setPostId(postId);
      return dto;
   }

   public Long getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Long createDate) {
      this.createDate = createDate;
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

   public Long getPostId() {
      return postId;
   }

   public void setPostId(Long postId) {
      this.postId = postId;
   }
}
