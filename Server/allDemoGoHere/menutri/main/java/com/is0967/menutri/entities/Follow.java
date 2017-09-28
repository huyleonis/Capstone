package com.is0967.menutri.entities;

import javax.persistence.*;

/**
 * Created by NBL.Huyen on 22-02-17.
 */
@Entity(name = "follows")
public class Follow {
   @Id
   @GeneratedValue(generator = "follows_id_seq", strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "follows_id_seq", sequenceName = "follows_id_seq")
   @Column
   private Long id;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;


   @ManyToOne
   @JoinColumn(name = "followed_user_id")
   private User followedUser;

   @Column(name = "create_date")
   private Long createDate;

   public Follow() {
   }

   public Follow(User user, User followedUser, Long createDate) {
      this.user = user;
      this.followedUser = followedUser;
      this.createDate = createDate;
   }

   public Long getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Long createDate) {
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

   public User getFollowedUser() {
      return followedUser;
   }

   public void setFollowedUser(User followedUser) {
      this.followedUser = followedUser;
   }
}
