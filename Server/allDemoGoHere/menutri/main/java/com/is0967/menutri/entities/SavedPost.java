package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by NBL.Huyen on 20-03-17.
 */
@Entity(name = "saved_posts")
public class SavedPost {

   @Id
   @GeneratedValue(generator = "saved_post_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "saved_post_id_seq", sequenceName = "saved_post_id_seq")
   @Column
   private Long id;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   @ManyToOne
   @JoinColumn(name = "post_id")
   private Post post;

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
