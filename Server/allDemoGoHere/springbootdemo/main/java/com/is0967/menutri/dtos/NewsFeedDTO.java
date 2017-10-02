package com.is0967.menutri.dtos;

public class NewsFeedDTO {
   private Long postId, itemId;
   private String imageurl, description, name;
   private UserDTO user;
   private Integer postType;
   private Long createDate;
   private Integer numOfLike, numOfComment;
   private Boolean isLiked;

   public Boolean getIsLiked() {
      return isLiked;
   }

   public void setIsLiked(Boolean liked) {
      isLiked = liked;
   }

   public Integer getNumOfLike() {
      return numOfLike;
   }

   public void setNumOfLike(Integer numOfLike) {
      this.numOfLike = numOfLike;
   }

   public Integer getNumOfComment() {
      return numOfComment;
   }

   public void setNumOfComment(Integer numOfComment) {
      this.numOfComment = numOfComment;
   }

   public Long getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Long createDate) {
      this.createDate = createDate;
   }

   public Integer getPostType() {
      return postType;
   }

   public void setPostType(Integer postType) {
      this.postType = postType;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Long getPostId() {
      return postId;
   }

   public void setPostId(Long postId) {
      this.postId = postId;
   }

   public Long getItemId() {
      return itemId;
   }

   public void setItemId(Long itemId) {
      this.itemId = itemId;
   }

   public String getImageurl() {
      return imageurl;
   }

   public void setImageurl(String imageurl) {
      this.imageurl = imageurl;
   }

   public UserDTO getUser() {
      return user;
   }

   public void setUser(UserDTO user) {
      this.user = user;
   }
}
