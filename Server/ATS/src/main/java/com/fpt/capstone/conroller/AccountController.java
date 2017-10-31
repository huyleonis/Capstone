package com.fpt.capstone.conroller;

import com.fpt.capstone.constant.Constant;
import com.fpt.capstone.dto.AccountDTO;
import com.fpt.capstone.dto.ResponseDTO;
import com.fpt.capstone.entity.Account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController extends AbstractController {

    /**
     * chuc nang dang nhap
     *
     * @param account
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO login(Account account) {

        account.setUsername("quan");
        account.setPassword("123");

        AccountDTO accountDTO = accountService.login(account);
        if (accountDTO != null) {
            ResponseDTO responseDTO =
                    new ResponseDTO(Constant.Code.LOGIN_SUCCESS, "Dang Nhap Thanh Cong");
            responseDTO.addObject(Constant.JsonName.USER, accountDTO);
            logger.info("\nLOGIN " + account + " SUCCESS");
            return responseDTO;
        }

        logger.info("\nLOGIN " + account + " FAIL");
        return new ResponseDTO(Constant.Code.LOGIN_FAIL, "Dang Nhap That Bai");
    }

    /**
     * tao tai khoan moi
     *
     * @param account
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO create(Account account) {

        account.setUsername("example01");
        account.setPassword("example01");
        account.setBalance(0);
        account.setEmail("example01@gmail.com");
        account.setFullname("example01");
        account.setPhone("123456789");
        account.setRole(1);

        logger.info("\nCREATE ACCOUNT: " + account);
        AccountDTO accountDTO = accountService.insert(account);
        if (accountDTO != null) {
            ResponseDTO responseDTO =
                    new ResponseDTO(Constant.Code.CREATE_ACCOUNT_SUCCESS, "Tao Tai Khoan Thanh Cong");
            responseDTO.addObject(Constant.JsonName.USER, accountDTO);
            return responseDTO;
        }

        return new ResponseDTO(Constant.Code.CREATE_ACCOUNT_FAIL, "Tao Tai Khoan That Bai");
    }

    /**
     * cap nhat tai khoan
     *
     * @param account
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO update(Account account) {

        account.setId(11);
        account.setUsername("example01");
        account.setPassword("example01");
        account.setBalance(20000);
        account.setEmail("example01@gmail.com");
        account.setFullname("example01");
        account.setPhone("123456789");
        account.setRole(1);

        logger.info("\nEDIT ACCOUNT: " + account);
        AccountDTO accountDTO = accountService.update(account);
        if (accountDTO != null) {
            ResponseDTO responseDTO = new ResponseDTO(Constant.Code.EDIT_ACCOUNT_SUCCESS, "Cap Nhat Tai Khoan Thanh Cong");
            responseDTO.addObject(Constant.JsonName.USER, accountDTO);
            return responseDTO;
        }

        return new ResponseDTO(Constant.Code.EDIT_ACCOUNT_FAIL, "Cap Nhat Tai Khoan That Bai");
    }

    public ResponseDTO isAvailable(String username) {

        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO read(@PathVariable int id) {

        logger.info("\nVIEW USER ID: " + id);
        AccountDTO accountDTO = accountService.getAccountById(id);
        if (accountDTO != null) {
            ResponseDTO responseDTO = new ResponseDTO(Constant.Code.GET_ACCOUNT_SUCCESS, "Tim Tai Khoan Thanh Cong");
            responseDTO.addObject(Constant.JsonName.USER, accountDTO);
            return responseDTO;
        }

        return new ResponseDTO(Constant.Code.GET_ACCOUNT_FAIL, "Tim Tai Khoan That Bai");
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO readAll() {

        logger.info("\nVIEW ALL USER");
        List<AccountDTO> dtos = accountService.getAllAccount();
        if (!dtos.isEmpty()) {
            ResponseDTO responseDTO = new ResponseDTO(Constant.Code.GET_ACCOUNT_SUCCESS, "Tim Tai Khoan Thanh Cong");
            responseDTO.addObject(Constant.JsonName.USER, dtos);
            return responseDTO;
        }

        return new ResponseDTO(Constant.Code.GET_ACCOUNT_FAIL, "Tim Tai Khoan That Bai");
    }

}
