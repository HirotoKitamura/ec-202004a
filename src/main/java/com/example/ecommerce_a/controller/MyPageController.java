package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.UpdateUserForm;
import com.example.ecommerce_a.service.LoginLogoutService;
import com.example.ecommerce_a.service.MyPageService;
import com.example.ecommerce_a.service.RegistrationUserService;



/**
 * ユーザーのマイページを表示するコントローラ.
 * 
 * @author hyoga.ito
 *
 */

@Controller
@RequestMapping("myPage")
public class MyPageController {

	@Autowired
	HttpSession session;

	@Autowired
	MyPageService myPageService;
	
	@Autowired
	RegistrationUserService registrationUserService;
	
	@Autowired
	LoginLogoutService loginLogoutService;

	/**
	 * フォームをセットアップする.
	 * 
	 * @return　フォームオブジェクト
	 */
	@ModelAttribute
	public UpdateUserForm setUpForm() {
		return new UpdateUserForm();
	}

	/**
	 * マイページを表示する.
	 * 
	 * @return マイページ
	 */
	@RequestMapping("")
	public String index() {
		if (session.getAttribute("user") == null) {
			return "redirect:/toLogin";
		}
		return "my_page";
	}
	
	/**
	 * ユーザー情報変更画面を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param hasError　バリデーションでエラーがある場合はtrue
	 * @return ユーザー情報変更画面
	 */
	@RequestMapping("showUpdateUser")
	public String showUpdateUser(Model model,boolean hasError) {
		if (session.getAttribute("user") == null) {
			return "redirect:/toLogin";
		}
		if(!hasError) {
			UpdateUserForm form =new UpdateUserForm();
			User user=(User)session.getAttribute("user");
			form.setName(user.getName());
			form.setEmail(user.getEmail());
			form.setZipcode(user.getZipcode());
			form.setAddress(user.getAddress());
			form.setTelephone(user.getTelephone());
			model.addAttribute(form);
		}
		return "update_user";
	}

	/**
	 * ユーザー情報を変更する.
	 * 
	 * @param form ユーザー情報
	 * @param result　バリデーションのエラー情報
	 * @param model　リクエストスコープ
　	 * @return　バリデーションエラーがある場合は、ユーザー情報変更画面　ない場合は、リダイレクト：マイページ
	 */
	@RequestMapping("updateUser")
	public String updateUser(@Validated UpdateUserForm form, BindingResult result,Model model) {
		User user = (User) session.getAttribute("user");

		if (!"".equals(form.getPassword()) ) {
			if (form.getPassword().length() < 8 || 16 < form.getPassword().length()) {
				result.reject("password", "パスワードは８文字以上１６文字以内で設定してください");
			}
			if (!form.getPassword().equals(form.getCheckpassword())) {
				result.reject("checkpassword", "パスワードと確認用パスワードが不一致です");
			}
		}
		
		if (!form.getEmail().equals(user.getEmail()) && registrationUserService.isExistEmail(form.getEmail())) {
			result.reject("email", "そのメールアドレスはすでに使われています");
		}
		
		user = loginLogoutService.login(user.getEmail(), form.getOldPassword());
		if(user == null) {
			result.reject("oldPassword","パスワードが間違っています");
		}

		if (result.hasErrors()) {
			return showUpdateUser(model,true);
		}
		
		user.setId(user.getId());
		
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		
		
		if(!"".equals(form.getPassword())) {
			user.setPassword(form.getPassword());
		}else {
			user.setPassword(form.getOldPassword());
		}
		
		
		user.setZipcode(form.getZipcode());
		user.setAddress(form.getAddress());
		user.setTelephone(form.getTelephone());
		
		myPageService.updateUser(user);
		
		user = myPageService.serchUserByEmail(user.getEmail());
		
		session.setAttribute("user", user);
		
		return "redirect:/myPage/";

	}
	
	/**
	 * ユーザー情報を削除する（退会する）.
	 * 
	 * @param userId　ユーザーID
	 * @return　ログアウトする
	 */
	@RequestMapping("withdrawal")
	public String withdrawal(Integer userId) {
		myPageService.withdrawalFromEcsite(userId);
		return "redirect:/logout";
	}
	
}
