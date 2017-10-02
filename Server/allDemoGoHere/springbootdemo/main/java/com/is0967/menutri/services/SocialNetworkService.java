package com.is0967.menutri.services;

import com.is0967.menutri.dtos.*;
import com.is0967.menutri.entities.Comment;
import com.is0967.menutri.entities.Post;

import java.util.HashMap;
import java.util.List;

public interface SocialNetworkService {
   //functions for post
   Post createPost(PostDTO postDTO);

   boolean deletePostAndContent(Long postId);

   PostDTO getPost(Long id);

   List<PostDTO> getAllPost();

   long getNumOfPostByUserId(Long userId);

   List<NewsFeedDTO> getPostByUserId(Long userId);

   boolean savePost(Long userId, Long postId);

   boolean unsavePost(Long userId, Long postId);

   List<NewsFeedDTO> getSavedPostByUserId(Long userId);

   HashMap<Post, Integer> getReportedPost();

   Post updatePost(PostDTO postDTO);

   List<FeaturedPostDTO> getFeaturedPost();

   Post setFeatured(Long id);

   List<NewsFeedDTO> getPostForNewsFeed(Long userId);

   Long getPostIdOfItem(Long itemId, Integer type);

   boolean reportPost(Long userId, Long postId);

   //////////////////////////////function like
   boolean likePost(Long userId, Long postId);

   boolean isLiked(Long postId, Long userId);

   //functions for comment
   CommentDTO commentPost(CommentDTO commentDTO);

   boolean deleteComment(Long id);

   //function follow
   boolean follow(Long userId, Long userIdToFollow);

   boolean unfollow(Long userId, Long followedUserId);

   boolean isFollowed(Long userId, Long followedUserId);

   long getNumOfFollowersByUserId(Long userId);

   long getNumOfFollowsByUserId(Long userId);

   //notification
   long getNumOfUncheckedNoti(Long userId);

   List<NotificationDTO> getFollowNoti(Long userId);

   List<NotificationDTO> getLikeNoti(Long userId);

   List<NotificationDTO> getCommentNoti(Long userId);
}
