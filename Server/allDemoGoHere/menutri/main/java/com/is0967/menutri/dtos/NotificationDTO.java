package com.is0967.menutri.dtos;

public class NotificationDTO {
   public transient static final int TYPE_FOLLOW = 0;
   public transient static final int TYPE_LIKE = 1;
   public transient static final int TYPE_COMMENT = 2;

   String firstName, lastName;
   Integer notiType, postType;
   Long postId, userId;
   Long createDate;

   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public Integer getPostType() {
      return postType;
   }

   public void setPostType(Integer postType) {
      this.postType = postType;
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

   public Integer getNotiType() {
      return notiType;
   }

   public void setNotiType(Integer notiType) {
      this.notiType = notiType;
   }

   public Long getPostId() {
      return postId;
   }

   public void setPostId(Long postId) {
      this.postId = postId;
   }

   public Long getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Long createDate) {
      this.createDate = createDate;
   }
}
