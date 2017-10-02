package com.is0967.menutri.services;

import com.is0967.menutri.dtos.UserDTO;
import com.is0967.menutri.entities.User;
import com.is0967.menutri.utils.SHAGenerator;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserServiceImpl extends AbstractServiceImpl implements UserService {
   @Override
   public UserDTO login(User user) {
      UserDTO userDTO = null;
      String hashedPassword = SHAGenerator.generateSHA256(user.getPassword()).toLowerCase();
      User loginUser = userRepo.findByUsernameAndPassword(user.getUsername(), hashedPassword);
      if (loginUser != null) {
         userDTO = UserDTO.convertFromEntity(loginUser);
      }
      return userDTO;
   }

   @Override
   public UserDTO insert(User user) {
      UserDTO dto = null;
      final String adminUsername = "admin";
      try {
         String hashedPassword = SHAGenerator.generateSHA256(user.getPassword()).toLowerCase();
         user.setPassword(hashedPassword);
         User processedUser = userRepo.save(user);
         if (processedUser != null) {
            dto = UserDTO.convertFromEntity(processedUser);
         }

         //follow admin va ban than de generate mon
         User admin = userRepo.findByUsername(adminUsername);
         followRepo.insertFollow(dto.getId(), admin.getId(), System.currentTimeMillis());
         followRepo.insertFollow(dto.getId(), dto.getId(), System.currentTimeMillis());
      } catch (ConstraintViolationException e) {
         logger.error(e.getMessage());
      } finally {
         return dto;
      }
   }

   @Override
   public UserDTO update(User user) {
      UserDTO dto = null;
      try {
         User existingUser = userRepo.findOne(user.getId());
         if (existingUser != null) {
            user.setPassword(existingUser.getPassword());
            User processedEntity = userRepo.save(user);
            dto = UserDTO.convertFromEntity(processedEntity);
         }
      } catch (Exception e) {
      } finally {
         return dto;
      }
   }

   @Override
   public boolean changePassword(User user, String newPassword) {
      boolean isPasswordChanged = false;
      try {
         String hashedOldPassword = SHAGenerator.generateSHA256(user.getPassword()).toLowerCase();
         User existing = userRepo.findByUsernameAndPassword(user.getUsername(), hashedOldPassword);
         if (existing != null) {
            String hashedNewPassword = SHAGenerator.generateSHA256(newPassword).toLowerCase();
            existing.setPassword(hashedNewPassword);
            userRepo.save(user);
            isPasswordChanged = true;
         }
      } catch (Exception e) {
         logger.error(e.getMessage());
      } finally {
         return isPasswordChanged;
      }

   }

   @Override
   public boolean isAvailable(String username) {
      User user = userRepo.findByUsername(username);
      return user == null;
   }

   @Override
   public UserDTO getUserById(Long userId) {
      User user = userRepo.findOne(userId);
      if (user != null) {
         UserDTO userDTO = UserDTO.convertFromEntity(user);
         return userDTO;
      } else {
         return null;
      }
   }

   @Override
   public List<UserDTO> getAllUsers() {
      List<User> listUsers = userRepo.findAll();
      List<UserDTO> listDTOs = new ArrayList<>();
      for (User user : listUsers) {
         listDTOs.add(UserDTO.convertFromEntity(user));
      }
      return listDTOs;
   }

   @Override
   public User certify(Long userId) {
      User processedUser = null;
      try {
         User user = userRepo.findOne(userId);
         Integer role = user.getRole();
         switch (role) {
            case User.ROLE_USER:
               user.setRole(user.ROLE_CERTIFIED);
               processedUser = userRepo.save(user);
               break;
            case User.ROLE_CERTIFIED:
               user.setRole(user.ROLE_USER);
               processedUser = userRepo.save(user);
               break;
            default:
         }
      } catch (Exception e) {
      } finally {
         return processedUser;
      }
   }

   @Override
   public User setFeatured(Long userId) {
      User processedUser = null;
      try {
         User user = userRepo.findOne(userId);
         Boolean featured = user.getFeatured();
         if (featured != true) {
            user.setFeatured(true);
            processedUser = userRepo.save(user);
         } else {
            user.setFeatured(false);
            processedUser = userRepo.save(user);
         }
      } catch (Exception e) {
      } finally {
         return processedUser;
      }
   }

   @Override
   public List<UserDTO> getFeaturedUsers() {
      List<User> listFeaturedUsers = userRepo.findByFeaturedTrue();
      List<UserDTO> listDTOs = new ArrayList<>();
      for (User featuredUser : listFeaturedUsers) {
         listDTOs.add(UserDTO.convertFromEntity(featuredUser));
      }
      return listDTOs;
   }
}
