package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by NBL.Huyen on 22-02-17.
 */
@Entity(name = "likes")
public class Like {
   @Id
   @GeneratedValue(generator = "likes_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "likes_id_seq", sequenceName = "likes_id_seq")
   @Column
   private Long id;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   @ManyToOne
   @JoinColumn(name = "post_id")
   private Post post;

   @Column(name = "create_date")
   private Long createDate;

   public Long getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Long createDate) {
      this.createDate = createDate;
   }

   public Like() {
   }

   public Like(User user, Post post) {
      this.user = user;
      this.post = post;
   }

   public Like(User user, Post post, Long createDate) {
      this.user = user;
      this.post = post;
      this.createDate = createDate;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
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
