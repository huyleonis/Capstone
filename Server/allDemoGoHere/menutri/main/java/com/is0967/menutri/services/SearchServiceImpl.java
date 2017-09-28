package com.is0967.menutri.services;

import com.is0967.menutri.dtos.NewsFeedDTO;
import com.is0967.menutri.dtos.UserDTO;
import com.is0967.menutri.entities.*;
import com.is0967.menutri.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl extends AbstractServiceImpl implements SearchService {

   private List<NewsFeedDTO> searchDishByName(String name) {
      List<Dish> a = dishRepo.findBySearchStringContainingAndDeletedFalse(name);
      List<NewsFeedDTO> listResults = new ArrayList<>();
      for (Dish dish : a) {
         Post post = postRepo.findByItemIdAndType(dish.getId(), Post.DISH);
         NewsFeedDTO newsFeedDTO = getDataFromPost(post);
         listResults.add(newsFeedDTO);
      }
      return listResults;
   }

   private List<NewsFeedDTO> searchMenuByName(String name) {
      List<Menu> listMenu = menuRepo.findBySearchStringContainingAndDeletedFalse(name);
      List<NewsFeedDTO> listResults = new ArrayList<>();
      for (Menu menu : listMenu) {
         Post post = postRepo.findByItemIdAndType(menu.getId(), Post.MENU);
         NewsFeedDTO newsFeedDTO = getDataFromPost(post);
         listResults.add(newsFeedDTO);
      }
      return listResults;
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
   public List<UserDTO> searchUser(String value) {
      List<User> foundUser = userRepo.search(value);
      List<UserDTO> results = new ArrayList<>();
      for (User user : foundUser) {
         results.add(UserDTO.convertFromEntity(user));
      }
      return results;
   }

   @Override
   public List<NewsFeedDTO> searchPost(String value) {
      List<NewsFeedDTO> searchResult = new ArrayList<>();
      searchResult.addAll(searchDishByName(value));
      searchResult.addAll(searchMenuByName(value));
      return searchResult;
   }

}
