package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by NBL.Huyen on 15-02-17.
 */

/**
 * @Entity(name = "tên bảng trong db")
 */
@Entity(name = "comments")
public class Comment {
   @Id
   @GeneratedValue(generator = "comments_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "comments_id_seq", sequenceName = "comments_id_seq")
   private Long id;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;


   @ManyToOne
   @JoinColumn(name = "post_id")
   private Post post;

   @Column(name = "content")
   private String content;

   @Column(name = "create_date")
   private Long createDate;

   public Long getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Long createDate) {
      this.createDate = createDate;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("ID\t:").append(id + "\n");
      if (user != null) {
         sb.append("UserID\t:").append(user.getId() + "\n");
      }
      if (post != null) {
         sb.append("PostId\t:").append(post.getId() + "\n");
      }
      if (content != null) {
         sb.append("Content\t:").append(content + "\n");
      }
      return sb.toString();
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Post getPost() {
      return post;
   }

   public void setPost(Post post) {
      this.post = post;
   }
}
