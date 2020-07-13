package com.example.ecommerce_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.form.RegistrationUserForm;
import com.example.ecommerce_a.service.RegistrationUserService;

/**
 * ユーザ登録を行うコントローラ．
 * 
 * @author yuiko.mitsui
 *
 */
@Controller
@RequestMapping("/registration-user")
public class RegistrationUserController {

	@Autowired
	RegistrationUserService registrationuserService;

	@ModelAttribute
	public RegistrationUserForm setupRegistrationUserForm() {
		return new RegistrationUserForm();
	}

	/**
	 * ユーザ登録画面を表示する．
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("")
	public String index() {
		return "register_user";
	}

	/**
	 * ユーザ登録を行う．
	 * 
	 * @return ユーザ登録画面(仮実装)
	 */
	@RequestMapping("/insert-user")
	public String insertUser(RegistrationUserForm form) {
		registrationuserService.insertUser(form);

		return "redirect:/toLogin";
	}
}
