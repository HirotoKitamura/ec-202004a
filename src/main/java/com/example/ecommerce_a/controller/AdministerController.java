package com.example.ecommerce_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.form.InsertItemForm;
import com.example.ecommerce_a.service.AdministerService;

/**
 * 管理者のコントローラークラス.
 * 
 * @author hiroto.kitamura
 *
 */
@Controller
@RequestMapping("administer")
public class AdministerController {
	@Autowired
	private AdministerService service;

	@ModelAttribute
	private InsertItemForm setUpForm() {
		return new InsertItemForm();
	}

	/**
	 * 管理者のトップ画面を表示する.
	 * 
	 * @return 管理者トップ画面
	 */
	@RequestMapping("")
	public String index() {
		return "administer";
	}

	/**
	 * 商品登録画面を表示する.
	 * 
	 * @return 商品登録画面
	 */
	@RequestMapping("toRegisterItem")
	public String toRegisterItem() {
		return "register-item";
	}

}
