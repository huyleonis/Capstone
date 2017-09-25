package com.is0967.menutri.controllers;

import com.is0967.menutri.constants.Constant;
import com.is0967.menutri.dtos.NewsFeedDTO;
import com.is0967.menutri.dtos.ResponseDTO;
import com.is0967.menutri.dtos.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by NBL.Huyen on 02-04-17.
 */
@Controller
@RequestMapping("/search")
public class SearchController extends AbstractController {
   @RequestMapping(value = "/{value}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseDTO searchPost(@PathVariable String value) {
      logger.info("SEARCH | " + value);
      List<NewsFeedDTO> postResult = searchService.searchPost(value);
      List<UserDTO> userResult = searchService.searchUser(value);

      ResponseDTO responseDTO = new ResponseDTO(Constant.Code.SEARCH_SUCCESS, "search thành công");
      responseDTO.addObject(Constant.JsonName.POST_LIST, postResult);
      responseDTO.addObject(Constant.JsonName.USER_LIST, userResult);
      return responseDTO;
   }

}
