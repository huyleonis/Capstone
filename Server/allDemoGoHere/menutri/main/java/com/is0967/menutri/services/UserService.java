package com.is0967.menutri.services;

import com.is0967.menutri.dtos.UserDTO;
import com.is0967.menutri.entities.User;

import java.util.List;

public interface UserService {
   UserDTO login(User user);

   UserDTO insert(User user);

   UserDTO update(User user);

   boolean isAvailable(String username);

   boolean changePassword(User user, String newPassword);

   UserDTO getUserById(Long id);

   List<UserDTO> getAllUsers();

   User certify(Long userId);

   User setFeatured(Long userId);

   List<UserDTO> getFeaturedUsers();
}
