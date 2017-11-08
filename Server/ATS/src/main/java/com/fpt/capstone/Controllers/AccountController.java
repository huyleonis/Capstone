/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.capstone.Dtos.AccountDTO;
import com.fpt.capstone.Entities.Account;
import com.fpt.capstone.Services.AccountService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView viewAccount() {
		ModelAndView m = new ModelAndView("user");
		return m;
	}

	@RequestMapping(value = "/getListAccount", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String getListAccount() throws JsonProcessingException {
		// ObjectMapper mapper = new ObjectMapper();
		List<AccountDTO> list = accountService.getListAccount();
		return new Gson().toJson(list);
	}

	/**
	 * Get account info by username
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/get/{username}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public AccountDTO getAccountByUsername(@PathVariable String username) {
		AccountDTO acc = accountService.getAccountByUsername(username);

		return acc;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestBody Account account) {

		boolean isSuccessful = false;
		
		AccountDTO dto = accountService.insert(account);

		if (dto != null) {
			isSuccessful = true;
		}

		return (isSuccessful) ? "success" : "fail";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@RequestBody Account account) {

		boolean isSuccessful = false;

		AccountDTO dto = accountService.update(account);

		if (dto != null) {
			isSuccessful = true;
		}

		return (isSuccessful) ? "success" : "fail";
	}

}
