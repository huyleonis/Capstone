package com.is0967.menutri.services;

import com.is0967.menutri.dtos.NewsFeedDTO;
import com.is0967.menutri.dtos.UserDTO;

import java.util.List;

public interface SearchService {
   List<UserDTO> searchUser(String value);
   List<NewsFeedDTO> searchPost(String value);
}
