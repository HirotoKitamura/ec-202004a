package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.LoginForm;
import com.example.ecommerce_a.service.LoginLogoutService;

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

	@Autowired
	private LoginLogoutService loginLogoutService;

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
		session.setAttribute("from", from);// nullだった場合は変更しないなどするといいかも
		return "login";
	}

	/**
	 * ログインする.
	 * 
	 * @param loginForm ログインフォーム
	 * @param result    バリデーション結果
	 * @return ヘッダーから来た場合:商品一覧画面 カートから注文確認しようとして来た場合:注文確認画面 ログインできなかった場合:ログイン画面
	 */
	@RequestMapping("login")
	public String login(@Validated LoginForm loginForm, BindingResult result) {
		User user = loginLogoutService.login(loginForm.getEmail(), loginForm.getPassword());
		if (user == null) {
			result.rejectValue("password", null, "メールアドレス、またはパスワードが間違っています");
		}
		if (result.hasErrors()) {
			return "login";
		}
		Integer guestId = (Integer) session.getAttribute("userId");
		if (guestId != null) {
			loginLogoutService.updateUserId(guestId, user.getId());
		}
		session.setAttribute("userId", user.getId());
		session.setAttribute("user", user);
		String from = (String) session.getAttribute("from");
		session.removeAttribute("from");
		if ("cart".equals(from)) {
			return "redirect:/showOrderConfirm";
		} else {
			return "redirect:/showItemList";
		}
	}

	/**
	 * ログアウトする.
	 * 
	 * @return 商品一覧画面
	 */
	@RequestMapping("logout")
	public String logout() {
		session.invalidate();
		return "redirect:/showItemList";
	}
}
