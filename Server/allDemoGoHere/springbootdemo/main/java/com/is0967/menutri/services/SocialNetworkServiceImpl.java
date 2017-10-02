package com.is0967.menutri.services;

import com.is0967.menutri.dtos.*;
import com.is0967.menutri.entities.*;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class SocialNetworkServiceImpl extends AbstractServiceImpl implements SocialNetworkService {
   @Autowired
   DishService dishService;
   @Autowired
   MenuService menuService;

   @Override
   public Post createPost(PostDTO postDTO) {
      Post createdPost = null;
      try {
         if (postDTO.getUserId() != null && postDTO.getType() != null
                 && postDTO.getItemId() != null) {
            Post post = PostDTO.convertToEntity(postDTO);
            post.setDeleted(false);
            post.setCreateDate(System.currentTimeMillis());
            post.setFeatured(false);
            createdPost = postRepo.save(post);
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return createdPost;
      }
   }

   @Override
   public PostDTO getPost(Long postId) {
      Post post = postRepo.findByIdAndDeletedFalse(postId);
      if (post != null) {
         PostDTO dto = PostDTO.convertFromEntity(post);
         Integer numOfLike = likeRepo.findNumOfLikeOfPost(postId);
         List<Comment> listComment = commentRepo.findByPostIdOrderByCreateDateAsc(postId);
         dto.setNumOfComment(listComment.size());
         dto.setNumOfLike(numOfLike);
         dto.setListComment(listComment);
         return dto;
      } else {
         return null;
      }
   }

   @Override
   public List<PostDTO> getAllPost() {
      List<Post> allPost = postRepo.findAllByDeletedFalse();
      List<PostDTO> postDTOs = new ArrayList<>();
      for (Post post : allPost) {
         postDTOs.add(PostDTO.convertFromEntity(post));
      }
      return postDTOs;
   }

   @Override
   public long getNumOfPostByUserId(Long userId) {
      List<Post> listPost = postRepo.findByUserIdAndDeletedFalse(userId);
      if (listPost != null) {
         return listPost.size();
      } else {
         return 0;
      }
   }

   @Override
   public List<NewsFeedDTO> getPostByUserId(Long userId) {
      List<NewsFeedDTO> listPostDTO = null;
      try {
         List<Post> listPost = postRepo.findByUserIdAndDeletedFalse(userId);
         listPostDTO = new ArrayList<>();
         for (Post post : listPost) {
            NewsFeedDTO dto = getDataFromPost(post);
            listPostDTO.add(dto);
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return listPostDTO;
      }
   }

   @Override
   public boolean savePost(Long userId, Long postId) {
      boolean isSaved = false;
      try {
         SavedPost post = savedPostRepo.findByUserIdAndPostId(userId, postId);
         if (post == null) {
            SavedPost savedPost = new SavedPost();
            savedPost.setUser(userRepo.findOne(userId));
            savedPost.setPost(postRepo.findOne(postId));
            savedPostRepo.save(savedPost);
            isSaved = true;
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return isSaved;
      }
   }

   @Override
   public boolean unsavePost(Long userId, Long postId) {
      boolean isUnsaved = false;
      try {
         SavedPost post = savedPostRepo.findByUserIdAndPostId(userId, postId);
         if (post != null) savedPostRepo.delete(post);
         isUnsaved = true;
      } catch (Exception e) {
      } finally {
         return isUnsaved;
      }
   }

   @Override
   public List<NewsFeedDTO> getSavedPostByUserId(Long userId) {
      List<NewsFeedDTO> feedDTOs = null;
      try {
         List<SavedPost> listPost = savedPostRepo.findByUserId(userId);
         feedDTOs = new ArrayList<>();
         for (SavedPost savedPost : listPost) {
            NewsFeedDTO newsFeedDTO = getDataFromPost(savedPost.getPost());
            feedDTOs.add(newsFeedDTO);
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return feedDTOs;
      }
   }

   @Override
   public HashMap<Post, Integer> getReportedPost() {

      HashMap<Post, Integer> result = null;
      List<ReportedPost> reportedItem = reportedPostRepo.findAll();
      if (reportedItem != null) {
         result = new HashMap<>();
         for (ReportedPost item : reportedItem) {
            if (!item.getPost().getDeleted()) {
               Integer times = result.get(item.getPost());
               if (times != null && times > 0) {
                  times++;
                  result.put(item.getPost(), times);
               } else {
                  result.put(item.getPost(), 1);
               }
            }
         }
      }

      return result;
   }

   @Override
   public Post updatePost(PostDTO postDTO) {
      Post updatedPost = null;
      try {
         Post post = PostDTO.convertToEntity(postDTO);
         updatedPost = postRepo.save(post);
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return updatedPost;
      }
   }

   @Override
   public List<FeaturedPostDTO> getFeaturedPost() {
      List<FeaturedPostDTO> listFeaturedPostDTO = null;
      try {
         List<Post> listPost = postRepo.findAlLByFeaturedTrueAndDeletedFalse();
         listFeaturedPostDTO = new ArrayList<>();
         for (Post post : listPost) {
            FeaturedPostDTO featuredPostDTO = new FeaturedPostDTO();
            featuredPostDTO.setPostId(post.getId());
            switch (post.getType()) {
               case Post.DISH:
                  Dish dish = dishRepo.findByIdAndDeletedFalse(post.getItemId());
                  if (dish.getName() != null) featuredPostDTO.setName(dish.getName());
                  if (dish.getImageurl() != null) featuredPostDTO.setImageurl(dish.getImageurl());
                  featuredPostDTO.setType(Post.DISH);
                  break;
               case Post.MENU:
                  Menu menu = menuRepo.findByIdAndDeletedFalse(post.getItemId());
                  if (menu.getName() != null) featuredPostDTO.setName(menu.getName());
                  if (menu.getImageurl() != null) featuredPostDTO.setImageurl(menu.getImageurl());
                  featuredPostDTO.setType(Post.MENU);
                  break;
            }
            listFeaturedPostDTO.add(featuredPostDTO);
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return listFeaturedPostDTO;
      }
   }

   @Override
   public Post setFeatured(Long id) {
      Post post = postRepo.findOne(id);
      Boolean isFeatured = post.getFeatured();
      if (isFeatured != true) {
         post.setFeatured(true);
         return postRepo.save(post);
      } else {
         post.setFeatured(false);
         return postRepo.save(post);
      }
   }

   @Override
   public List<NewsFeedDTO> getPostForNewsFeed(Long userId) {
      List<BigInteger> followedUserIds = followRepo.findFollowedIds(userId);
      List<Post> listPosts = new ArrayList<>();
      for (BigInteger followedUserId : followedUserIds) {
         listPosts.addAll(postRepo.findByUserIdAndDeletedFalse(followedUserId.longValue()));
      }
      listPosts.addAll(postRepo.findByUserIdAndDeletedFalse(userId)); //get user's post
      Collections.shuffle(listPosts);

      List<NewsFeedDTO> listFeedDTO = new ArrayList<>();
      for (Post post : listPosts) {
         NewsFeedDTO newsFeedDTO = getDataFromPost(post);
         listFeedDTO.add(newsFeedDTO);
      }
         return listFeedDTO;
   }

   private NewsFeedDTO getDataFromPost(Post post) {
      NewsFeedDTO newsFeedDTO = new NewsFeedDTO();
      if (post.getItemId() != null) newsFeedDTO.setItemId(post.getItemId());
      if (post.getId() != null) newsFeedDTO.setPostId(post.getId());
      if (post.getCreateDate() != null) newsFeedDTO.setCreateDate(post.getCreateDate());

      Like like = likeRepo.findByUserIdAndPostId(post.getUserId(), post.getId());
      newsFeedDTO.setIsLiked(like != null);

      newsFeedDTO.setNumOfLike(likeRepo.findNumOfLikeOfPost(post.getId()));
      newsFeedDTO.setNumOfComment(commentRepo.findNumOfCommentOfPost(post.getId()));
      User user = userRepo.findOne(post.getUserId());
      newsFeedDTO.setUser(UserDTO.convertFromEntity(user));
      switch (post.getType()) {
         case Post.DISH:
            Dish dish = dishRepo.findOne(post.getItemId());

            if (dish.getImageurl() != null) newsFeedDTO.setImageurl(dish.getImageurl());
            if (dish.getDescription() != null) newsFeedDTO.setDescription(dish.getDescription());
            if (dish.getName() != null) newsFeedDTO.setName(dish.getName());
            newsFeedDTO.setPostType(Post.DISH);
            break;
         case Post.MENU:
            Menu menu = menuRepo.findOne(post.getItemId());
            if (menu.getImageurl() != null) newsFeedDTO.setImageurl(menu.getImageurl());
            if (menu.getDescription() != null) newsFeedDTO.setDescription(menu.getDescription());
            if (menu.getName() != null) newsFeedDTO.setName(menu.getName());
            newsFeedDTO.setPostType(Post.MENU);
            break;
      }
      return newsFeedDTO;
   }

   @Override
   public Long getPostIdOfItem(Long itemId, Integer type) {
      return postRepo.findPostId(itemId, type);
   }

   @Override
   public boolean reportPost(Long userId, Long postId) {
      boolean isReported = false;
      try {
         ReportedPost reportedPost = reportedPostRepo.findByUserIdAndPostId(userId, postId);
         if (reportedPost == null) {
            reportedPost = new ReportedPost();
            reportedPost.setUserId(userId);
            reportedPost.setPost(postRepo.findOne(postId));
            reportedPostRepo.save(reportedPost);
            isReported = true;
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return isReported;
      }
   }

   @Override
   public boolean likePost(Long userId, Long postId) {
      boolean isProcessed = false;
      User user = userRepo.findOne(userId);
      Post post = postRepo.findOne(postId);
      Like like = likeRepo.findByUserIdAndPostId(userId, postId);
      try {
         if (like != null) {
            likeRepo.delete(like);
            isProcessed = true;
         } else {
            likeRepo.save(new Like(user, post, System.currentTimeMillis()));
            isProcessed = true;
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      }

      return isProcessed;
   }

   @Override
   public boolean isLiked(Long postId, Long userId) {
      return likeRepo.findByUserIdAndPostId(userId, postId) != null;
   }

   @Override
   public CommentDTO commentPost(CommentDTO commentDTO) {
      Long userId = commentDTO.getUserId();
      Long postId = commentDTO.getPostId();
      String content = commentDTO.getContent();
      try {
         if (userId != null && postId != null
                 && content != null && !content.trim().isEmpty()) {
            Comment comment = new Comment();
            comment.setContent(content);
            Long timeStamp = System.currentTimeMillis();
            comment.setCreateDate(timeStamp);
            comment.setUser(userRepo.findOne(userId));
            comment.setPost(postRepo.findOne(postId));
            commentRepo.save(comment);
            commentDTO.setCreateDate(timeStamp);
         }
      } catch (JDBCException e) {
         commentDTO = null;
         logger.error(e.getMessage());
      }
      return commentDTO;
   }

   @Override
   public boolean deleteComment(Long id) {
      boolean isDeleted = false;
      try {
         commentRepo.delete(id);
         isDeleted = true;
      } catch (HibernateException e) {
         logger.error(e.getMessage());
      }

      return isDeleted;
   }

   @Override
   public boolean follow(Long userId, Long userIdToFollow) {
      boolean isProcessed = false;
      try {
         if (followRepo.findByUserIdAndFollowedUserId(userId, userIdToFollow) == null) {
            Follow follow = new Follow(userRepo.findOne(userId),
                    userRepo.findOne(userIdToFollow), System.currentTimeMillis());
            followRepo.save(follow);
            isProcessed = true;
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      }
      return isProcessed;
   }

   @Override
   public boolean isFollowed(Long userId, Long followedUserId) {
      return followRepo.findByUserIdAndFollowedUserId(userId, followedUserId) != null;
   }

   @Override
   public boolean unfollow(Long userId, Long followedUserId) {
      boolean isProcessed = false;
      try {
         Follow follow = followRepo.findByUserIdAndFollowedUserId(userId, followedUserId);
         if (follow != null) followRepo.delete(follow);
         isProcessed = true;
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return isProcessed;
      }
   }

   @Override
   public long getNumOfFollowersByUserId(Long userId) {
      List<Follow> listFollowerIds = followRepo.findByFollowedUserId(userId);
      if (listFollowerIds != null) {
         return listFollowerIds.size();
      } else {
         return 0;
      }
   }

   @Override
   public long getNumOfFollowsByUserId(Long userId) {
      List<BigInteger> listFollowerIds = followRepo.findFollowedIds(userId);
      if (listFollowerIds != null) {
         return listFollowerIds.size();
      } else {
         return 0;
      }
   }

   @Override
   public long getNumOfUncheckedNoti(Long userId) {
      User user = userRepo.findOne(userId);

      long numOfUncheckedLike = 0;
      long numOfUncheckedFollow = 0;
      long numOfUncheckedComment = 0;

      if (user != null) {
         Long lastCheckInMilis = user.getLastCheckNotification();
         if (lastCheckInMilis != null) {
            numOfUncheckedLike = likeRepo.findByUserIdAndCreateDateGreaterThan(userId, lastCheckInMilis).size();
            numOfUncheckedFollow = followRepo.findByUserIdAndCreateDateGreaterThan(userId, lastCheckInMilis).size();
            numOfUncheckedComment = commentRepo.findByUserIdAndCreateDateGreaterThan(userId, lastCheckInMilis).size();
         }
      }
      return numOfUncheckedComment + numOfUncheckedFollow + numOfUncheckedLike;
   }

   @Override
   public List<NotificationDTO> getFollowNoti(Long userId) {
      List<Follow> listFollow = followRepo.findByFollowedUserId(userId);

      List<NotificationDTO> followDTOs = new ArrayList<>();
      for (Follow follow : listFollow) {
         NotificationDTO notificationDTO = new NotificationDTO();
         notificationDTO.setNotiType(NotificationDTO.TYPE_FOLLOW);
         notificationDTO.setUserId(follow.getUser().getId());
         notificationDTO.setCreateDate(follow.getCreateDate());
         notificationDTO.setFirstName(follow.getUser().getFirstName());
         notificationDTO.setLastName(follow.getUser().getLastName());
         followDTOs.add(notificationDTO);
      }

      return followDTOs;
   }

   @Override
   public List<NotificationDTO> getLikeNoti(Long userId) {
      List<BigInteger> postIds = postRepo.getPostIdsOfUser(userId);

      List<Like> listLikes = likeRepo.findByPostIdIn(convertBigIntegerToLong(postIds));

      List<NotificationDTO> likeDTOs = new ArrayList<>();
      for (Like like : listLikes) {
         if (like.getUser().getId() != userId) { //tránh tự lấy noti của chính mình
            NotificationDTO dto = new NotificationDTO();
            dto.setFirstName(like.getUser().getFirstName());
            dto.setLastName(like.getUser().getLastName());
            dto.setPostId(like.getPost().getId());
            dto.setCreateDate(like.getCreateDate());
            dto.setNotiType(NotificationDTO.TYPE_LIKE);
            dto.setPostType(like.getPost().getType());
            likeDTOs.add(dto);
         }
      }

      return likeDTOs;
   }

   @Override
   public List<NotificationDTO> getCommentNoti(Long userId) {
      List<BigInteger> postIds = postRepo.getPostIdsOfUser(userId);
      List<Comment> listComments = commentRepo.findByPostIdIn(convertBigIntegerToLong(postIds));

      List<NotificationDTO> commentDTOs = new ArrayList<>();
      for (Comment comment : listComments) {
         if (comment.getUser().getId() != userId) { //tránh tự lấy noti comment của chính mình
            NotificationDTO dto = new NotificationDTO();
            dto.setNotiType(NotificationDTO.TYPE_COMMENT);
            dto.setPostType(comment.getPost().getType());
            dto.setFirstName(comment.getUser().getFirstName());
            dto.setLastName(comment.getUser().getLastName());
            dto.setCreateDate(comment.getCreateDate());
            dto.setPostId(comment.getPost().getId());
            commentDTOs.add(dto);
         }
      }
      return commentDTOs;
   }

   @Override
   @Transactional
   public boolean deletePostAndContent(Long postId) {
      boolean isDeleted = false;
      try {
         Post post = postRepo.findOne(postId);
         if (post != null) {
            likeRepo.deleteByPostid(postId);
            commentRepo.deleteByPostId(postId);
            post.setDeleted(true);
            postRepo.save(post);

            Integer type = post.getType();
            switch (type) {
               case Post.DISH:
                  dishService.delete(post.getItemId());
                  break;
               case Post.MENU:
                  menuService.deleteMenu(post.getItemId());
                  break;
            }
            isDeleted = true;
         }
      } catch (HibernateException e) {
         logger.error(e.getMessage());
      } finally {
         return isDeleted;
      }
   }

   private List<Long> convertBigIntegerToLong(List<BigInteger> listBigIntegers) {
      List<Long> longIds = new ArrayList<>();
      for (BigInteger bigInteger : listBigIntegers) {
         longIds.add(bigInteger.longValue());
      }
      return longIds;
   }
}
