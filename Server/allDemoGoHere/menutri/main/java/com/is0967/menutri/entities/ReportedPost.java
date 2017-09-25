package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by NBL.Huyen on 27-03-17.
 */
@Entity(name = "reported_posts")
public class ReportedPost {
   @Id
   @GeneratedValue(generator = "reported_posts_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "reported_posts_id_seq", sequenceName = "reported_posts_id_seq")
   @Column
   private Long id;

   @Column(name = "user_id")
   private Long userId;

   @ManyToOne
   @JoinColumn(name = "post_id")
   private Post post;

   public Long getId() {
      return id;
   }


   public Long getUserId() {
      return userId;
   }

   public void setUserId(Long userId) {
      this.userId = userId;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Post getPost() {
      return post;
   }

   public void setPost(Post post) {
      this.post = post;
   }
}
