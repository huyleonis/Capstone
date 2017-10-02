package com.is0967.menutri.dtos;

import com.is0967.menutri.entities.Comment;
import com.is0967.menutri.entities.Post;

import java.util.List;

public class PostDTO {
   private Long id;
   private Long userId, itemId;
   private Long createDate;
   private Integer type;
   private Integer numOfComment, numOfLike;
   private List<Comment> listComment;
   private Boolean featured;

   public Boolean getFeatured() {
      return featured;
   }

   public void setFeatured(Boolean featured) {
      this.featured = featured;
   }

   public Long getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Long createDate) {
      this.createDate = createDate;
   }

   public Integer getNumOfComment() {
      return numOfComment;
   }

   public void setNumOfComment(Integer numOfComment) {
      this.numOfComment = numOfComment;
   }

   public Integer getNumOfLike() {
      return numOfLike;
   }

   public void setNumOfLike(Integer numOfLike) {
      this.numOfLike = numOfLike;
   }

   public List<Comment> getListComment() {
      return listComment;
   }

   public void setListComment(List<Comment> listComment) {
      this.listComment = listComment;
   }

   public Integer getType() {
      return type;
   }

   public void setType(Integer type) {
      this.type = type;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public Long getItemId() {
      return itemId;
   }

   public void setItemId(Long itemId) {
      this.itemId = itemId;
   }

   /**
    * userId, itemId, id, title, notiType, dto, createDate
    *
    * @param post
    * @return
    */
   public static PostDTO convertFromEntity(Post post) {
      Long userId = post.getUserId();
      Long itemId = post.getItemId();
      Long id = post.getId();
      Integer type = post.getType();
      Long createDateInMilis = post.getCreateDate();
      Boolean featured = post.getFeatured();

      PostDTO dto = new PostDTO();
      if (id != null) dto.setId(id);
      dto.setUserId(userId);
      if (itemId != null) dto.setItemId(itemId);
      if (createDateInMilis != null) dto.setCreateDate(createDateInMilis);
      if (featured != null) dto.setFeatured(featured);
      dto.setType(type);

      return dto;
   }

   public static Post convertToEntity(PostDTO postDTO) {
      Post post = new Post();
      if (postDTO.getId() != null) post.setId(postDTO.getId());
      if (postDTO.getCreateDate() != null) post.setCreateDate(postDTO.getCreateDate());
      if (postDTO.getUserId() != null) post.setUserId(postDTO.getUserId());
      if (postDTO.getItemId() != null) post.setItemId(postDTO.getItemId());
      if (postDTO.getType() != null) post.setType(postDTO.getType());
      if (postDTO.getFeatured() != null) post.setFeatured(postDTO.getFeatured());
      return post;
   }

   @Override
   public String toString() {
      {
         StringBuilder sb = new StringBuilder();
         sb.append("\nID\t:");
         if (id != null) {
            sb.append(id);
         }
         sb.append("\nType\t:");
         if (type != null) {
            sb.append(type);
         }
         sb.append("\nUserId\t:");
         if (userId != null) {
            sb.append(userId);
         }
         sb.append("\nItemId\t:");
         if (itemId != null) {
            sb.append(itemId);
         }
         sb.append("\nCreateDate:");
         if (createDate != null) {
            sb.append(createDate);
         }
         return sb.toString();
      }
   }
}
