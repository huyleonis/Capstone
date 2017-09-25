package com.is0967.menutri.controllers;

import com.is0967.menutri.constants.Constant;
import com.is0967.menutri.dtos.ResponseDTO;
import com.is0967.menutri.dtos.UserDTO;
import com.is0967.menutri.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by NBL.Huyen on 17-02-17.
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

   @RequestMapping(value = "/login", method = RequestMethod.POST)
   @ResponseBody
   public ResponseDTO login(@RequestBody User user) {
      UserDTO userDTO = userService.login(user);
      if (userDTO != null) {
         ResponseDTO responseDTO =
                 new ResponseDTO(Constant.Code.LOGIN_SUCCESS, "Login thành công");
         responseDTO.addObject(Constant.JsonName.USER, userDTO);

         getSocialNetworkInfo(userDTO, responseDTO);
         logger.info("\nLOGIN: " + user + "SUCCESS");
         return responseDTO;
      } else {
         logger.info("\nLOGIN: " + user + "FAIL");
         return new ResponseDTO(Constant.Code.LOGIN_FAIL, "Login fail");
      }
   }

   private void getSocialNetworkInfo(UserDTO userDTO, ResponseDTO responseDTO) {
      long numOfPosts = socialNetworkService.getNumOfPostByUserId(userDTO.getId());
      long numOfFollowers = socialNetworkService.getNumOfFollowersByUserId(userDTO.getId());
      long numOfFollows = socialNetworkService.getNumOfFollowsByUserId(userDTO.getId());
      long numOfUncheckedNoti = socialNetworkService.getNumOfUncheckedNoti(userDTO.getId());

      responseDTO.addObject(Constant.JsonName.NUM_OF_FOLLOWER, numOfFollowers);
      responseDTO.addObject(Constant.JsonName.NUM_OF_FOLLOW, numOfFollows);
      responseDTO.addObject(Constant.JsonName.NUM_OF_POST, numOfPosts);
      responseDTO.addObject(Constant.JsonName.NUM_OF_UNCHECKED_NOTI, numOfUncheckedNoti);
   }

   @RequestMapping(method = RequestMethod.POST)
   @ResponseBody
   public ResponseDTO create(@RequestBody User user) {
      logger.info("\nCREATE USER: \n" + user);
      UserDTO dto = userService.insert(user);
      if (dto != null) {
         ResponseDTO reponseDto =
                 new ResponseDTO(Constant.Code.USER_CREATE_SUCCESS, "Tạo user thành công");
         reponseDto.addObject(Constant.JsonName.USER, dto);
         return reponseDto;
      } else {
         return new ResponseDTO(Constant.Code.USER_CREATE_FAIL, "Tạo user thất bại");
      }
   }

   @RequestMapping(method = RequestMethod.PUT)
   @ResponseBody
   public ResponseDTO update(@RequestBody User user) {
      logger.info("\nEDIT USER: \n" + user);
      UserDTO dto = userService.update(user);
      if (dto != null) {
         ResponseDTO responseDto =
                 new ResponseDTO(Constant.Code.USER_UPDATE_SUCCESS, "Sửa thông tin user thành công");
         responseDto.addObject(Constant.JsonName.USER, dto);
         return responseDto;
      } else {
         return new ResponseDTO(Constant.Code.USER_UPDATE_FAIL, "Sủa thông tin user thất bại");
      }
   }

   @RequestMapping(value = "/check/{username}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO isAvailable(@PathVariable String username) {
      boolean isAvailable = userService.isAvailable(username);
      if (isAvailable) {
         return new ResponseDTO(Constant.Code.USERNAME_AVAILABLE, "Username dùng được");
      } else {
         return new ResponseDTO(Constant.Code.USERNAME_NOT_AVAILABLE, "Username đã tồn tại");
      }
   }

   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO read(@PathVariable Long id) {
      logger.info("VIEW USER ID: " + id);
      UserDTO userDTO = userService.getUserById(id);
      if (userDTO != null) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.USER_READ_SUCCESS, "get user thành công");
         responseDTO.addObject(Constant.JsonName.USER, userDTO);

         getSocialNetworkInfo(userDTO, responseDTO);

         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.USER_READ_FAIL, "Load user lỗi");
      }
   }


   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO readAll() {
      StringBuilder sb = new StringBuilder();
      sb.append("VIEW ALL USERS | ");
      Long startTime = System.currentTimeMillis();
      List<UserDTO> listDTOs = userService.getAllUsers();
      if(!listDTOs.isEmpty()) {
         ResponseDTO responseDTO = new ResponseDTO(Constant.Code.USER_READ_SUCCESS, "get user thành công");
         responseDTO.addObject(Constant.JsonName.USER_LIST, listDTOs);
         Long endTime = System.currentTimeMillis();
         sb.append("Time: ").append(endTime-startTime).append("ms");
         logger.info(sb.toString());
         return responseDTO;
      } else {
         sb.append("FAILED");
         logger.info(sb.toString());
         return new ResponseDTO(Constant.Code.USER_READ_FAIL, "lỗi hoặc hệ thống ko có user");
      }
   }

   @RequestMapping(value = "/certify/{id}", method = RequestMethod.PUT)
   @ResponseBody
   public ResponseDTO certify(@PathVariable Long id) {
      logger.info("CERTIFY USER ID: " + id);
      User user = userService.certify(id);
      if (user != null) {
         ResponseDTO responseDTO =
                 new ResponseDTO(Constant.Code.USER_CERTIFY_SUCCESS, "certify thành công");
         responseDTO.addObject(Constant.JsonName.USER, user);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.USER_CERTIFY_FAIL, "lỗi server hoặc user không thuộc role có thể certify");
      }
   }

   @RequestMapping(value = "/featured/{id}", method = RequestMethod.PUT)
   @ResponseBody
   public ResponseDTO setFeatured(@PathVariable Long id) {
      logger.info("SET FEATURED USER ID: " + id);
      User user = userService.setFeatured(id);
      if (user != null) {
         ResponseDTO responseDTO =
                 new ResponseDTO(Constant.Code.USER_SET_FEATURED_SUCCESS, "set featured thành công");
         responseDTO.addObject(Constant.JsonName.USER, user);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.USER_SET_FEATURED_FAIL, "lỗi server ");
      }
   }

   @RequestMapping(value = "/featured", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO getFeaturedUser(@PathVariable Long userId) {
      logger.info("GET FEATURED ");
      List<UserDTO> featuredUsers = userService.getFeaturedUsers();
      if (featuredUsers != null) {
         ResponseDTO responseDTO =
                 new ResponseDTO(Constant.Code.USER_READ_SUCCESS, "lấy featured user thành công");
         responseDTO.addObject(Constant.JsonName.USER_LIST, featuredUsers);
         return responseDTO;
      } else {
         return new ResponseDTO(Constant.Code.USER_READ_FAIL, "lỗi server ");
      }
   }

}
