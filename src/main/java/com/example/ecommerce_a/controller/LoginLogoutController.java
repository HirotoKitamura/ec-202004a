package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.form.LoginForm;

/**
 * ログインログアウトのコントローラー.
 * 
 * @author hiroto.kitamura
 *
 */
@Controller
@RequestMapping("")
public class LoginLogoutController {
	@Autowired
	private HttpSession session;

	@ModelAttribute
	private LoginForm setUpForm() {
		return new LoginForm();
	}

	/**
	 * ログイン画面を表示する.
	 * 
	 * @param from 遷移元のページ
	 * @return ログイン画面
	 */
	@RequestMapping("toLogin")
	public String toLogin(String from) {
		session.setAttribute("from", from == null ? "cart" : "header");
		return "login";
	}

	/**
	 * ログインする.
	 * 
	 * @param loginForm ログインフォーム
	 * @param result    バリデーション結果
	 * @return ヘッダーから来た場合:商品一覧画面 カートから注文確認しようとして来た場合:注文確認画面
	 */
	@RequestMapping("login")
	public String login(@Validated LoginForm loginForm, BindingResult result) {
		return null;
	}
}
