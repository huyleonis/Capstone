package com.is0967.menutri.dtos;

import com.is0967.menutri.entities.Comment;
import com.is0967.menutri.entities.User;

public class CommentDTO {
   Long id, userId, postId;
   String firstName, lastName;
   String content;
   Long createDate;

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
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

   public Long getPostId() {
      return postId;
   }

   public void setPostId(Long postId) {
      this.postId = postId;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public Long getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Long createDate) {
      this.createDate = createDate;
   }

   public static CommentDTO convertFromEntity(Comment comment) {
      CommentDTO commentDTO = new CommentDTO();
      if(comment.getId()!=null) commentDTO.setId(comment.getId());
      if(comment.getUser()!=null) {
         User user = comment.getUser();
         if(user.getFirstName()!=null) commentDTO.setFirstName(user.getFirstName());
         if(user.getLastName()!=null) commentDTO.setLastName(user.getLastName());
         commentDTO.setUserId(comment.getUser().getId());
      }
      if(comment.getContent()!=null) commentDTO.setContent(comment.getContent());
      if(comment.getCreateDate()!=null) commentDTO.setCreateDate(comment.getCreateDate());
      return commentDTO;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Id\t:");
      if (id != null) {
         sb.append(id + "\n");
      }
      sb.append("UserId\t:");
      if (userId!= null) {
         sb.append(userId + "\n");
      }
      sb.append("PostId\t:");
      if (postId!= null) {
         sb.append(postId + "\n");
      }
      sb.append("Content\t:");
      if (content!= null) {
         sb.append(content+ "\n");
      }
      sb.append("CreateDate\t:");
      if (userId!= null) {
         sb.append(createDate + "\n");
      }
      return sb.toString();
   }
}
