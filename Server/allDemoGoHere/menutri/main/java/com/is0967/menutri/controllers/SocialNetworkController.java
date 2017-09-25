package com.is0967.menutri.controllers;

import com.is0967.menutri.constants.Constant;
import com.is0967.menutri.dtos.*;
import com.is0967.menutri.entities.Comment;
import com.is0967.menutri.entities.Post;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NBL.Huyen on 21-02-17.
 */
@Controller
@RequestMapping("/social-network")
public class SocialNetworkController extends AbstractController {

   @RequestMapping(value = "/post", method = RequestMethod.POST)
   @ResponseBody
   public ResponseDTO createPost(@RequestBody PostDTO postDTO) {
      logger.info("\nCREATE POST" + postDTO);
      Post post = socialNetworkService.createPost(postDTO);
      if (post != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.POST_CREATE_SUCCESS, "Tạo post thành công");
         responseDTO.addObject(Constant.JsonName.POST, post);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.POST_CREATE_FAIL, "Tạo post thất bại");
      }
   }

   @RequestMapping(value = "/post", method = RequestMethod.PUT)
   @ResponseBody
   public ResponseDTO updatePost(PostDTO postDTO) {
      logger.info("\nEDIT POST" + postDTO);
      Post post = socialNetworkService.updatePost(postDTO);
      if (post != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.POST_UPDATE_SUCCESS, "Sửa post thành công");
         responseDTO.addObject(Constant.JsonName.POST, post);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.POST_UPDATE_FAIL, "Sửa post thất bại");
      }
   }

   @RequestMapping(value = "/post/{postId}", method = RequestMethod.DELETE)
   @ResponseBody
   public ResponseDTO deletePost(@PathVariable Long postId) {
      logger.info("DELETE POST AND CONTENT: " + postId);
      boolean isDeleted = socialNetworkService.deletePostAndContent(postId);
      if (isDeleted) {
         return new ResponseDTO(Constant.Code.POST_DELETE_SUCCESS, "Xoá bài thành công");
      } else {
         return new ResponseDTO(Constant.Code.POST_DELETE_FAIL, "Xoá bài thất bại");
      }
   }

   @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO readPostById(@PathVariable Long id) {
      logger.info("GET POST ID: " + id);
      PostDTO postDto = socialNetworkService.getPost(id);
      if (postDto != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.POST_READ_SUCCESS, "Tìm bài viết thành công");
         responseDTO.addObject(Constant.JsonName.POST, postDto);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.POST_READ_FAIL, "Không tìm được bài viết");
      }
   }

   @RequestMapping(value = "/post/byUser/{userId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO readPostByUserId(@PathVariable Long userId) {
      logger.info("GET POST OF USER ID: " + userId);
      List<NewsFeedDTO> listPostDto = socialNetworkService.getPostByUserId(userId);
      if (listPostDto != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.POST_READ_SUCCESS, "load post thành công");
         responseDTO.addObject(Constant.JsonName.POST_LIST, listPostDto);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.POST_READ_FAIL, "load post lỗi");
      }
   }

   @RequestMapping(value = "/post/saved/{userId}/{postId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO save(@PathVariable Long userId, @PathVariable Long postId) {
      logger.info("USER ID :" + userId + "  |  SAVE POST ID: " + postId);
      boolean isSaved = socialNetworkService.savePost(userId, postId);
      if (isSaved) {
         return new ResponseDTO(Constant.Code.POST_SAVE_SUCCESS, "save thành công");
      } else {
         return new ResponseDTO(Constant.Code.POST_SAVE_FAIL, "post đã được save");
      }
   }

   @RequestMapping(value = "/post/saved/{userId}/{postId}", method = RequestMethod.DELETE)
   @ResponseBody
   public ResponseDTO unsave(@PathVariable Long userId, @PathVariable Long postId) {
      logger.info("USER ID : " + userId + "UNSAVES POST ID: " + postId);
      boolean isUnsaved = socialNetworkService.unsavePost(userId, postId);
      if (isUnsaved) {
         return new ResponseDTO(Constant.Code.POST_UNSAVE_SUCCESS, "unsave thành công");
      } else {
         return new ResponseDTO(Constant.Code.POST_UNSAVE_FAIL, "unsave thất bại");
      }
   }


   @RequestMapping(value = "/post/saved/{userId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO readSavedPostByUserId(@PathVariable Long userId) {
      logger.info("GET SAVED POST OF USER ID: " + userId);
      List<NewsFeedDTO> listPostDTO = socialNetworkService.getSavedPostByUserId(userId);
      if (listPostDTO != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.POST_READ_SUCCESS, "lấy post thành công");
         responseDTO.addObject(Constant.JsonName.POST_LIST, listPostDTO);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.POST_READ_FAIL, "lấy post đã save lỗi");
      }
   }

   @RequestMapping(value = "/like/{userId}/{postId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO like(@PathVariable Long userId, @PathVariable Long postId) {
      logger.info("USER ID: " + userId + " LIKES POST ID: " + postId);
      boolean isProcessed = socialNetworkService.likePost(userId, postId);
      if (isProcessed) {
         return new ResponseDTO(Constant.Code.LIKE_SUCCESS, "Đã like");
      } else {
         return new ResponseDTO(Constant.Code.LIKE_FAIL, "Có sự cố khi like");
      }
   }

   @RequestMapping(value = "/follow/{userId}/{userIdToFollow}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO follow(@PathVariable Long userId, @PathVariable Long userIdToFollow) {
      logger.info("FOLLOW: " + userId + " follows " + userIdToFollow);
      boolean isProcessed = socialNetworkService.follow(userId, userIdToFollow);
      if (isProcessed) {
         return new ResponseDTO(Constant.Code.FOLLOW_SUCCESS, "Follow thành công");
      } else {
         return new ResponseDTO(Constant.Code.FOLLOW_FAIL, "Lỗi khi follow");
      }
   }

   @RequestMapping(value = "/unfollow/{userId}/{userIdToFollow}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO unfollow(@PathVariable Long userId, @PathVariable Long userIdToFollow) {
      logger.info("UNFOLLOW: " + userId + " unfollows " + userIdToFollow);
      boolean isProcessed = socialNetworkService.unfollow(userId, userIdToFollow);
      if (isProcessed) {
         return new ResponseDTO(Constant.Code.UNFOLLOW_SUCCESS, "unFollow thành công");
      } else {
         return new ResponseDTO(Constant.Code.UNFOLLOW_FAIL, "Lỗi khi unfollow");
      }
   }

   @RequestMapping(value = "/isFollowed/{userId}/{userIdToFollow}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO isFollowed(@PathVariable Long userId, @PathVariable Long userIdToFollow) {
      boolean isFollowed = socialNetworkService.isFollowed(userId, userIdToFollow);
      ResponseDTO responseDTO = new ResponseDTO();
      responseDTO.addObject(Constant.JsonName.IS_FOLLOWED, isFollowed);
      return responseDTO;
   }


   @RequestMapping(value = "/comment", method = RequestMethod.POST)
   @ResponseBody
   public ResponseDTO createComment(@RequestBody CommentDTO commentDTO) {
      logger.info("\nCREATE COMMENT: " + commentDTO);
      CommentDTO comment = socialNetworkService.commentPost(commentDTO);
      if (comment != null) {
         ResponseDTO responseDTO =
                 new ResponseDTO(Constant.Code.COMMENT_CREATE_SUCCESS, "Comment thành công");
         responseDTO.addObject(Constant.JsonName.COMMENT, comment);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.COMMENT_CREATE_FAIL, "Lỗi khi comment");
      }
   }

   @RequestMapping(value = "/comment/{id}", method = RequestMethod.DELETE)
   @ResponseBody
   public ResponseDTO deleteComment(@PathVariable Long id) {
      logger.info("DELETE COMMENT: " + id);
      boolean isCommentDeleted = socialNetworkService.deleteComment(id);
      if (isCommentDeleted) {
         return new ResponseDTO(Constant.Code.COMMENT_DELETE_SUCCESS, "Đã xoá comment");
      } else {
         return new ResponseDTO(Constant.Code.COMMENT_DELETE_FAIL, "Lỗi khi xoá comment");
      }
   }

   @RequestMapping(value = "/post/reported", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO getReportedPost() {
      logger.info("GET REPORTED POST");
      HashMap<Post, Integer> reportedPost = socialNetworkService.getReportedPost();
      ResponseDTO responseDTO = new ResponseDTO(Constant.Code.POST_READ_SUCCESS,
              "số post bị report: " + reportedPost.size());
      responseDTO.addObject(Constant.JsonName.POST_LIST, reportedPost);
      return responseDTO;
   }

   @RequestMapping(value = "/post/report/{userId}/{postId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO reportPost(@PathVariable Long userId, @PathVariable Long postId) {
      logger.info("USER ID:" + userId + " | REPORTS POST ID:" + postId);
      boolean isReported = socialNetworkService.reportPost(userId, postId);
      if (isReported) {
         return new ResponseDTO(Constant.Code.POST_REPORT_SUCCESS, "report thành công");
      } else {
         return new ResponseDTO(Constant.Code.POST_REPORT_FAIL, "đã report post này hoặc lỗi");
      }
   }

   @RequestMapping(value = "/post/featured", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO readFeaturedPost() {
      logger.info("GET ALL FEATURED POST");
      List<FeaturedPostDTO> listFeaturedPost = socialNetworkService.getFeaturedPost();
      if (listFeaturedPost != null && !listFeaturedPost.isEmpty()) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.POST_READ_SUCCESS,
                 "số featured post: " + listFeaturedPost.size());
         responseDTO.addObject(Constant.JsonName.POST_LIST, listFeaturedPost);
         return responseDTO;

      } else {
         return new ResponseDTO(Constant.Code.POST_READ_FAIL, "lỗi server hoặc không có featured post");
      }
   }

   @RequestMapping(value = "/post", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO readAllPost() {
      StringBuilder sb = new StringBuilder();
      sb.append("VIEW ALL POST | ");
      Long startTime = System.currentTimeMillis();
      List<PostDTO> listPostDTOs = socialNetworkService.getAllPost();
      if (listPostDTOs != null && !listPostDTOs.isEmpty()) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.POST_READ_SUCCESS, "lấy post thành công");
         responseDTO.addObject(Constant.JsonName.POST_LIST, listPostDTOs);
         Long endTime = System.currentTimeMillis();
         sb.append(endTime - startTime).append("ms");
         logger.info(sb.toString());
         return responseDTO;
      } else {
         sb.append("FAILED");
         logger.info(sb.toString());
         return new ResponseDTO(Constant.Code.POST_READ_FAIL, "lấy post lỗi hoặc hệ thống ko có post");
      }
   }

   @RequestMapping(value = "/post/featured/{id}", method = RequestMethod.PUT)
   @ResponseBody
   public ResponseDTO setFeatured(@PathVariable Long id) {
      logger.info("SET / UNSET FEATURED POST ID :" + id);
      Post post = socialNetworkService.setFeatured(id);
      if (post != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.POST_SET_FEATURED_SUCCESS,
                 "set featured post thành công");
         responseDTO.addObject(Constant.JsonName.POST, post);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.POST_SET_FEATURED_FAIL, "set featured post lỗi");
      }
   }

   @RequestMapping(value = "/feed/{userId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO readNewsFeed(@PathVariable Long userId) {
      logger.info("READ NEWS FEED USER ID: " + userId);
      List<NewsFeedDTO> a = socialNetworkService.getPostForNewsFeed(userId);
      if (a != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.POST_READ_SUCCESS,
                 "lấy post timeline thành công");
         responseDTO.addObject(Constant.JsonName.POST_LIST, a);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.POST_READ_FAIL, "lấy post timeline lỗi");
      }
   }

   @RequestMapping(value = "/isLiked/{userId}/{postId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO isLiked(@PathVariable Long userId, Long postId) {
      boolean isLiked = socialNetworkService.isLiked(postId, userId);
      if (isLiked) {
         return new ResponseDTO(Constant.Code.LIKE_ALREADY, "user đã like post này");
      } else {
         return new ResponseDTO(Constant.Code.LIKE_NOT_YET, "user chưa like post này");
      }
   }

   @RequestMapping(value = "/post/item/{type}/{id}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO getPostIdByItemId(@PathVariable Integer type, @PathVariable Long id) {
      StringBuilder sb = new StringBuilder();
      sb.append("GET POST ID OF ");
      if (type == Post.DISH) sb.append("DISH");
      if (type == Post.MENU) sb.append("MENU");
      sb.append(" ID: ").append(id);
      logger.info(sb.toString());

      Long postId = socialNetworkService.getPostIdOfItem(id, type);
      if (postId != null) {
         ResponseDTO dto = new ResponseDTO(Constant.Code.POST_READ_SUCCESS, "lấy post id thành công");
         dto.addObject(Constant.JsonName.POST_ID, postId);
         return dto;
      } else {
         return new ResponseDTO(Constant.Code.POST_READ_FAIL, "lỗi");
      }
   }

   @RequestMapping(value = "/notification/{userId}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO getNotification(@PathVariable Long userId) {
      logger.info("GET NOTIFICATION OF USER ID: " + userId);
      List<NotificationDTO> listLikeNoti = socialNetworkService.getLikeNoti(userId);
      List<NotificationDTO> listFollowNoti = socialNetworkService.getFollowNoti(userId);
      List<NotificationDTO> listCommentNoti = socialNetworkService.getCommentNoti(userId);
      List<NotificationDTO> listNoti = new ArrayList<>();
      listNoti.addAll(listCommentNoti);
      listNoti.addAll(listFollowNoti);
      listNoti.addAll(listLikeNoti);
      ResponseDTO responseDTO = new ResponseDTO(Constant.Code.NOTIFICATION_READ_SUCCESS, "lấy noti thành công");
      responseDTO.addObject(Constant.JsonName.NOTIFICATION_LIST, listNoti);
      return responseDTO;
   }
}
